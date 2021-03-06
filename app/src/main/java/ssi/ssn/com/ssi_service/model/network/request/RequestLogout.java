package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestLogout {

    private static String TAG = RequestLogout.class.getSimpleName();

    private static String ADDRESS = "/services/security/logout";

    private Project project;

    private RequestLogout(Project project) {
        this.project = project;
    }

    public static RequestLogout init(Project project) {
        return new RequestLogout(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseLogout(defaultResponse);
    }
}
