package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestModule {

    private static String TAG = RequestOs.class.getSimpleName();

    private static String ADDRESS = "/services/{name}/state";

    public static String ADDITIONAL_MODULE_NAME = TAG + "ADDITIONAL_MODULE_NAME";

    private Project project;
    private String name;

    private RequestModule(Project project, String name) {
        this.project = project;
        this.name = name;
    }

    public static RequestModule init(Project project, String name) {
        if (project.getDefaultResponseModuleList() != null) {
            project.getDefaultResponseModuleList().clear();
        }
        return new RequestModule(project, name);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS
                .replace("{name}", name);
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler) {
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(false);
                defaultResponse.addAdditional(ADDITIONAL_MODULE_NAME, name);
                project.addDefaultResponseModul(defaultResponse);
                return null;
            }
        };
    }
}
