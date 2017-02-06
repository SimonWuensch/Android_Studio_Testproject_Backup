package ssi.ssn.com.ssi_service.model.data.source;

import android.app.Activity;
import android.view.View;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;

import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class Project extends NetworkProject {

    private long _id;
    private String serverAddress;
    private String userName;
    private String password;

    private Status status;
    private long observationInterval;
    private boolean projectObservation = true;
    private long lastObservationTime;

    private String projectName;
    private String projectLocation;
    private String projectOrderNr;

    public Project(String serverAddress, String userName, String password, long observationInterval) {
        this.serverAddress = serverAddress;
        this.userName = userName;
        this.password = password;
        this.observationInterval = observationInterval;
    }

    public Project() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getObservationInterval() {
        return observationInterval;
    }

    public void setObservationInterval(long observationInterval) {
        this.observationInterval = observationInterval;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getProjectOrderNr() {
        return projectOrderNr;
    }

    public void setProjectOrderNr(String projectOrderNr) {
        this.projectOrderNr = projectOrderNr;
    }

    public boolean isProjectObservation() {
        return projectObservation;
    }

    public void setProjectObservation(boolean projectObservation) {
        this.projectObservation = projectObservation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getLastObservationTime() {
        return lastObservationTime;
    }

    public void setLastObservationTime(long lastObservationTime) {
        this.lastObservationTime = lastObservationTime;
    }

    public void loadFromNetwork() {
        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, getDefaultResponseApplication().getResult());
        this.projectName = responseApplication.getProject().getName();
        this.projectLocation = responseApplication.getProject().getLocation();
        this.projectOrderNr = responseApplication.getProject().getOrderNr();
    }

    public boolean isOutOfDate(){
        return new Date().getTime() - lastObservationTime > observationInterval;
    }

    public String toString() {
        return JsonHelper.toJson(this);
    }
}
