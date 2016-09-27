package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.ResponseState;
import ssi.ssn.com.ssi_service.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class ApplicationRequest extends AbstractRequest {

    private static String TAG = ApplicationRequest.class.getSimpleName();
    private static String ADDRESS = "/services/application";

    public ApplicationRequest(Project project) {
        super(project, ADDRESS);
    }

    public ApplicationRequest(Project project, AsyncTask customOnFinishedTask) {
        super(project, ADDRESS);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setApplication(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + ApplicationRequest.class.getSimpleName() + " set to [" + result + "].");
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }
}
