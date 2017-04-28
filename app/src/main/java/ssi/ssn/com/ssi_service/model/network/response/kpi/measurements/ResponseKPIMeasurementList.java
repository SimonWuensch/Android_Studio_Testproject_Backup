package ssi.ssn.com.ssi_service.model.network.response.kpi.measurements;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;


public class ResponseKPIMeasurementList extends AbstractResponse {

    private List<ResponseKPIMeasurement> measurements;

    public ResponseKPIMeasurementList() {
    }

    public List<ResponseKPIMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<ResponseKPIMeasurement> measurements) {
        this.measurements = measurements;
    }
}
