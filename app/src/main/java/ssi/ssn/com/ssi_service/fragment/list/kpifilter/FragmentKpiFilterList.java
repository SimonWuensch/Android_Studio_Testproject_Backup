package ssi.ssn.com.ssi_service.fragment.list.kpifilter;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKpi;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class FragmentKpiFilterList extends AbstractFragment {

    public static String TAG = FragmentKpiFilterList.class.getSimpleName();

    protected static String PROJECT_ID = TAG + "PROJECT_ID";

    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_kpi_filter;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_kpi_filter_cardview;

    private FragmentKpiFilterListAdapter adapter;
    private RecyclerView recyclerView;

    private Project project;
    private View rootView;

    private List<FilterKpi> filterKpiList;

    public static FragmentKpiFilterList newInstance(int projectID) {
        FragmentKpiFilterList fragment = new FragmentKpiFilterList();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }
        int projectID = getArguments().getInt(PROJECT_ID);
        SQLiteDB sqLiteDB = ((MainActivity) getActivity()).getSQLiteDB();
        project = sqLiteDB.project().getByID(projectID);
        CardObjectKpi.init(sqLiteDB, project);
        filterKpiList = project.getCardObjectKpi().getKpiFilters();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }

        rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
        Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

        adapter = new FragmentKpiFilterListAdapter(CARDVIEW, this, new Project(), new ArrayList<FilterKpi>());
        Log.d(TAG, "Adapter [" + adapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

        recyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

        initAdapter();
        initViewComponents();
        return rootView;
    }

    private void initAdapter() {
        new AsyncTask<Object, Void, RecyclerView.Adapter>() {
            @Override
            protected RecyclerView.Adapter doInBackground(Object... objects) {
                loadArguments();
                adapter = new FragmentKpiFilterListAdapter(CARDVIEW, FragmentKpiFilterList.this, project, filterKpiList);
                return adapter;
            }

            @Override
            protected void onPostExecute(RecyclerView.Adapter adapter) {
                recyclerView.setAdapter(adapter);
            }
        }.execute(adapter);
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_kpi_filter_list_title));

        ImageButton bReload = (ImageButton) rootView.findViewById(R.id.default_action_bar_button_reload);
        bReload.setVisibility(View.VISIBLE);
        bReload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initAdapter();
                        initViewComponents();
                    }
                }
        );

        ImageView ivAddFilter = (ImageView) rootView.findViewById(R.id.fragment_kpi_filter_list_add_filter);
        ivAddFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showKpiDefinitionList(project.get_id());
            }
        });
    }

    public void updateDataSet() {
        new AsyncTask<Object, Void, Object>(){
            @Override
            protected Object doInBackground(Object... objects) {
                CardObjectKpi.init(getSQLiteDB(), project);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                initAdapter();
                initViewComponents();
            }
        }.execute();
    }
}
