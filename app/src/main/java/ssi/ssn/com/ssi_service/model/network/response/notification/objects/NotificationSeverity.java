package ssi.ssn.com.ssi_service.model.network.response.notification.objects;

import android.content.Context;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public enum NotificationSeverity {
    INFO(R.color.INFO),
    WARN(R.color.WARNING),
    ERROR(R.color.ERROR);

    private int color;

    NotificationSeverity(int color){
        this.color = color;
    }

    public int getColor(Context context) {
        return SourceHelper.getColor(context, color);
    }


}
