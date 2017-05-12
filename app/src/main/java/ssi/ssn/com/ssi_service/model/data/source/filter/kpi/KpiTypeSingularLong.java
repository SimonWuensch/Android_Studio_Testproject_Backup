package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public class KpiTypeSingularLong implements KpiType {

    private long value;
    private VerificationObject voValue;

    public KpiTypeSingularLong() {
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public VerificationObject getVoValue() {
        return voValue;
    }

    public void setVoValue(VerificationObject voValue) {
        this.voValue = voValue;
    }

    @Override
    public boolean check(ResponseKpiMeasurement measurement) {
        return false;
    }
}
