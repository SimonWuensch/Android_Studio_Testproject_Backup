package ssi.ssn.com.ssi_service.model.network.response.notification.objects;

public class ResponseNotificationDefinition {

    private String key;
    private String type;
    private NotificationSeverity severity;

    public ResponseNotificationDefinition() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NotificationSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(NotificationSeverity severity) {
        this.severity = severity;
    }
}
