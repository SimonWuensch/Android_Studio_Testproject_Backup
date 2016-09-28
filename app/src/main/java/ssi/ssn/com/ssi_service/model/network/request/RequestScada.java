package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestScada {

    private static String TAG = RequestScada.class.getSimpleName();

    private static String ADDRESS = "/services/scada";

    private Project project;
    private RequestScada.Path path;

    private RequestScada(Project project, RequestScada.Path path) {
        this.project = project;
        this.path = path;
    }

    public static RequestScada init(Project project, RequestScada.Path path) {
        return new RequestScada(project, path);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS + path.getUrl();
    }

    public AsyncTask getTaskGET(final CookieHandler cookieHandler) {
        return new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
                DefaultResponse defaultResponse = httpGET.sendRequest(false);
                project.setDefaultResponseScada(defaultResponse);
                return null;
            }
        };
    }

    // *** Path ********************************************************************************* //
    public enum Path {
        SYSTEMS("/systems"),
        SCANNERS("/scanners");

        private String url;

        Path(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}
