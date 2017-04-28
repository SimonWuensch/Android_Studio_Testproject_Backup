package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestKPIDefinitions {

    private static String TAG = RequestKPIDefinitions.class.getSimpleName();

    private static String ADDRESS = "/services/kpi/definitions";

    private Project project;

    private RequestKPIDefinitions(Project project) {
        this.project = project;
    }

    public static RequestKPIDefinitions init(Project project) {
        return new RequestKPIDefinitions(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        defaultResponse.setResult("{\"definitions\":" + defaultResponse.getResult() + "}");
        project.setDefaultResponseKPIDefinitions(defaultResponse);
    }
}
