package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.projectlist.source.ProjectListSorter;
import ssi.ssn.com.ssi_service.model.data.source.Project;

public class FragmentProjectListAdapter extends RecyclerView.Adapter<FragmentProjectListViewHolder> {

    private static String TAG = FragmentProjectListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentProjectList fragment;
    private CardView cardView;

    private List<Project> projects = new ArrayList<>();
    private Map<Project, FragmentProjectListViewHolder> viewHolderMap = new HashMap<>();
    private Activity activity;


    public FragmentProjectListAdapter(int layoutCardView, final FragmentProjectList fragment, List<Project> projects) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.projects = ProjectListSorter.sortProjectsByName(projects);
    }

    @Override
    public FragmentProjectListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentProjectListViewHolder viewHolder = new FragmentProjectListViewHolder((MainActivity) fragment.getActivity(), this, cardView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentProjectListViewHolder viewHolder, int position) {
        viewHolder.assignData(projects.get(position), projects.size() -1 == position);
        viewHolderMap.put(projects.get(position), viewHolder);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void add(int position, Project project) {
        projects.add(position, project);
        notifyItemInserted(position);
    }

    public void remove(Project project) {
        int position = projects.indexOf(project);
        projects.remove(position);
        notifyItemRemoved(position);
    }

    public void sort(){
        List<Project> sortedList = ProjectListSorter.sortProjectsByStatus(projects);
        for(Project project : sortedList){
            int from = viewHolderMap.get(project).getAdapterPosition();
            int to = sortedList.indexOf(project);
            notifyItemMoved(from, to);
        }

    }

}