package ssi.ssn.com.ssi_service.model.network.handler;

import android.app.Activity;
import android.os.AsyncTask;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.request.ApplicationRequest;
import ssi.ssn.com.ssi_service.model.network.request.LoginRequest;
import ssi.ssn.com.ssi_service.model.network.request.LogoutRequest;
import ssi.ssn.com.ssi_service.model.network.request.NotificationRequest;
import ssi.ssn.com.ssi_service.model.network.request.OsRequest;
import ssi.ssn.com.ssi_service.model.network.request.ScadaRequest;
import ssi.ssn.com.ssi_service.model.network.request.SessionsCurrentRequest;

public class RequestHandler {

    private static String TAG = RequestHandler.class.getSimpleName();

    private CookieHandler cookieHandler;
    private Activity activity;

    public RequestHandler(Activity activity){
        this.activity = activity;
    }

    public RequestHandler(Activity activity, CookieHandler cookieHandler){
        this.activity = activity;
        this.cookieHandler = cookieHandler;
    }

    //** Without Cookie ************************************************************************* //
    public static void doApplicationRequest(Project project){
        ApplicationRequest applicationRequest = new ApplicationRequest(project);
        applicationRequest.initGetNetworkTask(null).execute();
    }

    public static void doApplicationRequest(Project project, AsyncTask onFinishedTask){
        ApplicationRequest applicationRequest = new ApplicationRequest(project, onFinishedTask);
        applicationRequest.initGetNetworkTask(null).execute();
    }

    //** With Cookie **************************************************************************** //
    public static RequestHandler initRequestHandler(Activity activity){
        return new RequestHandler(activity, new CookieHandler());
    }

    public void executeTask(Project project, AsyncTask onFinishedTask) {
        SessionsCurrentRequest sessionsCurrentRequest = new SessionsCurrentRequest(this, cookieHandler, project, onFinishedTask);
        sessionsCurrentRequest.initGetNetworkTask(cookieHandler).execute();
    }

    public void doLoginRequest(Project project){
        LoginRequest loginRequest = new LoginRequest(this, project);
        loginRequest.initGetNetworkTask(cookieHandler).execute();
    }

    public void doLoginRequest(Project project, AsyncTask onFinishedTask){
        LoginRequest loginRequest = new LoginRequest(this, project, onFinishedTask);
        loginRequest.initGetNetworkTask(cookieHandler).execute();
    }

    // *** OS
    public void doOsRequest(Project project){
        doOsRequest(project, getLogoutRequestTask(project));
    }

    public void doOsRequest(Project project, AsyncTask onFinishedTask){
        OsRequest osRequest = new OsRequest(this, project, onFinishedTask);
        executeTask(project, osRequest.initGetNetworkTask(cookieHandler));
    }

    // *** Notification
    public void doNotificationRequest(Project project, NotificationRequest.Path path){
        NotificationRequest notificationRequest = new NotificationRequest(this, project, null, path);
        executeTask(project, notificationRequest.initGetNetworkTask(cookieHandler));
    }

    public void doNotificationRequest(Project project, NotificationRequest.Path Path, AsyncTask onFinishedTask){
        NotificationRequest notificationRequest = new NotificationRequest(this, project, onFinishedTask, Path);
        executeTask(project, notificationRequest.initGetNetworkTask(cookieHandler));
    }

    public void doNotificationRequest(Project project, String customUrl, AsyncTask onFinishedTask){
        NotificationRequest notificationRequest = new NotificationRequest(this, project, onFinishedTask, customUrl);
        executeTask(project, notificationRequest.initGetNetworkTask(cookieHandler));
    }

    // *** Scada
    public void doScadaRequest(Project project, ScadaRequest.Path path){
        ScadaRequest scadaRequest = new ScadaRequest(this, project, null, path);
        executeTask(project, scadaRequest.initGetNetworkTask(cookieHandler));
    }

    // *** Logout
    public AsyncTask<String, Void, String> getLogoutRequestTask(Project project){
        LogoutRequest logoutRequest = new LogoutRequest(this, project, null);
        return logoutRequest.initGetNetworkTask(cookieHandler);
    }

    public void doLogoutRequest(Project project){
        doLogoutRequest(project, null);
    }

    public void doLogoutRequest(Project project, AsyncTask onFinishedTask){
        LogoutRequest logoutRequest = new LogoutRequest(this, project, onFinishedTask);
        logoutRequest.initGetNetworkTask(cookieHandler).execute();
    }
}
