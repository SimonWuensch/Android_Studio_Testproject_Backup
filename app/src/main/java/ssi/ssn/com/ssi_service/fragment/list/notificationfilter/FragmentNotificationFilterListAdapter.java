package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;

public class FragmentNotificationFilterListAdapter extends  RecyclerView.Adapter<FragmentNotificationFilterListViewHolder>{

    private static String TAG = FragmentNotificationFilterListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentNotificationFilterList fragment;
    private CardView cardView;

    private List<FilterNotification> notificationFilterList;

    public FragmentNotificationFilterListAdapter(int layoutCardView, FragmentNotificationFilterList fragment, List<FilterNotification> notificationFilterList){
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.notificationFilterList = notificationFilterList;
    }

    @Override
    public FragmentNotificationFilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentNotificationFilterListViewHolder viewHolder = new FragmentNotificationFilterListViewHolder(fragment.getActivity(), cardView);
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " initialized.");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentNotificationFilterListViewHolder viewHolder, int position) {
        viewHolder.assignData(notificationFilterList.get(position));
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " assigned Data. Position [" + position + "].");
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return notificationFilterList.size();
    }

}