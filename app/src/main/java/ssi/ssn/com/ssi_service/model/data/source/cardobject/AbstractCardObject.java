package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.app.Activity;
import android.view.View;

import com.owlike.genson.annotation.JsonIgnore;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;

public class AbstractCardObject {

    protected long _id;
    protected long _id_project;
    protected int title;
    protected int icon;

    protected Status status;
    protected boolean observation = true;
    protected long lastObservationTime;

    public AbstractCardObject(Project project) {
        this._id_project = project.get_id();
    }

    public AbstractCardObject() {
    }

    public static void init(MainActivity activity, Project project){
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
    public DBCardObject getDBSQLiteCardObject(MainActivity activity) {
        throw new NullPointerException("No database specified");
    }

    @JsonIgnore
    public void loadFromNetwork(MainActivity activity, Project project) {
        throw new NullPointerException("No network settings specified");
    }

    @JsonIgnore
    public void detectCardStatus(MainActivity activity) {
        throw new NullPointerException("No detection settings specified");
    }

    @JsonIgnore
    public void onClick(final MainActivity activity, final Project project) {
        throw new NullPointerException("No on click settings specified");
    }

    public String toString() {
        return JsonHelper.toJson(this);
    }
}
