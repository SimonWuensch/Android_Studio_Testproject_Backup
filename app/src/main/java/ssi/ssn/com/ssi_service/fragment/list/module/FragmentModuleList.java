package ssi.ssn.com.ssi_service.fragment.list.module;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class FragmentModuleList extends Fragment {

    public static String TAG = FragmentModuleList.class.getSimpleName();
    protected static String PROJECT_ID = TAG + "PROJECT_ID";
    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_module;
    private static int RECYCLERVIEW = R.id.fragment_module_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_module_card_view;

    private FragmentModuleListAdapter adapter;
    private RecyclerView recyclerView;

    private Project project;
    private View rootView;
    private List<ResponseModule> responseModuleList;

    public static FragmentModuleList newInstance(long projectID) {
        if (projectID <= 0) {
            return new FragmentModuleList();
        }

        FragmentModuleList fragment = new FragmentModuleList();
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
        CardObjectModule.init(sqLiteDB, project);
        responseModuleList = project.getCardObjectModule().getResponseModuleList();
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

        adapter = new FragmentModuleListAdapter(CARDVIEW, this, new ArrayList<ResponseModule>());
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
        new AsyncTask<Object, Void, RecyclerView.Adapter>(){
            @Override
            protected RecyclerView.Adapter doInBackground(Object... objects) {
                loadArguments();
                adapter = new FragmentModuleListAdapter(CARDVIEW, FragmentModuleList.this, responseModuleList);
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_module_list_title));
    }
}
