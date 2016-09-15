package ssi.ssn.com.ssi_client.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_client.model.data.ressource.Project;
import ssi.ssn.com.ssi_client.model.network.ResponseState;
import ssi.ssn.com.ssi_client.model.network.communication.GetCommunication;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_client.model.network.handler.RequestHandler;

public class NotificationRequest extends AbstractRequest {

    private static String TAG = NotificationRequest.class.getSimpleName();

    public static String ADDRESS = "/services/notification";

    public NotificationRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask, Path path){
        super(requestHandler, project, ADDRESS + path.getUrl());
        this.customOnFinishedTask = customOnFinishedTask;
    }

    public NotificationRequest(RequestHandler requestHandler, Project project, AsyncTask customOnFinishedTask, String customUrl){
        super(requestHandler, project, ADDRESS + customUrl);
        this.customOnFinishedTask = customOnFinishedTask;
    }

    @Override
    protected void doOnPostExecute(String result){
        if (!result.equals(ResponseState.ERROR)) {
            //project.setNotification(result);
            Log.i(TAG, ADDRESS + " - Project [" + project.toString() + "] " + NotificationRequest.class.getSimpleName() + " set to [" + result + "].");
        }else{
        Log.e(TAG, "[ERROR] " + result);
        }
        super.doOnPostExecute(result);
    }

    @Override
    public AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler) {
        AsyncTask<String, Void, String> followingTask = getFollowingTask();
        return networkTask = new GetCommunication(cookieHandler, false, getAddress(), followingTask);
    }

    //NotificationRequestType
    public enum Path {

        TABLE_ALL("/table"),
        TABLE_ERROR("/table", "&condition=(($<<active>>) AND ($<<definition.severity>> = 'ERROR'))"),
        TABLE_WARNING("/table", "&condition=(($<<active>>) AND ($<<definition.severity>> = 'WARN'))"),
        COUNT_ALL("/count"),
        COUNT_ERROR("/count", "&condition=(($<<active>>) AND ($<<definition.severity>> = 'ERROR'))"),
        COUNT_WARNING("/count", "&condition=(($<<active>>) AND ($<<definition.severity>> = 'WARN'))");

        private String defaultQuery = "?orderBy=$<<startTime>> DESC";
        private String url;

        Path(String url){
            this.url = url + defaultQuery + "&condition=$<<active>>";
        }

        Path(String url, String query){
            this.url = url + defaultQuery + query;
        }

        public String getUrl(){
            return url;
        }
    }
}
