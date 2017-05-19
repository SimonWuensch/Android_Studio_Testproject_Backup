package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiTypeAverage;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class StubAverage extends AbstractKpiTypeStub {

    private static int STUB_LAYOUT = R.layout.fragment_create_filter_kpi_stub_average;

    private KpiTypeAverage kpiType;

    private View inflatedView;

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

    private StubAverage(FilterKpi filter, ViewStub viewStub) {
        super(filter, viewStub);
        this.kpiType = (KpiTypeAverage) filter.getKpiType();
        initViewComponents();
        fillViewWithFilterInfo();
    }

    private StubAverage(ResponseKpiDefinition definition, ViewStub viewStub) {
        super(definition, viewStub);
        initViewComponents();
    }

    public static StubAverage initStub(FilterKpi filter, ViewStub viewStub) {
        return new StubAverage(filter, viewStub);
    }

    public static StubAverage initStub(ResponseKpiDefinition definition, ViewStub viewStub) {
        return new StubAverage(definition, viewStub);
    }

    @Override
    public void initViewComponents() {
        viewStub.setLayoutResource(STUB_LAYOUT);
        inflatedView = viewStub.inflate();

        etAverage = (EditText) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_average);
        etNumSamples = (EditText) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_numSamples);
        etMinimum = (EditText) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_min);
        etMaximum = (EditText) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_max);
        etStdDev = (EditText) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_stdDev);

        vbAverage = (VerificationButton) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_average);
        vbNumSamples = (VerificationButton) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_numSamples);
        vbMinimum = (VerificationButton) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_min);
        vbMaximum = (VerificationButton) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_max);
        vbStdDev = (VerificationButton) inflatedView.findViewById(R.id.fragment_create_kpi_filter_stub_average_button_verification_object_stdDev);
    }

    @Override
    protected void fillViewWithFilterInfo() {
        etAverage.setText(kpiType.getVoAverage().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getAverage()));
        etNumSamples.setText(kpiType.getVoNumSamples().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getNumSamples()));
        etMinimum.setText(kpiType.getVoMinimum().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getMinimum()));
        etMaximum.setText(kpiType.getVoMaximum().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getMaximum()));
        etStdDev.setText(kpiType.getVoStdDev().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getStdDev()));

        vbAverage.setVerificationObject(kpiType.getVoAverage());
        vbNumSamples.setVerificationObject(kpiType.getVoNumSamples());
        vbMinimum.setVerificationObject(kpiType.getVoMinimum());
        vbMaximum.setVerificationObject(kpiType.getVoMaximum());
        vbStdDev.setVerificationObject(kpiType.getVoStdDev());
    }

    @Override
    public FilterKpi.KpiTypeSignification getKpiTypeSignification() {
        return FilterKpi.KpiTypeSignification.AVERAGE;
    }

    @Override
    public View getInflatedView(){
        return inflatedView;
    }

    @Override
    protected KpiTypeAverage getKpiType() {
        KpiTypeAverage kpiType = new KpiTypeAverage();

        String average = etAverage.getText().toString();
        kpiType.setAverage(average.isEmpty() ? 0.0 : Double.valueOf(etAverage.getText().toString()));
        kpiType.setVoAverage(average.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbAverage.getText()));

        String numSamples = etNumSamples.getText().toString();
        kpiType.setNumSamples(numSamples.isEmpty() ? 0 : Integer.valueOf(etNumSamples.getText().toString()));
        kpiType.setVoNumSamples(numSamples.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbNumSamples.getText()));

        String minimum = etMinimum.getText().toString();
        kpiType.setMinimum(minimum.isEmpty() ? 0.0 : Double.valueOf(etMinimum.getText().toString()));
        kpiType.setVoMinimum(minimum.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbMinimum.getText()));

        String maximum = etMaximum.getText().toString();
        kpiType.setMaximum(maximum.isEmpty() ? 0.0 : Double.valueOf(etMaximum.getText().toString()));
        kpiType.setVoMaximum(maximum.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbMaximum.getText()));

        String stdDev = etStdDev.getText().toString();
        kpiType.setStdDev(stdDev.isEmpty() ? 0.0 : Double.valueOf(etStdDev.getText().toString()));
        kpiType.setVoStdDev(stdDev.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbStdDev.getText()));
        return kpiType;
    }



    @Override
    public boolean isReadyForCreation() {
        if (!etAverage.getText().toString().isEmpty() ||
                !etNumSamples.getText().toString().isEmpty() ||
                !etMinimum.getText().toString().isEmpty() ||
                !etMaximum.getText().toString().isEmpty() ||
                !etStdDev.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<EditText> getAllEditTextViews() {
        return new ArrayList<EditText>() {
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
    public List<VerificationButton> getAllVerificationButtonViews() {
        return new ArrayList<VerificationButton>() {
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
