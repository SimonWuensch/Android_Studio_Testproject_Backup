package ssi.ssn.com.ssi_service.model.helper;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;

public class AndroidNotificationHelper {

    private int NOTIFICATION_ID_ONE = 111;

    private int number = 0;

    public AndroidNotificationHelper() {
    }

    public void throwNotification(Activity activity) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(R.drawable.icon_project)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setTicker("Explicit: New Message Received!")
                        .setNumber(++number);

        NotificationManager mNotifyMgr =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NOTIFICATION_ID_ONE, mBuilder.build());
    }
}
