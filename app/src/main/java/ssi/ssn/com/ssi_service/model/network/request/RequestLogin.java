package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.Response;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestLogin {

    private static String TAG = RequestLogin.class.getSimpleName();

    private static String ADDRESS = "/services/security/login/{name}/{password}";

    private Project project;

    private RequestLogin(Project project) {
        this.project = project;
    }

    public static RequestLogin init(Project project) {
        return new RequestLogin(project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS
                .replace("{name}", project.getUserName())
                .replace("{password}", project.getPassword());
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler) {
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                Response response = httpGET.sendRequest(true);
                project.setResponseLogin(response);
                return null;
            }
        };
    }
}
