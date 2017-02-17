package ssi.ssn.com.ssi_service.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentListNotification;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoardNotification;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleListNotification;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectListNotification;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;

public class AndroidNotificationHelper {

    public static String TAG = AndroidNotificationHelper.class.getSimpleName();

    public static String TAG_FRAGMENT = TAG + "TAG_FRAGMENT";

    private Map<Integer, Notification> iDNotificationMap = new HashMap<>();

    public AndroidNotificationHelper() {
    }

    public void handleNotificationClick(Activity activity, Intent intent) {
        List<AbstractAndroidNotification> notificationToFragmentList = new ArrayList<AbstractAndroidNotification>() {
            {
                add(new FragmentProjectListNotification());
                add(new FragmentLaunchBoardNotification());
                add(new FragmentModuleListNotification());
                add(new FragmentComponentListNotification());
            }
        };

        for (AbstractAndroidNotification notification : notificationToFragmentList) {
            notification.newInstanceWithIntent(activity, intent);
        }
    }

    public void throwNotification(Context context, int projectID, Intent resultIntent, int color, int smallIcon, String contentTitle, String contentText, String ticker) {
        Notification notification = new Notification(contentTitle, contentText);
        int id = addNotification(projectID, notification);
        Log.d(TAG, "Notification id: [" + id + "], Notification: [" + notification + "]");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setColor(color)
                        .setSmallIcon(smallIcon)
                        .setContentTitle(contentTitle)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                        .setContentText(contentText)
                        .setTicker(ticker)
                        .setNumber(iDNotificationMap.get(id).getNumber());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                id,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, mBuilder.build());
    }

    private int addNotification(int projectID, Notification newNotification) {
        if (!iDNotificationMap.containsKey(projectID)) {
            iDNotificationMap.put(projectID, newNotification);
            return projectID;
        }

        int count = 0;
        for (int oldNotificationID : iDNotificationMap.keySet()) {
            if (String.valueOf(oldNotificationID).startsWith(String.valueOf(projectID))) {
                count++;
                Notification oldNotification = iDNotificationMap.get(oldNotificationID);
                if (oldNotification.toString().equals(newNotification.toString())) {
                    oldNotification.incrementNumber();
                    return oldNotificationID;
                }
            }
        }
        int newNotificationID = projectID * 1000 + count + 1;
        iDNotificationMap.put(newNotificationID, newNotification);
        return newNotificationID;
    }

    private class Notification {

        private String title;
        private String text;
        @JsonIgnore
        private int number ;

        public Notification() {
        }

        public Notification(String title, String text) {
            this.title = title;
            this.text = text;
            this.number = 1;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @JsonIgnore
        public void incrementNumber() {
            number++;
        }

        @JsonIgnore
        public int getNumber() {
            return number;
        }

        public String toString() {
            return JsonHelper.toJson(this);
        }
    }
}
