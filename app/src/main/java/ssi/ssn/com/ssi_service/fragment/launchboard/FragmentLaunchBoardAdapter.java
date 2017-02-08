package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractGenerator;
import ssi.ssn.com.ssi_service.model.data.source.Project;

class FragmentLaunchBoardAdapter extends RecyclerView.Adapter<FragmentLaunchBoardViewHolder> {

    private static String TAG = FragmentLaunchBoardAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentLaunchBoard fragment;
    private CardView cardView;

    private List<AbstractGenerator> cardObjects;
    private Project project;

    FragmentLaunchBoardAdapter(int layoutCardView, final FragmentLaunchBoard fragment, Project project, List<AbstractGenerator> cardObjects) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.cardObjects = cardObjects;
    }

    @Override
    public FragmentLaunchBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentLaunchBoardViewHolder(fragment.getActivity(), cardView);
    }

    @Override
    public void onBindViewHolder(FragmentLaunchBoardViewHolder viewHolder, int position) {
        viewHolder.assignData(cardObjects.get(position), project);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return cardObjects.size();
    }

}