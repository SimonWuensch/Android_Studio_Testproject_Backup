package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public class KpiTypeSpectrum extends FilterKpi {

    private double value;
    private VerificationObject voValue;
    private int channelIndex;

    public KpiTypeSpectrum() {
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

    public int getChannelIndex() {
        return channelIndex;
    }

    public void setChannelIndex(int channelIndex) {
        this.channelIndex = channelIndex;
    }

    @Override
    public boolean check() {
        return false;
    }
}
