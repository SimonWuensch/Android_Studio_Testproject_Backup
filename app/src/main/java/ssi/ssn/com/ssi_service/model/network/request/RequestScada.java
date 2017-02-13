package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
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

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.setDefaultResponseScada(defaultResponse);
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
