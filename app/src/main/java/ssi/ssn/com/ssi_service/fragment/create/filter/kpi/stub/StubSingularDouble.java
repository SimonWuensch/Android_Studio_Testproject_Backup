package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiTypeSingularDouble;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class StubSingularDouble extends AbstractKpiTypeStub{

    private static int STUB_LAYOUT = R.layout.fragment_create_filter_kpi_stub_singular_double;

    private FilterKpi filter;
    private KpiTypeSingularDouble kpiType;
    private ResponseKpiDefinition definition;
    private ViewStub viewStub;

    private boolean startedWithFilter = false;

    private EditText etValue;
    private VerificationButton vbValue;

    private StubSingularDouble(FilterKpi filter, ViewStub viewStub){
        this.viewStub = viewStub;

        this.filter = filter;
        this.definition = filter.getDefinition();
        this.kpiType = (KpiTypeSingularDouble) filter.getKpiType();
        startedWithFilter = true;

        initViewComponents();
        fillViewWithFilterInfo();
    }

    private StubSingularDouble(ResponseKpiDefinition definition, ViewStub viewStub){
        this.viewStub = viewStub;
        this.definition = definition;
        initViewComponents();
    }

    public static StubSingularDouble initStub(FilterKpi filter, ViewStub viewStub) {
        return new StubSingularDouble(filter, viewStub);
    }

    public static StubSingularDouble initStub(ResponseKpiDefinition definition, ViewStub viewStub) {
        return new StubSingularDouble(definition, viewStub);
    }

    public void initViewComponents(){
        viewStub.setLayoutResource(STUB_LAYOUT);
        View inflated = viewStub.inflate();

        etValue = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_singular_double_edit_text_value);
        vbValue = (VerificationButton) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_singular_double_button_verification_object_value);
    }

    private void fillViewWithFilterInfo(){
        etValue.setText(String.valueOf(kpiType.getValue()));
        vbValue.setVerificationObject(kpiType.getVoValue());
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

        newFilter.setSignification(FilterKpi.KpiTypeSignification.SINGULAR_DOUBLE);
        newFilter.setKpiType(getKpiType());
        return newFilter;
    }

    private KpiTypeSingularDouble getKpiType(){
        KpiTypeSingularDouble kpiType = new KpiTypeSingularDouble();

        kpiType.setValue(Double.valueOf(etValue.getText().toString()));
        kpiType.setVoValue(getVerificationObjectByIcon((String) vbValue.getText()));
        return kpiType;
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
}
