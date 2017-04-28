package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.app.Fragment;
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

import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKPI;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKPIDefinition;

public class FragmentKpiDefinitionList extends Fragment {

    public static String TAG = FragmentKpiDefinitionList.class.getSimpleName();

    protected static String PROJECT_ID = TAG + "PROJECT_ID";

    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_kpi_definition;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_kpi_definition_cardview;

    private Project project;
    private FragmentKpiDefinitionListAdapter adapter;
    private List<ResponseKPIDefinition> definitions;

    private View rootView;

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
        CardObjectKPI.init(sqLiteDB, project);
        definitions = project.getCardObjectKPI().getDefinitions().getDefinitions();
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

            this.adapter = new FragmentKpiDefinitionListAdapter(CARDVIEW, this, definitions);
            Log.d(TAG, "Adapter [" + adapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
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
        ImageButton ibFilter = (ImageButton) rootView.findViewById(R.id.fragment_kpi_definition_list_image_button_filter);
        final EditText etFilter = (EditText) rootView.findViewById(R.id.fragment_kpi_definition_list_edit_text_filter);

        ibFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.handleFilter(etFilter.getText().toString());
            }
        });
    }
}
