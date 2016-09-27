package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.Response;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestOs {

    private static String TAG = RequestOs.class.getSimpleName();

    private static String ADDRESS = "/services/application/diagnostics/os";

    private Project project;

    private RequestOs(Project project) {
        this.project = project;
    }

    public static RequestOs init(Project project){
        return new RequestOs(project);
    }

    private String getAddress(){
        return project.getServerAddress() + ADDRESS;
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler){
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                Response response = httpGET.sendRequest(false);
                project.setResponseOs(response);
                return null;
            }
        };
    }
}
