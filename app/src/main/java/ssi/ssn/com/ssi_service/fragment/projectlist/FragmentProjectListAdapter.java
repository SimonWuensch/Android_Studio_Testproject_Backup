package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.projectlist.source.ProjectListSorter;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;

public class FragmentProjectListAdapter extends RecyclerView.Adapter<FragmentProjectListViewHolder> {

    private static String TAG = FragmentProjectListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentProjectList fragment;
    private CardView cardView;

    private List<Project> projects = new ArrayList<>();
    private Map<Project, FragmentProjectListViewHolder> viewHolderMap = new HashMap<>();

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

    public void reloadCardViews(){
        for(Project project : viewHolderMap.keySet()){
            ObservationHelper.setLastObservationTimeToOLD((MainActivity) fragment.getActivity(), project);
            ((MainActivity) fragment.getActivity()).getSQLiteDB().project().updateLastObservationTime(project);

            FragmentProjectListViewHolder viewHolder = viewHolderMap.get(project);
            boolean isLast = project.equals(viewHolderMap.keySet().toArray()[viewHolderMap.keySet().size()-1]);
            viewHolder.assignData(project, isLast);
            if(isLast){
                Log.e(TAG + "-TEST", "Last Project: " + project.identity());
            }
        }
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