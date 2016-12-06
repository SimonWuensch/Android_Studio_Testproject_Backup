package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestApplicationConfig {

    private static String TAG = RequestApplicationConfig.class.getSimpleName();

    private static String ADDRESS = "/services/application/config";

    private Project project;

    private RequestApplicationConfig(Project project) {
        this.project = project;
    }

    public static RequestApplicationConfig init(Project project) {
        return new RequestApplicationConfig(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseApplicationConfig(defaultResponse);
    }
}
