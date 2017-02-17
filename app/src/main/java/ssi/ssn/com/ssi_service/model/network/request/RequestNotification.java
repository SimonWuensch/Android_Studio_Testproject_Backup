package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;


public class RequestNotification {

    private static String TAG = RequestNotification.class.getSimpleName();

    private static String ADDRESS = "/services/notification";

    private Project project;
    private RequestNotification.Path path;

    private RequestNotification(Project project, RequestNotification.Path path) {
        this.project = project;
        this.path = path;
    }

    public static RequestNotification init(Project project, RequestNotification.Path path) {
        return new RequestNotification(project, path);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS + path.getUrl();
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);

        switch (path) {
            case TABLE_ALL:
            case TABLE_ERROR:
            case TABLE_WARNING:
                project.setDefaultResponseNotification(defaultResponse);
                break;
            case COUNT_ALL:
            case COUNT_ERROR:
            case COUNT_WARNING:
                project.setDefaultResponseNotificationCount(defaultResponse);
                break;
        }
    }

    // *** Path ********************************************************************************* //
    public enum Path {
        TABLE_ALL("/table", "&condition=$<<active>>"),
        TABLE_ERROR("/table", "&condition=$<<active>> AND ($<<definition.severity>> = 'ERROR'))"),
        TABLE_WARNING("/table", "&condition=$<<active>> AND ($<<definition.severity>> = 'WARN'))"),
        COUNT_ALL("/count", "&condition=$<<active>>"),
        COUNT_ERROR("/count", "&condition=$<<active>> AND ($<<definition.severity>> = 'ERROR'))"),
        COUNT_WARNING("/count", "&condition=$<<active>> AND ($<<definition.severity>> = 'WARN'))");

        private String defaultQuery = "?orderBy=$<<startTime>> DESC";
        private String url;

        Path(String url) {
            this.url = url + defaultQuery + "&condition=$<<active>>";
        }

        Path(String url, String query) {
            this.url = url + defaultQuery + query;
        }

        public String getUrl() {
            return url;
        }
    }
}
