package ssi.ssn.com.ssi_service.fragment.componentlist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.model.notification.AndroidNotificationHelper;

public class FragmentComponentList extends Fragment{

    public static String TAG = FragmentComponentList.class.getSimpleName();
    protected static String PROJECT_ID = TAG + "PROJECT_ID";
    private static int FRAGMENT_LAYOUT = R.layout.fragment_component_list;
    private static int RECYCLERVIEW = R.id.fragment_component_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_component_list_card_view;
    private View rootView;
    private Project project;
    private List<ResponseComponent> responseComponentList;

    public static FragmentComponentList newInstance(long projectID) {
        if (projectID <= 0) {
            return new FragmentComponentList();
        }

        FragmentComponentList fragment = new FragmentComponentList();
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
        project = ((MainActivity) getActivity()).getSQLiteDB().project().getByID(projectID);
        CardObjectComponent.init((MainActivity) getActivity(), project);
        responseComponentList = project.getCardObjectComponent().getResponseComponentList();
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

            RecyclerView.Adapter mAdapter = new FragmentComponentListAdapter(CARDVIEW, this, responseComponentList);
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_component_list_title));
    }
}
