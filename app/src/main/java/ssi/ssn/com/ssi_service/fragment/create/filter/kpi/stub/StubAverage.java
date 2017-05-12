package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiTypeAverage;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class StubAverage extends AbstractKpiTypeStub{

    private static int STUB_LAYOUT = R.layout.fragment_create_filter_kpi_stub_average;

    private FilterKpi filter;
    private KpiTypeAverage kpiType;
    private ResponseKpiDefinition definition;
    private ViewStub viewStub;

    private boolean startedWithFilter = false;

    private EditText etAverage;
    private EditText etNumSamples;
    private EditText etMinimum;
    private EditText etMaximum;
    private EditText etStdDev;

    private VerificationButton vbAverage;
    private VerificationButton vbNumSamples;
    private VerificationButton vbMinimum;
    private VerificationButton vbMaximum;
    private VerificationButton vbStdDev;

    private StubAverage(FilterKpi filter, ViewStub viewStub){
        this.viewStub = viewStub;

        this.filter = filter;
        this.definition = filter.getDefinition();
        this.kpiType = (KpiTypeAverage) filter.getKpiType();
        startedWithFilter = true;

        initViewComponents();
        fillViewWithFilterInfo();
    }

    private StubAverage(ResponseKpiDefinition definition, ViewStub viewStub){
        this.viewStub = viewStub;
        this.definition = definition;
        initViewComponents();
    }

    public static StubAverage initStub(FilterKpi filter, ViewStub viewStub) {
        return new StubAverage(filter, viewStub);
    }

    public static StubAverage initStub(ResponseKpiDefinition definition, ViewStub viewStub) {
        return new StubAverage(definition, viewStub);
    }

    public void initViewComponents(){
        viewStub.setLayoutResource(STUB_LAYOUT);
        View inflated = viewStub.inflate();

        etAverage = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_average);
        etNumSamples = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_numSamples);
        etMinimum = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_min);
        etMaximum = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_max);
        etStdDev = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_stdDev);

        vbAverage = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_average);
        vbNumSamples = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_numSamples);
        vbMinimum = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_min);
        vbMaximum = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_max);
        vbStdDev = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_stdDev);
    }

    private void fillViewWithFilterInfo(){
        etAverage.setText(String.valueOf(kpiType.getAverage()));
        etNumSamples.setText(String.valueOf(kpiType.getNumSamples()));
        etMinimum.setText(String.valueOf(kpiType.getMinimum()));
        etMaximum.setText(String.valueOf(kpiType.getMaximum()));
        etStdDev.setText(String.valueOf(kpiType.getStdDev()));

        vbAverage.setVerificationObject(kpiType.getVoAverage());
        vbNumSamples.setVerificationObject(kpiType.getVoNumSamples());
        vbMinimum.setVerificationObject(kpiType.getVoMinimum());
        vbMaximum.setVerificationObject(kpiType.getVoMaximum());
        vbStdDev.setVerificationObject(kpiType.getVoStdDev());
    }

    @Override
    public FilterKpi loadFilterFromComponentViews(){
        FilterKpi newFilter = new FilterKpi();
        if(startedWithFilter){
            newFilter.setId(filter.getId());
            newFilter.setDefinition(filter.getDefinition());
        }else{
            newFilter.setDefinition(definition);
        }

        newFilter.setSignification(FilterKpi.KpiTypeSignification.AVERAGE);
        newFilter.setKpiType(getKpiType());
        return newFilter;
    }

    private KpiTypeAverage getKpiType(){
        KpiTypeAverage kpiType = new KpiTypeAverage();

        kpiType.setAverage(Double.valueOf(etAverage.getText().toString()));
        kpiType.setVoAverage(getVerificationObjectByIcon((String) vbAverage.getText()));

        kpiType.setNumSamples(Integer.valueOf(etNumSamples.getText().toString()));
        kpiType.setVoNumSamples(getVerificationObjectByIcon((String) vbNumSamples.getText()));

        kpiType.setMinimum(Double.valueOf(etMinimum.getText().toString()));
        kpiType.setVoMinimum(getVerificationObjectByIcon((String) vbMinimum.getText()));

        kpiType.setMaximum(Double.valueOf(etMaximum.getText().toString()));
        kpiType.setVoMaximum(getVerificationObjectByIcon((String) vbMaximum.getText()));

        kpiType.setStdDev(Double.valueOf(etStdDev.getText().toString()));
        kpiType.setVoStdDev(getVerificationObjectByIcon((String) vbStdDev.getText()));
        return kpiType;
    }

    @Override
    public List<EditText> getAllEditTextViews(){
        return new ArrayList<EditText>(){
            {
                add(etAverage);
                add(etNumSamples);
                add(etMinimum);
                add(etMaximum);
                add(etStdDev);
            }
        };
    }

    @Override
    public List<VerificationButton> getAllVerificationButtonViews(){
        return new ArrayList<VerificationButton>(){
            {
                add(vbAverage);
                add(vbNumSamples);
                add(vbMinimum);
                add(vbMaximum);
                add(vbStdDev);
            }
        };
    }
}
