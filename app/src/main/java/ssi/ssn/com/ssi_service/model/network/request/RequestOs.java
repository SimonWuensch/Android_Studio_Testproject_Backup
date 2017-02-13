package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestOs {

    private static String TAG = RequestOs.class.getSimpleName();

    private static String ADDRESS = "/services/application/diagnostics/os";

    private Project project;

    private RequestOs(Project project) {
        this.project = project;
    }

    public static RequestOs init(Project project) {
        return new RequestOs(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseOs(defaultResponse);
    }
}
