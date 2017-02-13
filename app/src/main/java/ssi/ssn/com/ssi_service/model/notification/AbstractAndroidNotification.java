package ssi.ssn.com.ssi_service.model.notification;

import android.app.Activity;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;

public class AbstractAndroidNotification {

    public void newInstanceWithIntent(Activity activity, Intent intent) {
    }

    public Intent createResultIntent(Activity activity, long projectID) {
        return new Intent(activity, MainActivity.class);
    }
}
