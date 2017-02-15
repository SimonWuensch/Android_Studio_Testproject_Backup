package ssi.ssn.com.ssi_service.model.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

public class SourceHelper {

    private static String LOADING_ERROR = "LOADING_ERROR";

    public static String getString(Context context, int source) {
        try {
            return context.getString(source);
        } catch (Throwable t) {
            return LOADING_ERROR;
        }
    }

    public static int getColor(Context context, int source) {
        try {
            return ContextCompat.getColor(context, source);
        } catch (Throwable t) {
            return Color.LTGRAY;
        }
    }
}
