package ssi.ssn.com.ssi_service.model.network.response.kpi.definitions;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;


public class ResponseKPIDefinitionList extends AbstractResponse {

    private List<ResponseKpiDefinition> definitions;

    public ResponseKPIDefinitionList() {
    }

    public List<ResponseKpiDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<ResponseKpiDefinition> definitions) {
        this.definitions = definitions;
    }
}
