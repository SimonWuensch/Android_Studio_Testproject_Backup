package ssi.ssn.com.ssi_service.model.helper;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

public class SourceHelper {

    private static String LOADING_ERROR = "LOADING_ERROR";

    public static String getString(Activity activity, int source) {
        try {
            return activity.getString(source);
        } catch (Throwable t) {
            return LOADING_ERROR;
        }
    }

    public static int getColor(Activity activity, int source) {
        try {
            return ContextCompat.getColor(activity, source);
        } catch (Throwable t) {
            return Color.LTGRAY;
        }
    }
}
