package ssi.ssn.com.ssi_service.model.network.response.component;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;
import ssi.ssn.com.ssi_service.model.network.response.component.objects.ResponseConfig;
import ssi.ssn.com.ssi_service.model.network.response.component.objects.ResponseState;

public class ResponseComponent extends AbstractResponse {

    private String name;
    private ResponseConfig config;
    private ResponseState state;

    public ResponseComponent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResponseConfig getConfig() {
        return config;
    }

    public void setConfig(ResponseConfig config) {
        this.config = config;
    }

    public ResponseState getState() {
        return state;
    }

    public void setState(ResponseState state) {
        this.state = state;
    }
}
