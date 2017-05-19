package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiType;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.KpiTypeAverage;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class AbstractKpiTypeStub {

    protected Activity activity;
    protected FilterKpi filter;
    protected ViewStub viewStub;
    protected ResponseKpiDefinition definition;

    protected boolean startedWithFilter = false;

    public AbstractKpiTypeStub(FilterKpi filter, ViewStub viewStub){
        this.filter = filter;
        this.viewStub = viewStub;
        this.definition = filter.getDefinition();
        startedWithFilter = true;
    }

    public AbstractKpiTypeStub(ResponseKpiDefinition definition, ViewStub viewStub){
        this.viewStub = viewStub;
        this.definition = definition;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void initViewComponents(){
        throw new NullPointerException("No settings found...");
    }

    protected void fillViewWithFilterInfo(){
        throw new NullPointerException("No settings found...");
    }

    public FilterKpi.KpiTypeSignification getKpiTypeSignification(){
        throw new NullPointerException("No settings found...");
    }

    public FilterKpi loadFilterFromComponentViews() {
        FilterKpi newFilter = new FilterKpi();
        if(startedWithFilter){
            newFilter.setId(filter.getId());
            newFilter.setDefinition(filter.getDefinition());
        }else{
            newFilter.setDefinition(definition);
        }

        newFilter.setSignification(getKpiTypeSignification());
        newFilter.setKpiType(getKpiType());
        return newFilter;
    }

    protected KpiType getKpiType(){
        throw new NullPointerException("No settings found...");
    }

    public View getInflatedView(){
        throw new NullPointerException("No settings found...");
    }

    public List<EditText> getAllEditTextViews() {
        return new ArrayList<>();
    }

    public List<VerificationButton> getAllVerificationButtonViews() {
        return new ArrayList<>();
    }

    public List<Spinner> getAllSpinnerViews() {
        return new ArrayList<>();
    }

    public boolean isReadyForCreation(){
        throw new NullPointerException("No settings found...");
    }

    VerificationObject getVerificationObjectByIcon(String icon) {
        switch (icon) {
            case "<":
                return VerificationObject.LESS_THEN;
            case "<=":
                return VerificationObject.LESS_THEN_EQUALS;
            case ">":
                return VerificationObject.GREATER_THEN;
            case ">=":
                return VerificationObject.GREATER_THEN_EQUALS;
            case "=":
                return VerificationObject.EQUALS;
        }
        throw new NullPointerException("No " + VerificationObject.class.getSimpleName() + " found to icon: [" + icon + "].");
    }
}
