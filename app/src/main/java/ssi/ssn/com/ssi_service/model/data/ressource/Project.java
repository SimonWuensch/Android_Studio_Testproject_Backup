package ssi.ssn.com.ssi_service.model.data.ressource;

import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class Project extends AbstractProject{

    private long _id;
    private String serverAddress;
    private String userName;
    private String password;

    private String projectName;
    private String projectLocation;
    private String projectOrderNr;
    private boolean projectObservation;

    public Project(String serverAddress, String userName, String password) {
        this.serverAddress = serverAddress;
        this.userName = userName;
        this.password = password;
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

    public void loadProjectInfoFromApplicationInfo(){
        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, getDefaultResponseApplication().getResult());
        this.projectName= responseApplication.getRestProject().getName();
        this.projectLocation = responseApplication.getRestProject().getLocation();
        this.projectOrderNr = responseApplication.getRestProject().getOrderNr();
    }

    public String toString(){
        return JsonHelper.toJson(this);
    }
}
