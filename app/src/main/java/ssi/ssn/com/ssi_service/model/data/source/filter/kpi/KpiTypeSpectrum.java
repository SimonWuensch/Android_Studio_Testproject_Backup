package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public class KpiTypeSpectrum implements KpiType {

    private double value;
    private VerificationObject voValue;
    private int channelIndex;
    private VerificationObject voChannelIndex;

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

    public VerificationObject getVoChannelIndex() {
        return voChannelIndex;
    }

    public void setVoChannelIndex(VerificationObject voChannelIndex) {
        this.voChannelIndex = voChannelIndex;
    }

    @Override
    public boolean check(FilterKpi filter, ResponseKpiMeasurement measurement) {
        //TODO Check value
        return false;
    }
}
