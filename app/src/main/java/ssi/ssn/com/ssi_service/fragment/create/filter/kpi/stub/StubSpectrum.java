package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiTypeSpectrum;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class StubSpectrum extends AbstractKpiTypeStub{

    private static int STUB_LAYOUT = R.layout.fragment_create_filter_kpi_stub_spectrum;

    private KpiTypeSpectrum kpiType;

    private EditText etValue;
    private VerificationButton vbValue;
    private Spinner spCategories;

    private StubSpectrum(Activity activity, FilterKpi filter, ViewStub viewStub){
        super(filter, viewStub);
        super.setActivity(activity);
        this.kpiType = (KpiTypeSpectrum) filter.getKpiType();
        initViewComponents();
        fillViewWithFilterInfo();
    }

    private StubSpectrum(Activity activity, ResponseKpiDefinition definition, ViewStub viewStub){
        super(definition, viewStub);
        super.setActivity(activity);
        initViewComponents();
    }

    public static StubSpectrum initStub(Activity activity, FilterKpi filter, ViewStub viewStub) {
        return new StubSpectrum(activity, filter, viewStub);
    }

    public static StubSpectrum initStub(Activity activity, ResponseKpiDefinition definition, ViewStub viewStub) {
        return new StubSpectrum(activity, definition, viewStub);
    }

    @Override
    public void initViewComponents(){
        viewStub.setLayoutResource(STUB_LAYOUT);
        View inflated = viewStub.inflate();

        etValue = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_spectrum_edit_text_value);
        vbValue = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_spectrum_button_verification_object_value);
        spCategories = (Spinner) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_spectrum_spinner_categories);


        List<String> spectrumCategories = new ArrayList<String>(Arrays.asList(definition.getSpectrumCategories()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_spinner_item, spectrumCategories);
        adapter.setDropDownViewResource(R.layout.default_spinner_drop_down_item);
        spCategories.setAdapter(adapter);
        spCategories.setSelection(0);
    }

    @Override
    protected void fillViewWithFilterInfo(){
        etValue.setText(kpiType.getVoValue().equals(VerificationObject.IGNORE) ? "" : String.valueOf(kpiType.getValue()));
        vbValue.setVerificationObject(kpiType.getVoValue());
        spCategories.setSelection(kpiType.getChannelIndex());
    }

    @Override
    public FilterKpi.KpiTypeSignification getKpiTypeSignification(){
        return FilterKpi.KpiTypeSignification.SPECTRUM;
    }

    @Override
    protected KpiTypeSpectrum getKpiType(){
        KpiTypeSpectrum kpiType = new KpiTypeSpectrum();
        String value = etValue.getText().toString();
        kpiType.setValue(value.isEmpty() ? 0.0 : Double.valueOf(etValue.getText().toString()));
        kpiType.setVoValue(value.isEmpty() ? VerificationObject.IGNORE : getVerificationObjectByIcon((String) vbValue.getText()));
        kpiType.setChannelIndex(spCategories.getSelectedItemPosition());
        kpiType.setVoChannelIndex(VerificationObject.EQUALS);
        return kpiType;
    }

    @Override
    public boolean isReadyForCreation() {
        if (!etValue.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public List<EditText> getAllEditTextViews(){
        return new ArrayList<EditText>(){
            {
                add(etValue);
            }
        };
    }

    @Override
    public List<VerificationButton> getAllVerificationButtonViews(){
        return new ArrayList<VerificationButton>(){
            {
                add(vbValue);
            }
        };
    }

    @Override
    public List<Spinner> getAllSpinnerViews() {
        return new ArrayList<Spinner>(){
            {
                add(spCategories);
            }
        };
    }
}
