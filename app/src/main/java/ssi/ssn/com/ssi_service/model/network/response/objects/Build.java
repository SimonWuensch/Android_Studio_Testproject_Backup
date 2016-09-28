package ssi.ssn.com.ssi_service.model.network.response.objects;

/**
 * Created by wuens on 28.09.2016.
 */

public class Build {

    public String version;
    public long number;
    public String builtBy;
    public long builtOn;

    public Build() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(String builtBy) {
        this.builtBy = builtBy;
    }

    public long getBuiltOn() {
        return builtOn;
    }

    public void setBuiltOn(long builtOn) {
        this.builtOn = builtOn;
    }
}

