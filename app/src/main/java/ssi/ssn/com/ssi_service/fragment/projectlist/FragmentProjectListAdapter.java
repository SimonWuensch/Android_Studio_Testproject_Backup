package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ssi.ssn.com.ssi_service.model.data.source.Project;

public class FragmentProjectListAdapter extends RecyclerView.Adapter<FragmentProjectListViewHolder> {

    private static String TAG = FragmentProjectListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentProjectList fragment;
    private CardView cardView;

    private SortedList<Project> projects;
    private Activity activity;

    public FragmentProjectListAdapter(int layoutCardView, final FragmentProjectList fragment) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        projects = new SortedList<>(Project.class, new ProjectListCallback());
        projects.addAll(fragment.getSQLiteHelper().getProjectList());
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

    private class ProjectListCallback extends SortedList.Callback<Project> {

        @Override
        public int compare(Project p1, Project p2) {
            if(p1.get_id() == p2.get_id()){
                return p1.getProjectName().compareTo(p2.getProjectName());
            }else if(p1.get_id() < p2.get_id()){
                return 1;
            }else{
                return -1;
            }
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemInserted(position);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRemoved(position);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
        }

        @Override
        public void onChanged(int position, int count) {
        }

        @Override
        public boolean areContentsTheSame(Project oldItem, Project newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Project item1, Project item2) {
            return false;
        }

    }

}