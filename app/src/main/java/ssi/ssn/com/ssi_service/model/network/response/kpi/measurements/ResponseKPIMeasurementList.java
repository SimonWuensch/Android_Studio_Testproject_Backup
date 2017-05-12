package ssi.ssn.com.ssi_service.model.network.response.kpi.measurements;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;


public class ResponseKPIMeasurementList extends AbstractResponse {

    private List<ResponseKpiMeasurement> measurements;

    public ResponseKPIMeasurementList() {
    }

    public List<ResponseKpiMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<ResponseKpiMeasurement> measurements) {
        this.measurements = measurements;
    }
}
