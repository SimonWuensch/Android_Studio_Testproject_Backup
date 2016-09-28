package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.fragment.customlist.source.list.CustomListCreator;
import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class FragmentCustomListAdapter extends  RecyclerView.Adapter<FragmentCustomListViewHolder>{

    private static String TAG = FragmentCustomListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentCustomList fragment;
    private CardView cardView;

    private List<CustomListObject> customListInputs;
    private Activity activity;

    public FragmentCustomListAdapter(int layoutCardView, final FragmentCustomList fragment, String response){
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.activity = fragment.getActivity();

        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, response);
        customListInputs = CustomListCreator.getCustomList(responseApplication);
    }

    @Override
    public FragmentCustomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
//        Log.d(TAG, "CardView inflated [" + activity.getResources().getResourceName(layoutCardView) + "].");
        FragmentCustomListViewHolder viewHolder = new FragmentCustomListViewHolder(fragment.getActivity(), cardView);
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " initialized.");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentCustomListViewHolder viewHolder, int position) {
        viewHolder.assignData(customListInputs.get(position));
        Log.d(TAG, viewHolder.getClass().getSimpleName() + " assigned Data. Position [" + position + "].");
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return customListInputs.size();
    }

}