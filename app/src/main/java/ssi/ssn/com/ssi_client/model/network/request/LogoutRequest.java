package ssi.ssn.com.ssi_client.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_client.model.data.ressource.Project;
import ssi.ssn.com.ssi_client.model.network.ResponseState;
import ssi.ssn.com.ssi_client.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_client.model.network.handler.RequestHandler;

public class LogoutRequest extends AbstractRequest {

    private static String TAG = LogoutRequest.class.getSimpleName();

    private static String ADDRESS = "/services/security/logout";

    public LogoutRequest(RequestHandler requestHandler, Project project) {
        super(requestHandler, project, ADDRESS);
    }

    public LogoutRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask) {
        super(requestHandler, project, ADDRESS);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setLogout(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + LogoutRequest.class.getSimpleName() + " set to [" + result + "].");
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }
}
