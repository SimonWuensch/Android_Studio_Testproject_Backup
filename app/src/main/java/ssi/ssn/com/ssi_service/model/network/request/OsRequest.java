package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.ResponseState;
import ssi.ssn.com.ssi_service.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

public class OsRequest extends AbstractRequest {

    private static String TAG = OsRequest.class.getSimpleName();

    private static String ADDRESS = "/services/application/diagnostics/os";

    public OsRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask){
        super(requestHandler, project, ADDRESS);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    public OsRequest(RequestHandler requestHandler, Project project){
        super(requestHandler, project, ADDRESS);
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setOs(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + OsRequest.class.getSimpleName() + " set to [" + result + "].");
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }
}
