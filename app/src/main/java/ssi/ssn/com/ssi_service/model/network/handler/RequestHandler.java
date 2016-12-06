package ssi.ssn.com.ssi_service.model.network.handler;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private Map<Long, CookieHandler> cookieHandlerMap = new HashMap<>();

    private ExecutorService executor;

    private RequestHandler(ExecutorService executor) {
        this.executor = executor;
    }

    public static RequestHandler initRequestHandler(ExecutorService executor) {
        return new RequestHandler(executor);
    }

    public ExecutorService getExecutor(){
        return executor;
    }

    private CookieHandler getCookieHandler(Project project){
        if(!cookieHandlerMap.containsKey(project.get_id())){
            cookieHandlerMap.put(project.get_id(), new CookieHandler());
        }
        return cookieHandlerMap.get(project.get_id());
    }

    //** Without Cookie ************************************************************************* //
    public AsyncTask getRequestApplicationTask(Project project) {
        return RequestApplication.init(project).getTaskGET(getCookieHandler(project));
    }

    public AsyncTask getRequestSessionsCurrentTask(Project project) {
        return RequestSessionsCurrent.init(project).getTaskGET(getCookieHandler(project));
    }

    // ** With Cookie *************************************************************************** //
    // *** Login  ******************************************************************************* //
    public AsyncTask getRequestLoginTask(Project project) {
        return RequestLogin.init(this, project).getTaskGET(getCookieHandler(project));
    }

    public void addRequestLoginTaskToExecutor(Project project) {
        RequestLogin.init(this, project).addTaskGETtoExecutor(executor, getCookieHandler(project));
    }

    // *** Application Config ******************************************************************* //
    public AsyncTask getRequestApplicationConfigTask(Project project) {
        return RequestApplicationConfig.init(project).getTaskGET(getCookieHandler(project));
    }

    public AsyncTask getRequestComponentTask(Project project, String componentName) {
        return RequestComponent.init(project, componentName).getTaskGET(getCookieHandler(project));
    }

    public AsyncTask getRequestModuleTask(Project project, String moduleName) {
        return RequestModule.init(project, moduleName).getTaskGET(getCookieHandler(project));
    }

    // *** OS *********************************************************************************** //
    public AsyncTask getRequestOsTask(Project project) {
        return RequestOs.init(project).getTaskGET(getCookieHandler(project));
    }

    // *** Notification ************************************************************************* //
    public AsyncTask getRequestNotificationTask(Project project, RequestNotification.Path path) {
        return RequestNotification.init(project, path).getTaskGET(getCookieHandler(project));
    }

    // *** Scada ******************************************************************************** //
    public AsyncTask getRequestScadaTask(Project project, RequestScada.Path path) {
        return RequestScada.init(project, path).getTaskGET(getCookieHandler(project));
    }

    // *** Logout
    public AsyncTask getRequestLogoutTask(Project project) {
        return RequestLogout.init(project).getTaskGET(getCookieHandler(project));
    }
}
