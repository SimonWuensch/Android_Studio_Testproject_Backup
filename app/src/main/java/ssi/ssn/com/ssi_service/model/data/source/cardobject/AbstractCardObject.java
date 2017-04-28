package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.content.Context;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class AbstractCardObject {

    protected int title;
    protected int icon;
    protected Status status;
    private long _id;
    private long _id_project;
    private boolean observation = true;
    private long lastObservationTime;

    public AbstractCardObject(Project project) {
        this._id_project = project.get_id();
    }

    public AbstractCardObject() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        throw new NullPointerException("No initialization settings specified");
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_id_project() {
        return _id_project;
    }

    public void set_id_project(long _id_project) {
        this._id_project = _id_project;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isObservation() {
        return observation;
    }

    public void setObservation(boolean observation) {
        this.observation = observation;
    }

    public long getLastObservationTime() {
        return lastObservationTime;
    }

    public void setLastObservationTime(long lastObservationTime) {
        this.lastObservationTime = lastObservationTime;
    }

    @JsonIgnore
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        throw new NullPointerException("No database specified");
    }

    @JsonIgnore
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        throw new NullPointerException("No network settings specified");
    }

    @JsonIgnore
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        throw new NullPointerException("No detection settings specified");
    }

    @JsonIgnore
    public void onClick(final MainActivity activity, final Project project) {
        throw new NullPointerException("No detection settings specified");
    }

    // ** Notification settings ***************************************************************** //

    @JsonIgnore
    public int getNotificationID() {
        throw new NullPointerException("No notification id specified");
    }

    @JsonIgnore
    public AbstractAndroidNotification getNotificationClass() {
        throw new NullPointerException("No notification class specified");
    }

    @JsonIgnore
    public List<String> getNotificationMessages(Context context) {
        throw new NullPointerException("No notification message specified");
    }

    // ** Default ******************************************************************************* //

    public String toString() {
        return JsonHelper.toJson(this);
    }
}
