package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import com.owlike.genson.annotation.JsonIgnore;

import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FilterKpi {

    private static String TAG = FilterKpi.class.getSimpleName();

    private int id;
    private ResponseKpiDefinition definition;

    private FilterKpi.KpiTypeSignification signification;
    private String jsonKpiType;

    @JsonIgnore
    private KpiType kpiType;

    public FilterKpi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResponseKpiDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ResponseKpiDefinition definition) {
        this.definition = definition;
    }

    public KpiTypeSignification getSignification() {
        return signification;
    }

    public void setSignification(KpiTypeSignification signification) {
        this.signification = signification;
    }

    public String getJsonKpiType() {
        return jsonKpiType;
    }

    public void setJsonKpiType(String jsonKpiType) {
        this.jsonKpiType = jsonKpiType;
    }

    // ** Json serialisation exclusion ********************************************************** //

    @JsonIgnore
    public KpiType getKpiType() {
        deserializeJsonKpiType();
        return kpiType;
    }

    @JsonIgnore
    public void setKpiType(KpiType kpiType) {
        jsonKpiType = JsonHelper.toJson(kpiType);
        this.kpiType = kpiType;
    }

    private void deserializeJsonKpiType(){
        switch(signification){
            case AVERAGE:
                kpiType = (KpiTypeAverage) JsonHelper.fromJsonGeneric(KpiTypeAverage.class, jsonKpiType);
                break;
            case SINGULAR_DOUBLE:
                kpiType = (KpiTypeSingularDouble) JsonHelper.fromJsonGeneric(KpiTypeSingularDouble.class, jsonKpiType);
                break;
            case SINGULAR_LONG:
                kpiType = (KpiTypeSingularLong) JsonHelper.fromJsonGeneric(KpiTypeSingularLong.class, jsonKpiType);
                break;
            case SPECTRUM:
                kpiType = (KpiTypeSpectrum) JsonHelper.fromJsonGeneric(KpiTypeSpectrum.class, jsonKpiType);
                break;
            case STATUS_EVENT:
                kpiType = (KpiTypeStatusEvent) JsonHelper.fromJsonGeneric(KpiTypeStatusEvent.class, jsonKpiType);
                break;
        }
    }

    public String identity() {
        return "[" + getId() + ";" + getDefinition() + "]";
    }

    // ** Filter Significations ***************************************************************** //
    public enum KpiTypeSignification {
        AVERAGE,
        SPECTRUM,
        SINGULAR_LONG,
        SINGULAR_DOUBLE,
        STATUS_EVENT;
    }


}
