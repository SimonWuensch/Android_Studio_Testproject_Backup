package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public class KpiTypeSingularDouble implements KpiType {

    private double value;
    private VerificationObject voValue;

    public KpiTypeSingularDouble() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
        return voValue.check(measurement.getValue(), value);
    }
}
