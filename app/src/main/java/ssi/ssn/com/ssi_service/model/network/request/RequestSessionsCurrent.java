package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.response.ResponseSessionsCurrent;

public class RequestSessionsCurrent {

    private static String TAG = RequestSessionsCurrent.class.getSimpleName();

    private static String ADDRESS = "/services/security/sessions/current";

    private Project project;

    private RequestSessionsCurrent(Project project) {
        this.project = project;
    }

    public static RequestSessionsCurrent init(Project project){
        return new RequestSessionsCurrent(project);
    }

    private String getAddress(){
        return project.getServerAddress() + ADDRESS;
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler){
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(false);
                project.setDefaultResponseSessionsCurrent(defaultResponse);
                return null;
            }
        };
    }

    public boolean isLoggedIn(){
        if(project.getDefaultResponseSessionsCurrent() != null){
            ResponseSessionsCurrent responseSessionsCurrent = (ResponseSessionsCurrent) JsonHelper.fromJsonGeneric(ResponseSessionsCurrent.class, project.getDefaultResponseSessionsCurrent().getResult());
            return responseSessionsCurrent.getStatus().equals("LOGGED_IN");
        }
        return false;
    }
}
