package ssi.ssn.com.ssi_service.fragment.list.kpifilter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;


public class FragmentKpiFilterListViewHolder extends RecyclerView.ViewHolder{

    private static String TAG = FragmentKpiFilterListViewHolder.class.getSimpleName();

    private Activity activity;
    private final TextView tvTextView;
    private View cardView;

    public FragmentKpiFilterListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        tvTextView = ((TextView) cardView.findViewById(R.id.fragment_standard_text_view));
    }

    protected void assignData(final Project project, final FilterKpi filterKpi){
        tvTextView.setText(filterKpi.getDefinition().getName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).showCreateKpiFilterFragment(project.get_id(), filterKpi);
            }
        });

    }
}
