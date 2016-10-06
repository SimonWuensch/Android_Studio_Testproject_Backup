package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentLaunchBoardAdapter extends RecyclerView.Adapter<FragmentLaunchBoardViewHolder> {

    private static String TAG = FragmentLaunchBoardAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentLaunchBoard fragment;
    private CardView cardView;

    private List<String> defaultInputs;
    private Activity activity;

    public FragmentLaunchBoardAdapter(int layoutCardView, final FragmentLaunchBoard fragment) {
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
    public FragmentLaunchBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentLaunchBoardViewHolder viewHolder = new FragmentLaunchBoardViewHolder(fragment.getActivity(), cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentLaunchBoardViewHolder viewHolder, int position) {
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