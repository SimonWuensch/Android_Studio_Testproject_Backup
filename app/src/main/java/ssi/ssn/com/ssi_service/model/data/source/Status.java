package ssi.ssn.com.ssi_service.model.data.source;

import android.app.Activity;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public enum Status {


    OK(R.color.OK),
    ERROR(R.color.ERROR),
    WARNING(R.color.WARNING),
    NOT_AVAILABLE(R.color.NOT_AVAILABLE);

    public static String TEXT_RUNNING = "RUNNING";
    public static String TEXT_NOT_RUNNING = "NOT_RUNNING";
    public static String TEXT_NOT_AVAILABLE = "NOT_AVAILABLE";

    private int color;

    Status(int color) {
        this.color = color;
    }

    public int getColor(Activity activity) {
        return SourceHelper.getColor(activity, color);
    }
}
