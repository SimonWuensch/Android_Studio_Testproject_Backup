package ssi.ssn.com.ssi_service.model.network.response.notification.objects;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;

public class ResponseNotification {

    private long id;
    private long startTime;
    private String text;
    private String nodePath = "";
    private String tKey;
    private ResponseNotificationDefinition definition;

    public ResponseNotification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public String gettKey() {
        return tKey;
    }

    public void settKey(String tKey) {
        this.tKey = tKey;
    }

    public ResponseNotificationDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ResponseNotificationDefinition definition) {
        this.definition = definition;
    }
}
