package ssi.ssn.com.ssi_service.model.network.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.network.request.RequestApplication;
import ssi.ssn.com.ssi_service.model.network.request.RequestApplicationConfig;
import ssi.ssn.com.ssi_service.model.network.request.RequestComponent;
import ssi.ssn.com.ssi_service.model.network.request.RequestKPIDefinitions;
import ssi.ssn.com.ssi_service.model.network.request.RequestKPIMeasurements;
import ssi.ssn.com.ssi_service.model.network.request.RequestLogin;
import ssi.ssn.com.ssi_service.model.network.request.RequestLogout;
import ssi.ssn.com.ssi_service.model.network.request.RequestModule;
import ssi.ssn.com.ssi_service.model.network.request.RequestNotification;
import ssi.ssn.com.ssi_service.model.network.request.RequestOs;
import ssi.ssn.com.ssi_service.model.network.request.RequestScada;
import ssi.ssn.com.ssi_service.model.network.request.RequestSessionsCurrent;

public class RequestHandler {

    private static String TAG = RequestHandler.class.getSimpleName();

    private Map<Integer, CookieHandler> cookieHandlerMap = new HashMap<>();

    private ExecutorService executor;

    private RequestHandler(ExecutorService executor) {
        this.executor = executor;
    }

    public static RequestHandler initRequestHandler(ExecutorService executor) {
        return new RequestHandler(executor);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    private CookieHandler getCookieHandler(Project project) {
        if (!cookieHandlerMap.containsKey(project.get_id())) {
            cookieHandlerMap.put(project.get_id(), new CookieHandler());
        }
        return cookieHandlerMap.get(project.get_id());
    }

    //** Without Cookie ************************************************************************* //
    public void sendRequestApplication(Project project) {
        RequestApplication.init(project).getTaskGET(getCookieHandler(project));
    }

    public void sendRequestSessionsCurrent(Project project) {
        RequestSessionsCurrent.init(project).getTaskGET(getCookieHandler(project));
    }

    // ** With Cookie *************************************************************************** //
    // *** Login  ******************************************************************************* //
    public void sendRequestLogin(Project project) {
        RequestLogin.init(this, project).getTaskGET(getCookieHandler(project));
    }

    // *** Application Config ******************************************************************* //
    public void sendRequestApplicationConfig(Project project) {
        RequestApplicationConfig.init(project).getTaskGET(getCookieHandler(project));
    }

    public void sendRequestComponent(Project project, String componentName) {
        RequestComponent.init(project, componentName).getTaskGET(getCookieHandler(project));
    }

    public void sendRequestModule(Project project, String moduleName) {
        RequestModule.init(project, moduleName).getTaskGET(getCookieHandler(project));
    }

    // *** OS *********************************************************************************** //
    public void sendRequestOs(Project project) {
        RequestOs.init(project).getTaskGET(getCookieHandler(project));
    }

    // *** Notification ************************************************************************* //
    public void sendRequestNotificationTableAll(Project project) {
        sendRequestNotification(project, RequestNotification.Path.TABLE_ALL);
    }

    public void sendRequestNotificationCountAll(Project project) {
        sendRequestNotification(project, RequestNotification.Path.COUNT_ALL);
    }

    public void sendRequestNotification(Project project, RequestNotification.Path path) {
        RequestNotification.init(project, path).getTaskGET(getCookieHandler(project));
    }

    public void sendRequestNotification(Project project, FilterNotification filter) {
        RequestNotification.init(project, filter).getTaskGET(getCookieHandler(project));
    }

    // ** KPI *********************************************************************************** //
    public void sendRequestKPIDefinitions(Project project) {
        RequestKPIDefinitions.init(project).getTaskGET(getCookieHandler(project));
    }

    public void sendRequestKPIMeasurements(Project project, FilterKpi filter) {
        RequestKPIMeasurements.init(project, filter).getTaskGET(getCookieHandler(project));
    }

    // *** Scada ******************************************************************************** //
    public void sendRequestScada(Project project, RequestScada.Path path) {
        RequestScada.init(project, path).getTaskGET(getCookieHandler(project));
    }

    // *** Logout  ****************************************************************************** //
    public void sendRequestLogout(Project project) {
        RequestLogout.init(project).getTaskGET(getCookieHandler(project));
    }
}
