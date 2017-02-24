package ssi.ssn.com.ssi_service.model.network.request;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;


public class RequestNotification {

    private static String TAG = RequestNotification.class.getSimpleName();

    private static String ADDRESS = "/services/notification";
    private static String PLACEHOLDER_SEVERITY = "PLACEHOLDER_SEVERITY";
    private static String PLACEHOLDER_TEXT = "PLACEHOLDER_TEXT";
    private static String PLACEHOLDER_NODE = "PLACEHOLDER_NODE";

    private Project project;
    private FilterNotification filter;
    private RequestNotification.Path path;

    private RequestNotification(Project project, RequestNotification.Path path) {
        this.project = project;
        this.path = path;
    }

    private RequestNotification(Project project, FilterNotification filter, RequestNotification.Path path) {
        this.project = project;
        this.path = path;
        this.filter = filter;
    }


    public static RequestNotification init(Project project, RequestNotification.Path path) {
        return new RequestNotification(project, path);
    }

    public static RequestNotification init(Project project, FilterNotification filter) {
        return new RequestNotification(project, filter, Path.TABLE_FILTER);
    }

    private String getAddress() {
        if (!path.equals(Path.TABLE_FILTER)) {
        return project.getServerAddress() + ADDRESS + path.getUrl();
        }
        return project.getServerAddress() + ADDRESS + path.getUrl()
                .replace(PLACEHOLDER_SEVERITY, filter.getSeverity().name())
                .replace(PLACEHOLDER_NODE, filter.getNote())
                .replace(PLACEHOLDER_TEXT, filter.getText());
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);

        switch (path) {
            case TABLE_FILTER:
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
    //http://172.26.26.16:8180/services/notification/table?orderBy=$<<startTime>> DESC&condition=$<<active>> AND (($<<definition.severity>> = 'ERROR') AND ($<<text>> = 'USV Netzausfall') AND ($<<nodePath>> IN ('F0105LRB049')))
    /*public enum Path {
        TABLE_FILTER("/table", "&condition=$<<active>> AND (($<<definition.severity>> = '" + PLACEHOLDER_SEVERITY + "') AND ($<<text>> = '" + PLACEHOLDER_TEXT + "') AND ($<<nodePath>> IN ('" + PLACEHOLDER_NODE + "')))"),
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
    }*/

    public enum Path {
        TABLE_FILTER("/table", "&condition=(($<<definition.severity>> = '" + PLACEHOLDER_SEVERITY + "') AND ($<<text>> = '" + PLACEHOLDER_TEXT + "') AND ($<<nodePath>> IN ('" + PLACEHOLDER_NODE + "')))"),
        TABLE_ALL("/table", ""),
        TABLE_ERROR("/table", "&condition=($<<definition.severity>> = 'ERROR'))"),
        TABLE_WARNING("/table", "&condition=($<<definition.severity>> = 'WARN'))"),
        COUNT_ALL("/count", ""),
        COUNT_ERROR("/count", "&condition=($<<definition.severity>> = 'ERROR'))"),
        COUNT_WARNING("/count", "&condition=($<<definition.severity>> = 'WARN'))");

        private String defaultQuery = "?orderBy=$<<startTime>> DESC&firstResult=0&maxResults=19";
        private String url;

        Path(String url) {
            this.url = url + defaultQuery;
        }

        Path(String url, String query) {
            this.url = url + defaultQuery + query;
        }

        public String getUrl() {
            return url;
        }
    }
}
