package ssi.ssn.com.ssi_service.model.network.response.application.objects;

/**
 * Created by wuens on 28.09.2016.
 */

public class ResponseState {

    public long since;
    public String status;

    public ResponseState() {
    }

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
