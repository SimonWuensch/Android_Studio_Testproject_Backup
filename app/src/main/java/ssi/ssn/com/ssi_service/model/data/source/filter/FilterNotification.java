package ssi.ssn.com.ssi_service.model.data.source.filter;

import ssi.ssn.com.ssi_service.model.network.response.notification.objects.NotificationSeverity;

public class FilterNotification {

    private int id;
    private String note;
    private long activeTime;
    private NotificationSeverity severity;
    private String text;

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
}
