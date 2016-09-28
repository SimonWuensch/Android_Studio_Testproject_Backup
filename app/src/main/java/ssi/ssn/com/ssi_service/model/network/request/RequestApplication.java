package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
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

    public AsyncTask getTaskGET(final CookieHandler cookieHandler) {
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(false);
                project.setDefaultResponseApplication(defaultResponse);
                return null;
            }
        };
    }
}
