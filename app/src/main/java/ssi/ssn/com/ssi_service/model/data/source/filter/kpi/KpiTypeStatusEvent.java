package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public class KpiTypeStatusEvent extends FilterKpi {

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
    public boolean check() {
        return false;
    }

}
