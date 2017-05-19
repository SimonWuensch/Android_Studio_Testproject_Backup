package ssi.ssn.com.ssi_service.fragment.list.kpi;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FragmentKpiDefinitionViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentKpiDefinitionViewHolder.class.getSimpleName();

    private Activity activity;
    private FragmentKpiDefinitionListAdapter adapter;
    private TextView tvHeadLine;
    private TextView tvDescription;
    private View cardView;

    private ResponseKpiDefinition definition;

    public FragmentKpiDefinitionViewHolder(Activity activity, FragmentKpiDefinitionListAdapter adapter, View cardView) {
        super(cardView);
        this.activity = activity;
        this.adapter = adapter;
        this.cardView = cardView;
        tvHeadLine = ((TextView) cardView.findViewById(R.id.fragment_kpi_definition_list_headline));
        tvDescription = ((TextView) cardView.findViewById(R.id.fragment_kpi_definition_list_description_value));
    }

    protected void assignData(final Project project, final ResponseKpiDefinition definition) {
        this.definition = definition;
        updateHeadline();
        tvDescription.setText(definition.getDescription());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (definition.getType().equals(FilterKpi.KpiTypeSignification.AVERAGE.name()) ||
                        definition.getType().equals(FilterKpi.KpiTypeSignification.SINGULAR_DOUBLE.name()) ||
                        definition.getType().equals(FilterKpi.KpiTypeSignification.SINGULAR_LONG.name()) ||
                        definition.getType().equals(FilterKpi.KpiTypeSignification.SPECTRUM.name()) ||
                        definition.getType().equals(FilterKpi.KpiTypeSignification.STATUS_EVENT.name())) {
                    ((MainActivity) activity).showCreateKpiFilterFragment(project.get_id(), definition);
                } else {
                    Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_kpi_definition_list_type_is_not_known) + definition.getType(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void updateHeadline() {
        tvHeadLine.setText(adapter.headlineType == FragmentKpiDefinitionListAdapter.HEADLINE_TYPE_KEY ? definition.getKey() : definition.getName() == null ? definition.getKey() : definition.getName());
    }
}
