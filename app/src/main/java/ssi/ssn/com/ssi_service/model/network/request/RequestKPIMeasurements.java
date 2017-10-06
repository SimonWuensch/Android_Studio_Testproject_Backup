package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestKPIMeasurements {

    private static String TAG = RequestKPIMeasurements.class.getSimpleName();

    private static String ADDRESS = "/services/kpi/measurements";

    private Project project;
    private FilterKpi filter;

    private RequestKPIMeasurements(Project project, FilterKpi filter) {
        this.project = project;
        this.filter = filter;
    }

    public static RequestKPIMeasurements init(Project project, FilterKpi filter) {
        return new RequestKPIMeasurements(project, filter);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS + "/" + filter.getDefinition().getKey();
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.putDefaultResponseKPIMeasurement(filter, defaultResponse);
    }
}
