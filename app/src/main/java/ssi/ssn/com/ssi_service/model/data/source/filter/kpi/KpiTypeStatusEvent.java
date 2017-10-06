package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public class KpiTypeStatusEvent implements KpiType {

    private String value;
    private VerificationObject voValue;

    public KpiTypeStatusEvent() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VerificationObject getVoValue() {
        return voValue;
    }

    public void setVoValue(VerificationObject voValue) {
        this.voValue = voValue;
    }

    @Override
    public boolean check(FilterKpi filter, ResponseKpiMeasurement measurement) {
        //TODO Check value
        return false;
    }

}
