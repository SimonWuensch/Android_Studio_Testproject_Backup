package ssi.ssn.com.ssi_service.model.network.response.kpi.definitions;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;


public class ResponseKPIDefinitionList extends AbstractResponse {

    private List<ResponseKPIDefinition> definitions;

    public ResponseKPIDefinitionList() {
    }

    public List<ResponseKPIDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<ResponseKPIDefinition> definitions) {
        this.definitions = definitions;
    }
}
