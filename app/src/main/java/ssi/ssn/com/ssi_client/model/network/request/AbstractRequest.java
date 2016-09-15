package ssi.ssn.com.ssi_client.model.network.request;

import android.os.AsyncTask;
import android.util.Log;

import ssi.ssn.com.ssi_client.model.data.ressource.Project;
import ssi.ssn.com.ssi_client.model.network.ResponseState;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_client.model.network.handler.RequestHandler;

public class AbstractRequest {


    private String address = "/";

    protected Project project;

    protected AsyncTask<String, Void, String> followingTask;
    protected AsyncTask<String, Void, String> customOnFinishedTask;
    protected AsyncTask<String, Void, String> networkTask;

    protected RequestHandler requestHandler;

    protected AbstractRequest(Project project, String address){
        this.project = project;
        this.address = address;
        initFollowingTask();
    }

    protected AbstractRequest(RequestHandler requestHandler, Project project, String address){
        this.requestHandler = requestHandler;
        this.project = project;
        this.address = address;
        initFollowingTask();
    }

    protected String getAddress(){
        return project.getServerAddress() + address;
    }

    protected AsyncTask<String, Void, String> getFollowingTask(){
        if(followingTask == null){
            Log.w(getClass().getSimpleName(), "FollowingTask is not initialized. Project: " + project + ", Address: [" + getAddress() + " ].");
            return new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String[] params) {
                    return null;
                }
            };
        }
        return followingTask;
    }

    protected AsyncTask<String, Void, String> initFollowingTask() {
        final String address = getAddress();
        return followingTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                if (params.length <= 0) {
                    return null;
                }

                String result = params[0];
                if (result.contains(ResponseState.ERROR)) {
                    Log.e(getClass().getSimpleName(), "It is occurred an error while get Request. - Address: [" + address + "]");
                    return ResponseState.ERROR;
                } else {
                    return result;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                doOnPostExecute(result);
            }
        };
    }

    protected void doOnPostExecute(String result){
        if(customOnFinishedTask != null){
            customOnFinishedTask.execute();
        }
    }

    protected AsyncTask<String, Void, String> getGetNetworkTask(){
        if(networkTask == null){
            Log.w(getClass().getSimpleName(), "Networktask is not initialized. Project: " + project + ", Address: [" + getAddress() +" ].");
            return new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    return null;
                }
            };
        }
        return networkTask;
    }
    protected AsyncTask<String, Void, String> initGetNetworkTask(CookieHandler cookieHandler){
        return getGetNetworkTask();
    }
}
