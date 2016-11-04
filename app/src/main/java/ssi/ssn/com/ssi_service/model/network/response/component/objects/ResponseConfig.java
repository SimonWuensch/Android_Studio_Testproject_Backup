package ssi.ssn.com.ssi_service.model.network.response.component.objects;

public class ResponseConfig {

    private String home;
    private String address;
    private boolean manage;
    private long startTimeout;
    private long stopTimeout;
    private long overwatch;

    public ResponseConfig() {
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
    }

    public long getStartTimeout() {
        return startTimeout;
    }

    public void setStartTimeout(long startTimeout) {
        this.startTimeout = startTimeout;
    }

    public long getStopTimeout() {
        return stopTimeout;
    }

    public void setStopTimeout(long stopTimeout) {
        this.stopTimeout = stopTimeout;
    }

    public long getOverwatch() {
        return overwatch;
    }

    public void setOverwatch(long overwatch) {
        this.overwatch = overwatch;
    }
}
