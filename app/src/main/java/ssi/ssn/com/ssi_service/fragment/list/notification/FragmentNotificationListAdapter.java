package ssi.ssn.com.ssi_service.fragment.list.notification;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

class FragmentNotificationListAdapter extends RecyclerView.Adapter<FragmentNotificationListViewHolder> {

    private static String TAG = FragmentNotificationListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentNotificationList fragment;
    private CardView cardView;

    private List<ResponseNotification> notificationList;
    private Activity activity;

    private Project project;
    private FilterNotification filter;

    protected FragmentNotificationListAdapter(int layoutCardView, FragmentNotificationList fragment, Project project, FilterNotification filter, List<ResponseNotification> notificationList) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.filter = filter;
        this.notificationList = notificationList;
    }

    @Override
    public FragmentNotificationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentNotificationListViewHolder((MainActivity) fragment.getActivity(), project, filter, cardView);
    }

    @Override
    public void onBindViewHolder(FragmentNotificationListViewHolder viewHolder, int position) {
        viewHolder.assignData(fragment, position, notificationList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

}