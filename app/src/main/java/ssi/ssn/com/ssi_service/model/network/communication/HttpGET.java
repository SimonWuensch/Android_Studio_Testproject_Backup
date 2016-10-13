package ssi.ssn.com.ssi_service.model.network.communication;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class HttpGET {

    private static String TAG = HttpGET.class.getSimpleName();
    private static String PROTOCOLL = "http://";
    private static int GET_COMMUNICATION_ERROR = 900;
    private static int GET_COMMUNICATION_ERROR_TIMEOUT = 901;

    private CookieHandler cookieHandler;
    private String address;


    public HttpGET(CookieHandler cookieHandler, String address){
        this.cookieHandler = cookieHandler;
        this.address = address;
    }

    public DefaultResponse sendRequest(boolean resetCookie){
        String hostAddress = PROTOCOLL + address;
        HttpURLConnection urlConnection = null;
        Log.d(TAG, "[GET Request] Address: [" + hostAddress + "].");
        try{
            String address = getEncodeAddress(hostAddress);
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);

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
            Log.i(TAG, "[GET DefaultResponse] Address: [" + hostAddress + "], Result: [" + result + "]");
            return new DefaultResponse(urlConnection.getResponseCode(), result);
        } catch (java.net.SocketTimeoutException e) {
            Log.e(TAG, "[ERROR] GET Request Timeout: [" + hostAddress + "]");
            return new DefaultResponse(GET_COMMUNICATION_ERROR_TIMEOUT, e.getMessage());
        }catch (Throwable t) {
            Log.e(TAG, "[ERROR] GET Request. Address: [" + hostAddress + "]");
            return new DefaultResponse(GET_COMMUNICATION_ERROR, t.getMessage());
        }finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private String getEncodeAddress(String address) throws UnsupportedEncodingException {
        if(address.contains("?")) {
            address = address.replaceAll(" ", "%20");
            return address;
        }
        return address;
    }
}
