package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public interface KpiType {

    boolean check(FilterKpi filter, ResponseKpiMeasurement measurement);
}
