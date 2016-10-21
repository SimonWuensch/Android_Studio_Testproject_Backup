package ssi.ssn.com.ssi_service.fragment.componentlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentComponentListAdapter extends RecyclerView.Adapter<FragmentComponentListViewHolder> {

    private static String TAG = FragmentComponentListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentComponentList fragment;
    private CardView cardView;

    private List<String> defaultInputs;
    private Activity activity;

    public FragmentComponentListAdapter(int layoutCardView, final FragmentComponentList fragment) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        initDefaultInputs();
    }

    private void initDefaultInputs() {
        defaultInputs = new ArrayList<>();

        defaultInputs.add("ONE");
        defaultInputs.add("TWO");
        defaultInputs.add("THREE");
        defaultInputs.add("FOUR");
    }

    @Override
    public FragmentComponentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentComponentListViewHolder viewHolder = new FragmentComponentListViewHolder(fragment.getActivity(), cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentComponentListViewHolder viewHolder, int position) {
        viewHolder.assignData(defaultInputs.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return defaultInputs.size();
    }

}