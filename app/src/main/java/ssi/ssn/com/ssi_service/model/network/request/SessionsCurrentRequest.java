package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.handler.JsonHandler;
import ssi.ssn.com.ssi_service.model.network.ResponseState;
import ssi.ssn.com.ssi_service.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.SessionsCurrentResponse;

public class SessionsCurrentRequest extends AbstractRequest{

    private static String TAG = SessionsCurrentRequest.class.getSimpleName();
    private static String ADDRESS = "/services/security/sessions/current";

    private CookieHandler cookieHandler;

    public SessionsCurrentRequest(RequestHandler requestHandler, CookieHandler cookieHandler, Project project, AsyncTask customOnFinishedTask){
        super(requestHandler, project, ADDRESS);
        this.customOnFinishedTask = customOnFinishedTask;
        this.cookieHandler = cookieHandler;
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + SessionsCurrentRequest.class.getSimpleName() + " set to [" + result + "].");
            SessionsCurrentResponse response = (SessionsCurrentResponse) JsonHandler.fromJsonGeneric(SessionsCurrentResponse.class, result);
            if(!response.getStatus().equals("LOGGED_IN")) {
                requestHandler.doLoginRequest(project, customOnFinishedTask);
            }
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }
}
