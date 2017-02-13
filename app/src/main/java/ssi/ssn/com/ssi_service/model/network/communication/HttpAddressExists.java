package ssi.ssn.com.ssi_service.model.network.communication;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

import ssi.ssn.com.ssi_service.model.helper.FormatHelper;

public class HttpAddressExists extends AbstractHttpCommunication {

    private static String TAG = HttpAddressExists.class.getSimpleName();

    public HttpAddressExists(String address) {
        super(address);
    }

    @Override
    protected int send() {
        String hostAddress = PROTOCOLL + address;
        HttpURLConnection urlConnection = null;
        try {
            String address = FormatHelper.getEncodeAddress(hostAddress);
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(READ_TIME_OUT_INTERVAL);
            urlConnection.setReadTimeout(READ_TIME_OUT_INTERVAL);
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "[GET URL EXISTS] Address: [" + hostAddress + "], Code: [" + responseCode + "]");
            return responseCode;
        } catch (java.net.SocketTimeoutException e) {
            Log.e(TAG, "[ERROR] GET URL EXISTS Timeout: [" + hostAddress + "]");
            return GET_COMMUNICATION_ERROR_TIMEOUT;
        } catch (Throwable t) {
            Log.e(TAG, "[ERROR] GET URL EXISTS. Address: [" + hostAddress + "]");
            return GET_COMMUNICATION_ERROR;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
