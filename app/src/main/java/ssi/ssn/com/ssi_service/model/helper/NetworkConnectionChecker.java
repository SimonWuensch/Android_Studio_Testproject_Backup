package ssi.ssn.com.ssi_service.model.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkConnectionChecker {

    private static String TAG = NetworkConnectionChecker.class.getSimpleName();

    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static NetworkConnectionChecker getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return new NetworkConnectionChecker();
    }

    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;
        } catch (Throwable t) {
            t.printStackTrace();
            Log.v(TAG, t.toString());
        }
        return connected;
    }
}
