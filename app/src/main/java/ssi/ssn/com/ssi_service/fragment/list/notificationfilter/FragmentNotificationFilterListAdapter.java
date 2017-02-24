package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;

public class FragmentNotificationFilterListAdapter extends  RecyclerView.Adapter<FragmentNotificationFilterListViewHolder>{

    private static String TAG = FragmentNotificationFilterListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentNotificationFilterList fragment;
    private CardView cardView;

    private Project project;
    private List<FilterNotification> notificationFilterList;

    private List<FragmentNotificationFilterListViewHolder> viewHolderList = new LinkedList<>();

    public FragmentNotificationFilterListAdapter(int layoutCardView, FragmentNotificationFilterList fragment, Project project, List<FilterNotification> notificationFilterList) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.notificationFilterList = notificationFilterList;
    }

    @Override
    public FragmentNotificationFilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentNotificationFilterListViewHolder viewHolder = new FragmentNotificationFilterListViewHolder((MainActivity) fragment.getActivity(), cardView);
        return viewHolder;
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

    public void reloadCardViews() {
        SQLiteDB sqLiteDB = ((MainActivity) fragment.getActivity()).getSQLiteDB();
        ObservationHelper.setLastObservationTimeToOLD(sqLiteDB, project);
        for (int i = 0; i < viewHolderList.size(); i++) {
            FragmentNotificationFilterListViewHolder viewHolder = viewHolderList.get(i);
            viewHolder.assignData(project, notificationFilterList.get(i));
        }
    }

}