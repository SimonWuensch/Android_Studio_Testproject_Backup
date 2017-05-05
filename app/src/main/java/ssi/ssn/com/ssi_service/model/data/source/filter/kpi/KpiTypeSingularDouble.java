package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public class KpiTypeSingularDouble extends FilterKpi {

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
    public boolean check() {
        return false;
    }
}
