package ssi.ssn.com.ssi_service.model.network.handler;

import android.text.TextUtils;
import android.util.Log;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class CookieHandler {

    private static String TAG = CookieHandler.class.getSimpleName();

    private CookieManager cookieManager;

    public void initCookieManager(HttpURLConnection urlConnection) {
        Log.d(TAG, "CookieManager initialization...");
        cookieManager = new CookieManager();
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
        Log.d(TAG, "CookieManager initialized.");
    }

    public void setCookieRequestProperty(HttpURLConnection urlConnection) {
        if (cookieManager.getCookieStore().getCookies().size() > 0) {
            urlConnection.setRequestProperty("Cookie",
                    TextUtils.join(",", cookieManager.getCookieStore().getCookies()));
        }
    }

    public boolean isCookieManagerNull() {
        return cookieManager == null;
    }
}
