package ssi.ssn.com.ssi_service.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.ResponseState;
import ssi.ssn.com.ssi_service.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

public class ScadaRequest extends AbstractRequest {

    private static String TAG = ScadaRequest.class.getSimpleName();
    private static String ADDRESS = "/services/scada";

    public ScadaRequest(Project project) {
        super(project, ADDRESS);
    }

    public ScadaRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask, ScadaRequest.Path path){
        super(requestHandler, project, ADDRESS + path.getUrl());
        this.customOnFinishedTask = customOnFinishedTask;
    }

    public ScadaRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask, String customUrl){
        super(requestHandler, project, ADDRESS + customUrl);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setScada(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + ScadaRequest.class.getSimpleName() + " set to [" + result + "].");
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }

    public enum Path {

        SYSTEMS("/systems"),
        SCANNERS("/scanners");

        private String url;

        Path(String url){
            this.url = url;
        }

        public String getUrl(){
            return url;
        }
    }

}
