package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.sessionscurrent.ResponseSessionsCurrent;

public class RequestLogin {

    private static String TAG = RequestLogin.class.getSimpleName();

    private static String ADDRESS = "/services/security/login/{name}/{password}";

    private Project project;
    private RequestHandler requestHandler;

    private RequestLogin(Project project) {
        this.project = project;
    }

    private RequestLogin(RequestHandler requestHandler, Project project) {
        this.project = project;
        this.requestHandler = requestHandler;
    }

    public static RequestLogin init(RequestHandler requestHandler, Project project) {
        return new RequestLogin(requestHandler, project);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS
                .replace("{name}", project.getUserName())
                .replace("{password}", project.getPassword());
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler) {
        return new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(true);
                project.setDefaultResponseLogin(defaultResponse);
                return null;
            }
        };
    }

    public void addTaskGETtoExecutor(ExecutorService executor, final CookieHandler cookieHandler) {
        requestHandler.getRequestSessionsCurrentTask(project).executeOnExecutor(executor);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                if(project.getDefaultResponseSessionsCurrent().getCode() != 200){
                    return null;
                }else{
                    ResponseSessionsCurrent responseSessionsCurrent = (ResponseSessionsCurrent) JsonHelper.fromJsonGeneric(ResponseSessionsCurrent.class, project.getDefaultResponseSessionsCurrent().getResult());
                    if(responseSessionsCurrent.getStatus().equals(ResponseSessionsCurrent.STATUS_LOGGED_ID)){
                        Log.d(TAG, "User is already logged in. [" + project + "]");
                        return null;
                    }
                }
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(true);
                project.setDefaultResponseLogin(defaultResponse);
                return null;
            }
        }.executeOnExecutor(executor);
    }
}
