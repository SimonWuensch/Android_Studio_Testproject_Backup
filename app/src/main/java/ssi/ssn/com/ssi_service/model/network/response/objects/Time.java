package ssi.ssn.com.ssi_service.model.network.response.objects;

/**
 * Created by wuens on 28.09.2016.
 */

public class Time {

    public long stamp;
    public long offset;

    public Time(){
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}