package ssi.ssn.com.ssi_service.model.network.response.kpi.definitions;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;


public class ResponseKpiDefinitionList extends AbstractResponse {

    private List<ResponseKpiDefinition> definitions;

    public ResponseKpiDefinitionList() {
    }

    public List<ResponseKpiDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<ResponseKpiDefinition> definitions) {
        this.definitions = definitions;
    }
}
