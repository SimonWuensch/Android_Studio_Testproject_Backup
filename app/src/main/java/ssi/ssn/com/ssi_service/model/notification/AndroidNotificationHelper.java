package ssi.ssn.com.ssi_service.model.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentList;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentListNotification;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleList;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleListNotification;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectList;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectListNotification;

public class AndroidNotificationHelper {

    public static String TAG_FRAGMENT = "TAG_FRAGMENT";
    private int NOTIFICATION_ID_ONE = 111;


    private Activity activity;
    private int number = 0;

    public AndroidNotificationHelper(Activity activity) {
        this.activity = activity;
    }

    public void handleNotificationClick(Intent intent) {
        List<AbstractAndroidNotification> notificationToFragmentList = new ArrayList<AbstractAndroidNotification>() {
            {
                add(new FragmentProjectListNotification());
                add(new FragmentProjectListNotification());
                add(new FragmentModuleListNotification());
                add(new FragmentComponentListNotification());
            }
        };

        for (AbstractAndroidNotification notification : notificationToFragmentList) {
            notification.newInstanceWithIntent(activity, intent);
        }
    }

    public void throwNotification(Activity activity, Intent resultIntent, int smallIcon, String contentTitle, String contentText, String ticker) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(smallIcon)
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setTicker(ticker)
                        .setNumber(++number);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(activity);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NOTIFICATION_ID_ONE, mBuilder.build());
    }
}
