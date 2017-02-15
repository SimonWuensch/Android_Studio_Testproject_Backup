package ssi.ssn.com.ssi_service.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.app.Notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentListNotification;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleListNotification;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectListNotification;

public class AndroidNotificationHelper {

    public static String TAG_FRAGMENT = "TAG_FRAGMENT";
    private int NOTIFICATION = 111;

    NotificationManager mNotifyMgr;
    private Set<Integer> notificationKeySet = new HashSet<>();

    public AndroidNotificationHelper(Context context) {
        this.mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cancelAllNotifications(){
        for(int id : notificationKeySet){
            mNotifyMgr.cancel(id);
        }
    }

    public void handleNotificationClick(Activity activity, Intent intent) {
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

    public void throwNotification(Context context, long projectID, Intent resultIntent, int color, int smallIcon, String contentTitle, String contentText, String ticker) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setColor(color)
                        .setSmallIcon(smallIcon)
                        .setContentTitle(contentTitle)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                        .setContentText(contentText)
                        .setTicker(ticker);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        mNotifyMgr.notify(NOTIFICATION + safeLongToInt(projectID), mBuilder.build());
        notificationKeySet.add(NOTIFICATION + safeLongToInt(projectID));
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
