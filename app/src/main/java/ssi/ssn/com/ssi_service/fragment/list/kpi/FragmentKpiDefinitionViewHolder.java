package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKPIDefinition;

public class FragmentKpiDefinitionViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentKpiDefinitionViewHolder.class.getSimpleName();

    private Activity activity;
    private FragmentKpiDefinitionListAdapter adapter;
    private TextView tvHeadLine;
    private TextView tvDescription;
    private View cardView;

    private ResponseKPIDefinition definition;

    public FragmentKpiDefinitionViewHolder(Activity activity, FragmentKpiDefinitionListAdapter adapter, View cardView) {
        super(cardView);
        this.activity = activity;
        this.adapter = adapter;
        this.cardView = cardView;
        tvHeadLine = ((TextView) cardView.findViewById(R.id.fragment_kpi_definition_list_headline));
        tvDescription = ((TextView) cardView.findViewById(R.id.fragment_kpi_definition_list_description_value));
    }

    protected void assignData(final ResponseKPIDefinition definition) {
        this.definition = definition;
        updateVisibility();
        updateHeadline();
        tvDescription.setText(definition.getDescription());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void updateVisibility() {
        if (definition == null) {
            return;
        }

        String key = definition.getKey().toLowerCase();
        String name = definition.getName() == null ? "" : definition.getName().toLowerCase();


        // TODO fix error...  Cannot call this method while RecyclerView is computing a layout or scrolling
        /*if (adapter.filterText.isEmpty()) {
            //cardView.setVisibility(View.VISIBLE);
            if(adapter.definitions.contains(definition)){
                return;
            }
            adapter.definitions.add(getAdapterPosition(), definition);
            adapter.notifyItemInserted(getAdapterPosition());
            adapter.notifyItemRangeChanged(getAdapterPosition(),  adapter.definitions.size());

        } else if (name.contains(adapter.filterText) || key.contains(adapter.filterText)) {
            //cardView.setVisibility(View.VISIBLE);
            if(adapter.definitions.contains(definition)){
                return;
            }
            adapter.definitions.add(getAdapterPosition(), definition);
            adapter.notifyItemInserted(getAdapterPosition());
            adapter.notifyItemRangeChanged(getAdapterPosition(),  adapter.definitions.size());
        } else {
            //cardView.setVisibility(View.GONE);
            if(!adapter.definitions.contains(definition)){
                return;
            }
            adapter.definitions.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
            adapter.notifyItemRangeChanged(getAdapterPosition(),  adapter.definitions.size());
        }*/
    }

    protected void updateHeadline() {
        tvHeadLine.setText(adapter.headlineType == FragmentKpiDefinitionListAdapter.HEADLINE_TYPE_KEY ? definition.getKey() : definition.getName() == null ? definition.getKey() : definition.getName());
    }
}
