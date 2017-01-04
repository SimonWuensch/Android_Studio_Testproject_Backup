package ssi.ssn.com.ssi_service.model.network.communication;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class HttpGET extends AbstractHttpCommunication{

    private static String TAG = HttpGET.class.getSimpleName();

    public HttpGET(CookieHandler cookieHandler, String address) {
        super(cookieHandler, address);
    }

    @Override
    protected DefaultResponse send(boolean resetCookie) {
        String hostAddress = PROTOCOLL + address;
        HttpURLConnection urlConnection = null;
        try {
            String address = FormatHelper.getEncodeAddress(hostAddress);
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(READ_TIME_OUT_INTERVAL);
            urlConnection.setReadTimeout(READ_TIME_OUT_INTERVAL);

            if (cookieHandler.isCookieManagerNull()) {
                resetCookie = true;
            }

            if (cookieHandler != null) {
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
        } catch (Throwable t) {
            Log.e(TAG, "[ERROR] GET Request. Address: [" + hostAddress + "]");
            return new DefaultResponse(GET_COMMUNICATION_ERROR, t.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
