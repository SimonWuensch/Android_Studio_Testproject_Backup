package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKPIDefinition;

public class FragmentKpiDefinitionListAdapter extends RecyclerView.Adapter<FragmentKpiDefinitionViewHolder> {

    protected static String HEADLINE_TYPE_KEY = "HEADLINE_KEY";
    protected static String HEADLINE_TYPE_NAME = "HEADLINE_Name";
    private static String TAG = FragmentKpiDefinitionListAdapter.class.getSimpleName();
    private final int layoutCardView;
    private final FragmentKpiDefinitionList fragment;
    protected String headlineType = HEADLINE_TYPE_KEY;
    protected String filterText = "";
    protected List<ResponseKPIDefinition> definitions;
    private CardView cardView;
    private List<FragmentKpiDefinitionViewHolder> viewHolders = new ArrayList<>();

    public FragmentKpiDefinitionListAdapter(int layoutCardView, final FragmentKpiDefinitionList fragment, List<ResponseKPIDefinition> definitions) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.definitions = definitions;
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
        viewHolder.assignData(definitions.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
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

    // ** Filter Settings *********************************************************************** //
    public void handleFilter(String text) {
        filterText = text.toLowerCase();
        for (FragmentKpiDefinitionViewHolder viewHolder : viewHolders) {
            viewHolder.updateVisibility();
        }
    }
}