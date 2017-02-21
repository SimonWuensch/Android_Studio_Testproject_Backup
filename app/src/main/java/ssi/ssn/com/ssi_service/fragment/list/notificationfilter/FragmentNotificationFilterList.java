package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class FragmentNotificationFilterList extends AbstractFragment {

    public static String TAG = FragmentNotificationFilterList.class.getSimpleName();

    private static String PROJECT_ID = TAG + "PROJECT_ID";

    private static int FRAGMENT_LAYOUT = R.layout.fragment_notification_filter_list;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_notification_filter_list_card_view;

    private View rootView;
    private Project project;

    public static FragmentNotificationFilterList newInstance(int projectID) {
        FragmentNotificationFilterList fragment = new FragmentNotificationFilterList();
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
        project = getSQLiteDB().project().getByID(projectID);
        CardObjectNotification.init(getSQLiteDB(), project);
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


            RecyclerView.Adapter mAdapter = new FragmentNotificationFilterListAdapter(CARDVIEW, this, new ArrayList<>(project.getCardObjectNotification().getNotificationFilters().values()));
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_notification_filter_title));
    }
}
