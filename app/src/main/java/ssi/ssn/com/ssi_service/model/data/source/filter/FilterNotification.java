package ssi.ssn.com.ssi_service.model.data.source.filter;

import android.util.Log;

import java.util.Date;

import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.network.response.notification.ResponseNotificationTable;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.NotificationSeverity;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class FilterNotification {

    private static String TAG = FilterNotification.class.getSimpleName();

    private int id;
    private String note;
    private long activeTime;
    private NotificationSeverity severity;
    private String text;
    private ResponseNotificationTable notificationTable;
    private ResponseNotificationTable activeTimeReachedNotificationTable = new ResponseNotificationTable();

    public FilterNotification() {
    }

    public FilterNotification(String note, long activeTime, NotificationSeverity severity, String text) {
        this.note = note;
        this.activeTime = activeTime;
        this.severity = severity;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public NotificationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResponseNotificationTable getNotificationTable() {
        return notificationTable;
    }

    public void setNotificationTable(ResponseNotificationTable notificationTable) {
        this.notificationTable = notificationTable;
    }

    public ResponseNotificationTable getActiveTimeReachedNotificationTable() {
        return activeTimeReachedNotificationTable;
    }

    public void setActiveTimeReachedNotificationTable(ResponseNotificationTable activeTimeReachedNotificationTable) {
        this.activeTimeReachedNotificationTable = activeTimeReachedNotificationTable;
    }

    public void checkResponseNotificationTable() {
        activeTimeReachedNotificationTable = new ResponseNotificationTable();
        if (notificationTable == null) {
            return;
        }
        for (ResponseNotification notification : notificationTable.getData()) {
            long activeTime = new Date().getTime() - notification.getStartTime();
            if (activeTime >= this.activeTime) {
                activeTimeReachedNotificationTable.addNotification(notification);
                Log.d(TAG, "Notification found, which reached the maximal active time. Active Time: " + FormatHelper.formatMillisecondsToHours(activeTime) + " hours.");
            }
        }
        if (activeTimeReachedNotificationTable.getCount() > 0) {
            Log.i(TAG + " - " + identity(), "[" + activeTimeReachedNotificationTable.getCount() + "] Notifications found, which reached the maximal active time");
        }
    }

    public String identity() {
        return "[" + getNote() + ";" + getActiveTime() + ";" + getSeverity() + ";" + getText() + "]";
    }
}
