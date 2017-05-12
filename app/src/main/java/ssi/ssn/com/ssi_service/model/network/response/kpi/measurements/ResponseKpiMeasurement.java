package ssi.ssn.com.ssi_service.model.network.response.kpi.measurements;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;

public class ResponseKpiMeasurement extends AbstractResponse {

    private String defKey;
    private String type;
    private String granularity;
    private long timestamp;
    private double value;
    private double avg;
    private long numSamples;
    private double min;
    private double max;
    private double stdDev;
    private long channelIndex;

    public ResponseKpiMeasurement() {
    }

    public String getDefKey() {
        return defKey;
    }

    public void setDefKey(String defKey) {
        this.defKey = defKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public long getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(long numSamples) {
        this.numSamples = numSamples;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public long getChannelIndex() {
        return channelIndex;
    }

    public void setChannelIndex(long channelIndex) {
        this.channelIndex = channelIndex;
    }
}
