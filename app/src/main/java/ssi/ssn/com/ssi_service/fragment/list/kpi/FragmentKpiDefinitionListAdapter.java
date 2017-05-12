package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FragmentKpiDefinitionListAdapter extends RecyclerView.Adapter<FragmentKpiDefinitionViewHolder> {

    private static String TAG = FragmentKpiDefinitionListAdapter.class.getSimpleName();

    protected static String HEADLINE_TYPE_KEY = "HEADLINE_KEY";
    protected static String HEADLINE_TYPE_NAME = "HEADLINE_Name";
    protected String headlineType = HEADLINE_TYPE_KEY;

    protected String filterText = "";

    private final int layoutCardView;
    private final FragmentKpiDefinitionList fragment;
    private CardView cardView;

    private Project project;
    private List<FragmentKpiDefinitionViewHolder> viewHolders = new ArrayList<>();
    protected List<ResponseKpiDefinition> currentDefinitions;


    public FragmentKpiDefinitionListAdapter(int layoutCardView, final FragmentKpiDefinitionList fragment, Project project, List<ResponseKpiDefinition> currentDefinitions) {
        this.layoutCardView = layoutCardView;
        this.project = project;
        this.fragment = fragment;
        this.currentDefinitions = currentDefinitions;
    }

    @Override
    public FragmentKpiDefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentKpiDefinitionViewHolder viewHolder = new FragmentKpiDefinitionViewHolder(fragment.getActivity(), this, cardView);
        viewHolders.add(viewHolder);
        Log.d(TAG, "View Holder create. ViewHolders Size: " + viewHolders.size());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentKpiDefinitionViewHolder viewHolder, int position) {
        viewHolder.assignData(project, currentDefinitions.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return currentDefinitions.size();
    }

    // ** Tap Settings ************************************************************************** //

    public void changeCardViewHeadlinesToKey() {
        if (headlineType.equals(HEADLINE_TYPE_KEY)) {
            return;
        }
        headlineType = HEADLINE_TYPE_KEY;
        updateViewHolders();
    }

    public void changeCardViewHeadlinesToName() {
        if (headlineType.equals(HEADLINE_TYPE_NAME)) {
            return;
        }
        headlineType = HEADLINE_TYPE_NAME;
        updateViewHolders();
    }

    public void updateViewHolders() {
        for (FragmentKpiDefinitionViewHolder viewHolder : viewHolders) {
            viewHolder.updateHeadline();
        }
    }
}