package ssi.ssn.com.ssi_service.fragment.list.kpifilter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.source.KpiTypeStubHandler;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.AbstractKpiTypeStub;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;


public class FragmentKpiFilterListViewHolder extends RecyclerView.ViewHolder{

    private static String TAG = FragmentKpiFilterListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;
    private TextView tvKeyName;
    private TextView tvDescription;
    private ImageView ivSettings;
    private ViewStub vsKpiType;

    public FragmentKpiFilterListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;

        tvKeyName = (TextView) cardView.findViewById(R.id.fragment_kpi_filter_list_card_view_text_view_key_name);
        tvDescription = (TextView) cardView.findViewById(R.id.fragment_kpi_filter_list_card_view_text_view_description_value);
        ivSettings = (ImageView) cardView.findViewById(R.id.fragment_kpi_filter_list_card_view_image_settings);
        vsKpiType = (ViewStub) cardView.findViewById(R.id.fragment_kpi_filter_list_view_stub_kpi_type);
    }

    protected void assignData(final Project project, final FilterKpi filterKpi){
        tvKeyName.setText(filterKpi.getDefinition().getKey());
        tvDescription.setText(filterKpi.getDefinition().getDescription());
        final AbstractKpiTypeStub stub = KpiTypeStubHandler.initStub(activity, vsKpiType, filterKpi.getDefinition(), filterKpi);
        final View inflatedView = stub.getInflatedView();
        inflatedView.setVisibility(View.GONE);

        for(EditText editText : stub.getAllEditTextViews()){
            editText.setEnabled(false);
            editText.setSelected(false);
            if(editText.getText().toString().isEmpty()){
                ((View) editText.getParent()).setVisibility(View.GONE);
            }
        }
        for(VerificationButton button : stub.getAllVerificationButtonViews()){
            button.setEnabled(false);
        }
        for(Spinner spinner : stub.getAllSpinnerViews()){
            spinner.setEnabled(false);
        }

        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity).showCreateKpiFilterFragment(project.get_id(), filterKpi);
            }
        });

        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(inflatedView.getVisibility() == View.GONE){
                    inflatedView.setVisibility(View.VISIBLE);
                }else{
                    inflatedView.setVisibility(View.GONE);
                }
            }
        });
    }
}
