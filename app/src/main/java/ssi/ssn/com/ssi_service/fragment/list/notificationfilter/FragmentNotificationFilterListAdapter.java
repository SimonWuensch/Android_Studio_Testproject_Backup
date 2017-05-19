package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.detector.DetectorCardObjectNotification;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

class FragmentNotificationFilterListAdapter extends RecyclerView.Adapter<FragmentNotificationFilterListViewHolder> {

    private static String TAG = FragmentNotificationFilterListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentNotificationFilterList fragment;
    protected FilterNotification clickedFilter = null;
    private CardView cardView;
    private Project project;
    private List<FilterNotification> notificationFilterList;
    private List<FragmentNotificationFilterListViewHolder> viewHolderList = new LinkedList<>();

    FragmentNotificationFilterListAdapter(int layoutCardView, FragmentNotificationFilterList fragment, Project project, List<FilterNotification> notificationFilterList) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.notificationFilterList = notificationFilterList;
    }

    @Override
    public FragmentNotificationFilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentNotificationFilterListViewHolder((MainActivity) fragment.getActivity(), this, cardView);
    }

    @Override
    public void onBindViewHolder(FragmentNotificationFilterListViewHolder viewHolder, int position) {
        viewHolder.assignData(project, notificationFilterList.get(position));
        viewHolderList.add(viewHolder);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return notificationFilterList.size();
    }

    protected void reloadCardViews() {

        new AsyncTask<Object, Void, Object>() {

            @Override
            protected Object doInBackground(Object... objects) {
                Log.d(TAG, "Reloading all filters");
                SQLiteDB sqLiteDB = ((MainActivity) fragment.getActivity()).getSQLiteDB();
                RequestHandler requestHandler = ((MainActivity) fragment.getActivity()).getRequestHandler();
                DetectorCardObjectNotification.loadAllActiveNotificationsFromNetwork(requestHandler, project);
                ObservationHelper.setLastObservationTimeToOLD(sqLiteDB, project);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                for (int i = 0; i < viewHolderList.size(); i++) {
                    FilterNotification filter = project.getCardObjectNotification().getFilterByID(notificationFilterList.get(i).getId());
                    notificationFilterList.remove(i);
                    notificationFilterList.add(i, filter);
                    FragmentNotificationFilterListViewHolder viewHolder = viewHolderList.get(i);
                    viewHolder.assignData(project, notificationFilterList.get(i));
                }
                Log.i(TAG + " - " + project.identity(), "All Filters reloaded. Filter count: [" + project.getCardObjectNotification().getNotificationFilters().size() + "]");
            }
        }.execute();
    }
}