package ssi.ssn.com.ssi_service.fragment.list.notification;

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
import ssi.ssn.com.ssi_service.fragment.list.notification.source.NotificationListSorter;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class FragmentNotificationList extends Fragment {

    public static String TAG = FragmentNotificationList.class.getSimpleName();
    public static String FILTER_ID = TAG + "FILTER_ID";
    protected static String PROJECT_ID = TAG + "PROJECT_ID";
    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_notification;
    private static int RECYCLERVIEW = R.id.fragment_standard_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_notification_card_view;

    private FragmentNotificationListAdapter adapter;
    private RecyclerView recyclerView;

    private Project project;
    private View rootView;
    protected FilterNotification filter;
    List<ResponseNotification> responseNotifications;

    public static FragmentNotificationList newInstance(int projectID) {
        return newInstance(projectID, -1);
    }

    public static FragmentNotificationList newInstance(int projectID, int filterID) {
        if (projectID <= 0) {
            return new FragmentNotificationList();
        }

        FragmentNotificationList fragment = new FragmentNotificationList();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);

        if (filterID != -1) {
            bundle.putInt(FILTER_ID, filterID);
        }

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
        CardObjectNotification.init(sqLiteDB, project);

        if (getArguments().containsKey(FILTER_ID)) {
            int filterID = getArguments().getInt(FILTER_ID);
            filter = project.getCardObjectNotification().getFilterByID(filterID);
            responseNotifications = NotificationListSorter.sortNotificationBySeverity(filter.getNotificationTable().getData());
            return;
        }
        responseNotifications = NotificationListSorter.sortNotificationBySeverity(project.getCardObjectNotification().getNotificationTable().getData());
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

        adapter = new FragmentNotificationListAdapter(CARDVIEW, this, new Project(), new FilterNotification(), new ArrayList<ResponseNotification>());
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
                adapter = new FragmentNotificationListAdapter(CARDVIEW, FragmentNotificationList.this, project, filter, responseNotifications);
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_notification_list_title));
    }
}
