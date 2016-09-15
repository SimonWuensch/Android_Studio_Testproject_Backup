package ssi.ssn.com.ssi_client.model.network.communication;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import ssi.ssn.com.ssi_client.model.network.ResponseState;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;

public class GetCommunication extends AsyncTask<String, Void, String> {

    private static String TAG = GetCommunication.class.getSimpleName();

    private CookieHandler cookieHandler;
    private boolean resetCookie;
    private String address;
    private AsyncTask<String, Void, String> onFinishedTask;

    public GetCommunication(CookieHandler cookieHandler, boolean resetCookie, String address, AsyncTask<String, Void, String> onFinishedTask){
        this.cookieHandler = cookieHandler;
        this.resetCookie = resetCookie;
        this.address = address;
        this.onFinishedTask = onFinishedTask;
    }

    @Override
    protected String doInBackground(String... params) {
        return getRequest(address, resetCookie);
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        if(onFinishedTask != null){
            Log.d(TAG, "Executing custom transferred onFinishedTask. Address: + [" + address + "] + Result: [" + result + "].");
            onFinishedTask.execute(result);
        }
    }

    private String getRequest(String address, boolean resetCookie){
        String hostAddress = "http://" + address;
        Log.d(TAG, "SENDING GET Request. Address: [" + hostAddress + "].");
        HttpURLConnection urlConnection = null;
        try{
            String encodedHostAddress = getEncodeAddress(hostAddress);
            URL url = new URL(encodedHostAddress);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
/*
            Log.i(TAG, "ResponseCode: " + urlConnection.getResponseCode());
            Log.i(TAG, "ResponseMessage: " + urlConnection.getResponseMessage());
            InputStream isError = urlConnection.getErrorStream();
            String error = IOUtils.toString(isError);
            Log.i(TAG, "Errorstream: " + error);
            */

            if(cookieHandler.isCookieManagerNull()){
                resetCookie = true;
            }

            if(cookieHandler != null) {
                if (resetCookie) {
                    cookieHandler.initCookieManager(urlConnection);
                } else {
                    cookieHandler.setCookieRequestProperty(urlConnection);
                }
            }

            InputStream is = urlConnection.getInputStream();
            String result = IOUtils.toString(is);
            Log.i(TAG, "GET Response. Address: [" + hostAddress + "], Result: [" + result + "]");
            return result;
        } catch (Exception ex) {
            Log.e(TAG, "ERROR GET Request. Address: [" + hostAddress + "] " + ex.getMessage());
        }finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return ResponseState.ERROR + " at address [" + hostAddress + "]";
    }

    private String getEncodeAddress(String address) throws UnsupportedEncodingException {
        if(address.contains("?")) {
            address = address.replaceAll(" ", "%20");
            return address;
        }
        return address;
    }
}
