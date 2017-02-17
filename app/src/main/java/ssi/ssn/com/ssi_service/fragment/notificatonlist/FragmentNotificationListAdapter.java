package ssi.ssn.com.ssi_service.fragment.notificatonlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class FragmentNotificationListAdapter extends  RecyclerView.Adapter<FragmentNotificationListViewHolder>{

    private static String TAG = FragmentNotificationListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentNotificationList fragment;
    private CardView cardView;

    private List<ResponseNotification> notificationList;
    private Activity activity;

    public FragmentNotificationListAdapter(int layoutCardView, FragmentNotificationList fragment, List<ResponseNotification> notificationList){
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.notificationList = notificationList;
    }

    @Override
    public FragmentNotificationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
//        Log.d(TAG, "CardView inflated [" + activity.getResources().getResourceName(layoutCardView) + "].");
        FragmentNotificationListViewHolder viewHolder = new FragmentNotificationListViewHolder(fragment.getActivity(), cardView);
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " initialized.");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentNotificationListViewHolder viewHolder, int position) {
        viewHolder.assignData(notificationList.get(position));
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " assigned Data. Position [" + position + "].");
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