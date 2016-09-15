package ssi.ssn.com.ssi_client.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_client.model.data.ressource.Project;
import ssi.ssn.com.ssi_client.model.network.ResponseState;
import ssi.ssn.com.ssi_client.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_client.model.network.handler.RequestHandler;

public class LoginRequest extends AbstractRequest{

    private static String TAG = LoginRequest.class.getSimpleName();
    private static String ADDRESS = "/services/security/login/{name}/{password}";

    public LoginRequest(RequestHandler requestHandler, Project project){
        super(requestHandler, project, ADDRESS);
    }

    public LoginRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask){
        super(requestHandler, project, ADDRESS);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    @Override
    public String getAddress() {
        return project.getServerAddress() + ADDRESS
                .replace("{name}", project.getUserName())
                .replace("{password}", project.getPassword());
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setLogin(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + LoginRequest.class.getSimpleName() + " set to [" + result + "].");
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, true, getAddress(), followingTask);
    }
}
