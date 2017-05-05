package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKpi;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FragmentKpiDefinitionList extends AbstractFragment {

    public static String TAG = FragmentKpiDefinitionList.class.getSimpleName();

    protected static String PROJECT_ID = TAG + "PROJECT_ID";

    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_kpi_definition;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_kpi_definition_cardview;

    private FragmentKpiDefinitionListAdapter adapter;
    private RecyclerView mRecyclerView;
    private EditText etFilter;
    private Button clearButton;
    private View rootView;

    private Project project;
    private List<ResponseKpiDefinition> definitions;

    public static FragmentKpiDefinitionList newInstance(long projectID) {
        if (projectID <= 0) {
            return new FragmentKpiDefinitionList();
        }

        FragmentKpiDefinitionList fragment = new FragmentKpiDefinitionList();
        Bundle bundle = new Bundle();
        bundle.putLong(PROJECT_ID, projectID);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }
        long projectID = getArguments().getLong(PROJECT_ID);
        SQLiteDB sqLiteDB = ((MainActivity) getActivity()).getSQLiteDB();
        project = sqLiteDB.project().getByID(projectID);
        CardObjectKpi.init(sqLiteDB, project);
        definitions = project.getCardObjectKpi().getDefinitions().getDefinitions();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

            this.adapter = new FragmentKpiDefinitionListAdapter(CARDVIEW, project, this, definitions);
            Log.d(TAG, "Adapter [" + adapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

            mRecyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(adapter);
            Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

            initViewComponents();
        }
        return rootView;
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_kpi_definition_list_title));

        handleTapSettings();
        handleFilter();
    }

    // ** Tap Settings ************************************************************************** //

    private void handleTapSettings() {
        final Button bKey = (Button) rootView.findViewById(R.id.fragment_kpi_definition_list_tap_button_key);
        final Button bName = (Button) rootView.findViewById(R.id.fragment_kpi_definition_list_tap_button_name);

        bKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTapButtonPressed(bKey, bName);
                adapter.changeCardViewHeadlinesToKey();
            }
        });

        bName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTapButtonPressed(bName, bKey);
                adapter.changeCardViewHeadlinesToName();
            }
        });
    }

    private void handleTapButtonPressed(Button one, Button two) {
        one.setBackgroundColor(SourceHelper.getColor(getActivity(), R.color.colorWhite));
        one.setTextColor(SourceHelper.getColor(getActivity(), R.color.colorBlack));
        two.setBackgroundColor(SourceHelper.getColor(getActivity(), R.color.defaultColor));
        two.setTextColor(SourceHelper.getColor(getActivity(), R.color.colorWhite));
    }

    // ** Filter Settings *********************************************************************** //
    private void handleFilter() {
        etFilter = (EditText) rootView.findViewById(R.id.fragment_kpi_definition_list_edit_text_filter);
        super.onTextChangeListener(etFilter);
        clearButton = (Button) rootView.findViewById(R.id.fragment_kpi_definition_list_button_filter_clear);
        ImageButton ibFilter = (ImageButton) rootView.findViewById(R.id.fragment_kpi_definition_list_image_button_filter);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFilter.setText("");
                updateAdapter("");
            }
        });

        ibFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAdapter(etFilter.getText().toString());
            }
        });
    }

    private void updateAdapter(String filterText) {
        List<ResponseKpiDefinition> currentDefinitions = filterText.isEmpty() ? definitions : getDefinitionsByFilterText(filterText);
        this.adapter = new FragmentKpiDefinitionListAdapter(CARDVIEW, project, this, currentDefinitions);
        mRecyclerView.setAdapter(adapter);
    }

    private List<ResponseKpiDefinition> getDefinitionsByFilterText(String filterText) {
        List<ResponseKpiDefinition> currentDefinitions = new ArrayList<>();
        for (ResponseKpiDefinition definition : definitions) {
            String key = definition.getKey().toLowerCase();
            String name = definition.getName() == null ? "" : definition.getName().toLowerCase();
            if (name.contains(filterText) || key.contains(filterText)) {
                currentDefinitions.add(definition);
            }
        }
        return currentDefinitions;
    }

    @Override
    public void doAfterChanged() {
        String filterText = etFilter.getText().toString();
        clearButton.setVisibility(filterText.isEmpty() ? View.GONE : View.VISIBLE);
    }

}
