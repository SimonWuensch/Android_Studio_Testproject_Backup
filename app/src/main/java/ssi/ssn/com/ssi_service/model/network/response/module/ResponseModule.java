package ssi.ssn.com.ssi_service.model.network.response.module;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;

public class ResponseModule extends AbstractResponse {

    private String name;
    private String status;
    private long since;
    private String enabled;
    private String xmlModuleName;

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

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getXmlModuleName() {
        return xmlModuleName;
    }

    public void setXmlModuleName(String xmlModuleName) {
        this.xmlModuleName = xmlModuleName;
    }
}
