package ssi.ssn.com.ssi_service.model.data.source.filter.kpi;

import android.app.Activity;
import android.widget.ArrayAdapter;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public interface KpiType {

    boolean check(ResponseKpiMeasurement measurement);
}
