package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

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

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class FragmentNotificationFilterList extends AbstractFragment {

    public static String TAG = FragmentNotificationFilterList.class.getSimpleName();

    protected static String PROJECT_ID = TAG + "PROJECT_ID";

    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_notification_filter;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_notification_filter_card_view;

    private FragmentNotificationFilterListAdapter adapter;
    private RecyclerView recyclerView;

    private Project project;
    private View rootView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
        Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

        adapter = new FragmentNotificationFilterListAdapter(CARDVIEW, this, new Project(), new ArrayList<FilterNotification>());
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
                adapter = new FragmentNotificationFilterListAdapter(CARDVIEW, FragmentNotificationFilterList.this, project, project.getCardObjectNotification().getNotificationFilters());
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_notification_filter_list_title));

        ImageButton bReload = (ImageButton) rootView.findViewById(R.id.default_action_bar_button_reload);
        bReload.setVisibility(View.VISIBLE);
        bReload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.reloadCardViews();
                    }
                }
        );

        ImageView ivAddFilter = (ImageView) rootView.findViewById(R.id.fragment_notification_filter_list_add_filter);
        ivAddFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showCreateNotificationFilterFragment(project.get_id());
            }
        });
    }

    public void updateDataSet() {
        new AsyncTask<Object, Void, Object>(){
            @Override
            protected Object doInBackground(Object... objects) {
                CardObjectNotification.init(getSQLiteDB(), project);
                adapter.setNotificationFilterList(project.getCardObjectNotification().getNotificationFilters());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
