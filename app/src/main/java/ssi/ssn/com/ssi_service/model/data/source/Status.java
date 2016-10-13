package ssi.ssn.com.ssi_service.model.data.source;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import ssi.ssn.com.ssi_service.R;

public enum Status {

    OK(R.color.OK),
    ERROR(R.color.ERROR),
    WARNING(R.color.WARNING),
    NOT_AVAILABLE(R.color.NOT_AVAILABLE);

    private int color;

    Status(int color) {
        this.color = color;
    }

    public int getColor(Activity activity) {
        return ContextCompat.getColor(activity, color);
    }
}
