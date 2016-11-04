package ssi.ssn.com.ssi_service.model.network.response.application.objects;

public class ResponseTime {

    private long stamp;
    private long offset;

    public ResponseTime() {
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
