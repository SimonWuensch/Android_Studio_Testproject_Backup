package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FilterKpi {

    private static String TAG = FilterKpi.class.getSimpleName();

    private int id;
    private ResponseKpiDefinition definition;

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

    public boolean check(){
        throw new NullPointerException("No settings found for filter check...");
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
