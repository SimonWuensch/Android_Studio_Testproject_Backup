package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.model.data.source.AbstractProject;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

public class FragmentModuleList extends Fragment {

    public static String TAG = FragmentModuleList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_module_list;
    private static int RECYCLERVIEW = R.id.fragment_module_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_module_list_card_view;

    private static String PROJECT_JSON = TAG + "PROJECT_JSON";

    private View rootView;
    private Project project;

    public static FragmentModuleList newInstance(Project project) {
        if (project == null) {
            return new FragmentModuleList();
        }

        FragmentModuleList fragment = new FragmentModuleList();
        Bundle bundle = new Bundle();
        bundle.putString(PROJECT_JSON, JsonHelper.toJson(project));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }

        String projectJson = getArguments().getString(PROJECT_JSON);
        project = (Project) JsonHelper.fromJsonGeneric(Project.class, projectJson);

        //TODO DELETE
        RequestHandler requestHandler = ((AbstractActivity)getActivity()).getRequestHandler();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        requestHandler.getRequestLoginTask(project).executeOnExecutor(executor);
        requestHandler.getRequestApplicationConfigTask(project).executeOnExecutor(executor);

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

            RecyclerView.Adapter mAdapter = new FragmentModuleListAdapter(CARDVIEW, this);
            Log.d(TAG, "Adapter [" + mAdapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

            initViewComponents();
        }
        return rootView;
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(getActivity().getString(R.string.fragment_module_list_title));
    }
}
