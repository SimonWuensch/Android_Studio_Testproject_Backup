package ssi.ssn.com.ssi_service.model.data.source;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public enum Status {

    OK(1, R.color.OK),
    ERROR(2, R.color.ERROR),
    WARNING(3, R.color.WARNING),
    NOT_AVAILABLE(4, R.color.NOT_AVAILABLE),
    NOT_OBSERVATION(5, R.color.lightGray);

    public static String TEXT_RUNNING = "RUNNING";
    public static String TEXT_ONLINE = "ONLINE";
    public static String TEXT_UNKNOWN = "UNKNOWN";
    public static String TEXT_NOT_AVAILABLE = "NOT_AVAILABLE";

    private int id;
    private int color;

    Status(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public static Status getStatusByID(int id) {
        Map<Integer, Status> statusMap = new HashMap<Integer, Status>() {
            {
                put(1, OK);
                put(2, ERROR);
                put(3, WARNING);
                put(4, NOT_AVAILABLE);
                put(5, NOT_OBSERVATION);
            }
        };
        return statusMap.get(id);
    }

    public int getColor(Context context) {
        return SourceHelper.getColor(context, color);
    }

    public int getId() {
        return id;
    }
}
