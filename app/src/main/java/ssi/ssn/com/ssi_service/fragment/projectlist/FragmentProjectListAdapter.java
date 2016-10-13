package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;

public class FragmentProjectListAdapter extends RecyclerView.Adapter<FragmentProjectListViewHolder> {

    private static String TAG = FragmentProjectListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentProjectList fragment;
    private CardView cardView;

    private List<Project> projects;
    private Activity activity;

    public FragmentProjectListAdapter(int layoutCardView, final FragmentProjectList fragment) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        projects = fragment.getSQLiteHelper().getProjectList();
    }

    @Override
    public FragmentProjectListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentProjectListViewHolder viewHolder = new FragmentProjectListViewHolder(fragment.getActivity(), cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentProjectListViewHolder viewHolder, int position) {
        viewHolder.assignData(projects.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

}