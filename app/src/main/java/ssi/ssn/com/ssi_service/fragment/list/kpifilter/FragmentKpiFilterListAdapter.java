package ssi.ssn.com.ssi_service.fragment.list.kpifilter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;

public class FragmentKpiFilterListAdapter extends  RecyclerView.Adapter<FragmentKpiFilterListViewHolder>{

    private static String TAG = FragmentKpiFilterListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentKpiFilterList fragment;
    private CardView cardView;

    private Project project;
    private List<FilterKpi> filterKpiList;

    public FragmentKpiFilterListAdapter(int layoutCardView, final FragmentKpiFilterList fragment, Project project, List<FilterKpi> filterKpiList){
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.filterKpiList = filterKpiList;
    }

    @Override
    public FragmentKpiFilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentKpiFilterListViewHolder viewHolder = new FragmentKpiFilterListViewHolder(fragment.getActivity(), cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentKpiFilterListViewHolder viewHolder, int position) {
        viewHolder.assignData(project, filterKpiList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return filterKpiList.size();
    }

}