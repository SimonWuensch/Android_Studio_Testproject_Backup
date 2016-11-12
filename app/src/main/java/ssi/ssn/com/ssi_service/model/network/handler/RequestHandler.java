package ssi.ssn.com.ssi_service.model.network.handler;

import android.os.AsyncTask;

import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.request.RequestApplication;
import ssi.ssn.com.ssi_service.model.network.request.RequestApplicationConfig;
import ssi.ssn.com.ssi_service.model.network.request.RequestComponent;
import ssi.ssn.com.ssi_service.model.network.request.RequestLogin;
import ssi.ssn.com.ssi_service.model.network.request.RequestLogout;
import ssi.ssn.com.ssi_service.model.network.request.RequestModule;
import ssi.ssn.com.ssi_service.model.network.request.RequestNotification;
import ssi.ssn.com.ssi_service.model.network.request.RequestOs;
import ssi.ssn.com.ssi_service.model.network.request.RequestScada;
import ssi.ssn.com.ssi_service.model.network.request.RequestSessionsCurrent;

public class RequestHandler {

    private static String TAG = RequestHandler.class.getSimpleName();

    private CookieHandler cookieHandler;

    private RequestHandler(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    public static RequestHandler initRequestHandler() {
        return new RequestHandler(new CookieHandler());
    }

    //** Without Cookie ************************************************************************* //
    public AsyncTask getRequestApplicationTask(Project project) {
        return RequestApplication.init(project).getTaskGET(cookieHandler);
    }

    public AsyncTask getRequestSessionsCurrentTask(Project project) {
        return RequestSessionsCurrent.init(project).getTaskGET(cookieHandler);
    }

    // ** With Cookie *************************************************************************** //
    // *** Login  ******************************************************************************* //
    public AsyncTask getRequestLoginTask(Project project) {
        return RequestLogin.init(this, project).getTaskGET(cookieHandler);
    }

    public void addRequestLoginTaskToExecutor(ExecutorService executor, Project project) {
        RequestLogin.init(this, project).addTaskGETtoExecutor(executor, cookieHandler);
    }

    // *** Application Config ******************************************************************* //
    public AsyncTask getRequestApplicationConfigTask(Project project) {
        return RequestApplicationConfig.init(project).getTaskGET(cookieHandler);
    }

    public AsyncTask getRequestComponentTask(Project project, String componentName) {
        return RequestComponent.init(project, componentName).getTaskGET(cookieHandler);
    }

    public AsyncTask getRequestModuleTask(Project project, String moduleName) {
        return RequestModule.init(project, moduleName).getTaskGET(cookieHandler);
    }

    // *** OS *********************************************************************************** //
    public AsyncTask getRequestOsTask(Project project) {
        return RequestOs.init(project).getTaskGET(cookieHandler);
    }

    // *** Notification ************************************************************************* //
    public AsyncTask getRequestNotificationTask(Project project, RequestNotification.Path path) {
        return RequestNotification.init(project, path).getTaskGET(cookieHandler);
    }

    // *** Scada ******************************************************************************** //
    public AsyncTask getRequestScadaTask(Project project, RequestScada.Path path) {
        return RequestScada.init(project, path).getTaskGET(cookieHandler);
    }

    // *** Logout
    public AsyncTask getRequestLogoutTask(Project project) {
        return RequestLogout.init(project).getTaskGET(cookieHandler);
    }
}
