package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

public class KpiTypeAverage extends FilterKpi {

    private double average;
    private VerificationObject voAverage;
    private int numSamples;
    private VerificationObject voNumSamples;
    private double minimum;
    private VerificationObject voMinimum;
    private double maximum;
    private VerificationObject voMaximum;
    private double stdDev;
    private VerificationObject voStdDev;

    public KpiTypeAverage() {
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public VerificationObject getVoAverage() {
        return voAverage;
    }

    public void setVoAverage(VerificationObject voAverage) {
        this.voAverage = voAverage;
    }

    public int getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(int numSamples) {
        this.numSamples = numSamples;
    }

    public VerificationObject getVoNumSamples() {
        return voNumSamples;
    }

    public void setVoNumSamples(VerificationObject voNumSamples) {
        this.voNumSamples = voNumSamples;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public VerificationObject getVoMinimum() {
        return voMinimum;
    }

    public void setVoMinimum(VerificationObject voMinimum) {
        this.voMinimum = voMinimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public VerificationObject getVoMaximum() {
        return voMaximum;
    }

    public void setVoMaximum(VerificationObject voMaximum) {
        this.voMaximum = voMaximum;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public VerificationObject getVoStdDev() {
        return voStdDev;
    }

    public void setVoStdDev(VerificationObject voStdDev) {
        this.voStdDev = voStdDev;
    }

    @Override
    public boolean check() {
        return false;
    }
}
