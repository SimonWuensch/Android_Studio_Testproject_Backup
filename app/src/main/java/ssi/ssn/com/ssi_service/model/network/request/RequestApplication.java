package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestApplication {

    private static String TAG = RequestApplication.class.getSimpleName();

    private static String ADDRESS = "/services/application";

    private Project project;

    private RequestApplication(Project project) {
        this.project = project;
    }

    public static RequestApplication init(Project project) {
        return new RequestApplication(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseApplication(defaultResponse);
    }
}
