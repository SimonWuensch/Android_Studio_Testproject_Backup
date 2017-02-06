package ssi.ssn.com.ssi_service.model.data.source;

import android.app.Activity;

import com.owlike.genson.annotation.JsonDateFormat;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public enum Status {

    OK(R.color.OK),
    ERROR(R.color.ERROR),
    WARNING(R.color.WARNING),
    NOT_AVAILABLE(R.color.NOT_AVAILABLE),
    NOT_OBSERVATION(R.color.lightGray);

    public static String TEXT_RUNNING = "RUNNING";
    public static String TEXT_ONLINE = "ONLINE";
    public static String TEXT_UNKNOWN = "UNKNOWN";
    public static String TEXT_NOT_AVAILABLE = "NOT_AVAILABLE";

    private int color;

    Status(int color) {
        this.color = color;
    }

    public int getColor(Activity activity) {
        return SourceHelper.getColor(activity, color);
    }

    public void setColor(int color) {
        this.color = color;
    }
}
