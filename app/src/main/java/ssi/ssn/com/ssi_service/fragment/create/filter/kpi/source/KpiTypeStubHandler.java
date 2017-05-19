package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.source;

import android.app.Activity;
import android.view.ViewStub;

import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.AbstractKpiTypeStub;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubAverage;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSingularDouble;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSingularLong;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSpectrum;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubStatusEvent;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class KpiTypeStubHandler {

    public static AbstractKpiTypeStub initStub(Activity activity, ViewStub vsKpiType, ResponseKpiDefinition definition) {
        return initStub(activity, vsKpiType, definition, null);
    }

    public static AbstractKpiTypeStub initStub(Activity activity, ViewStub vsKpiType, ResponseKpiDefinition definition, FilterKpi filter) {
        if (definition.getType().equals(FilterKpi.KpiTypeSignification.AVERAGE.name())) {
                return filter != null ? StubAverage.initStub(filter, vsKpiType) : StubAverage.initStub(definition, vsKpiType);
        } else if (definition.getType().equals(FilterKpi.KpiTypeSignification.SINGULAR_DOUBLE.name())) {
                return filter != null ? StubSingularDouble.initStub(filter, vsKpiType) : StubSingularDouble.initStub(definition, vsKpiType);
        } else if (definition.getType().equals(FilterKpi.KpiTypeSignification.SINGULAR_LONG.name())) {
                return filter != null ? StubSingularLong.initStub(filter, vsKpiType) : StubSingularLong.initStub(definition, vsKpiType);
        } else if (definition.getType().equals(FilterKpi.KpiTypeSignification.SPECTRUM.name())) {
                return filter != null ? StubSpectrum.initStub(activity, filter, vsKpiType) : StubSpectrum.initStub(activity, definition, vsKpiType);
        } else if (definition.getType().equals(FilterKpi.KpiTypeSignification.STATUS_EVENT.name())) {
                return filter != null ? StubStatusEvent.initStub(filter, vsKpiType) : StubStatusEvent.initStub(definition, vsKpiType);
        }

        throw new NullPointerException("Definition type is unknown. [" + definition.getType() + "]");
    }
}
