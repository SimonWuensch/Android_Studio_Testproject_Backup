package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.response.sessionscurrent.ResponseSessionsCurrent;

public class RequestSessionsCurrent {

    private static String TAG = RequestSessionsCurrent.class.getSimpleName();

    private static String ADDRESS = "/services/security/sessions/current";

    private Project project;

    private RequestSessionsCurrent(Project project) {
        this.project = project;
    }

    public static RequestSessionsCurrent init(Project project) {
        return new RequestSessionsCurrent(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS;
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseSessionsCurrent(defaultResponse);
    }

    public boolean isLoggedIn() {
        if (project.getDefaultResponseSessionsCurrent() != null) {
            ResponseSessionsCurrent responseSessionsCurrent = (ResponseSessionsCurrent) JsonHelper.fromJsonGeneric(ResponseSessionsCurrent.class, project.getDefaultResponseSessionsCurrent().getResult());
            return responseSessionsCurrent.getStatus().equals("LOGGED_IN");
        }
        return false;
    }
}
