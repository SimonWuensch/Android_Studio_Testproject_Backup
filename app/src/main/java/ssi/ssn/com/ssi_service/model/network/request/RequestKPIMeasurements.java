package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestKPIMeasurements {

    private static String TAG = RequestKPIMeasurements.class.getSimpleName();

    private static String ADDRESS = "/services/kpi/measurements";

    private Project project;

    private RequestKPIMeasurements(Project project) {
        this.project = project;
    }

    public static RequestKPIMeasurements init(Project project) {
        return new RequestKPIMeasurements(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        defaultResponse.setResult("{\"measurements\":" + defaultResponse.getResult() + "}");
        project.setDefaultResponseKPIMeasurements(defaultResponse);
    }
}
