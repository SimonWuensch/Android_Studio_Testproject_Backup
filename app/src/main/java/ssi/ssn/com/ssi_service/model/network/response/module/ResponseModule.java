package ssi.ssn.com.ssi_service.model.network.response.module;

public class ResponseModule {

    private String name;
    private String status;
    private long since;

    public ResponseModule() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSince() {
        return since;
    }

    public void setSince(long since) {
        this.since = since;
    }
}
