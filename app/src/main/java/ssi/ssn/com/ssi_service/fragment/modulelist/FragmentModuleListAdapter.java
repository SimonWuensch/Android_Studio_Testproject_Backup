package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

class FragmentModuleListAdapter extends RecyclerView.Adapter<FragmentModuleListViewHolder> {

    private static String TAG = FragmentModuleListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private CardView cardView;

    private List<ResponseModule> responseModuleList;
    private Activity activity;

    FragmentModuleListAdapter(int layoutCardView, FragmentModuleList fragment, List<ResponseModule> responseModuleList) {
        this.layoutCardView = layoutCardView;
        this.activity = fragment.getActivity();
        this.responseModuleList = responseModuleList;
    }

    @Override
    public FragmentModuleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentModuleListViewHolder(activity, cardView);
    }

    @Override
    public void onBindViewHolder(FragmentModuleListViewHolder viewHolder, int position) {
        ResponseModule responseModule = responseModuleList.get(position);
        viewHolder.assignData(responseModule);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return responseModuleList.size();
    }

}