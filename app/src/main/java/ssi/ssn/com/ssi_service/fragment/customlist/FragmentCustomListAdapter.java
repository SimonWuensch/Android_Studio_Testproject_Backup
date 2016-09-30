package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.ResponseAbstract;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

class FragmentCustomListAdapter extends  RecyclerView.Adapter<FragmentCustomListViewHolder>{

    private static String TAG = FragmentCustomListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentCustomList fragment;
    private CardView cardView;

    private List<CustomListObject> customListInputs;
    private Activity activity;

    FragmentCustomListAdapter(int layoutCardView, final FragmentCustomList fragment, ResponseAbstract responseAbstract){
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        customListInputs = responseAbstract.getCustomList(activity);
    }

    @Override
    public FragmentCustomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentCustomListViewHolder(fragment.getActivity(), cardView);
    }

    @Override
    public void onBindViewHolder(FragmentCustomListViewHolder viewHolder, int position) {
        viewHolder.assignData(customListInputs.get(position));
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