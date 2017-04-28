package ssi.ssn.com.ssi_service.model.data.source;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKPI;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.detector.DetectorProject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class Project extends NetworkProject {

    private int _id;
    private String serverAddress;
    private String userName;
    private String password;
    private String alias;
    private long observationInterval;
    private boolean projectObservation = true;

    private Status status;
    private long lastObservationTime;

    private String projectName;
    private String projectLocation;
    private String projectOrderNr;
    private String projectVersion;
    private Status applicationStatus;

    @JsonIgnore
    private CardObjectModule cardObjectModule;
    @JsonIgnore
    private CardObjectComponent cardObjectComponent;
    @JsonIgnore
    private CardObjectNotification cardObjectNotification;
    @JsonIgnore
    private CardObjectKPI cardObjectKPI;

    public Project(String serverAddress, String userName, String password, long observationInterval) {
        this.serverAddress = serverAddress;
        this.userName = userName;
        this.password = password;
        this.observationInterval = observationInterval;
    }

    public Project() {
    }

    // ** User Settings ************************************************************************* //

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getObservationInterval() {
        return observationInterval;
    }

    public void setObservationInterval(long observationInterval) {
        this.observationInterval = observationInterval;
    }

    public boolean isProjectObservation() {
        return projectObservation;
    }

    public void setProjectObservation(boolean projectObservation) {
        this.projectObservation = projectObservation;
    }

    // ** Application Information *************************************************************** //

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

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public Status getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Status applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    // ** Runtime Information ******************************************************************* //

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

    // ** Card Objects *************************************************************************** //

    @JsonIgnore
    public CardObjectModule getCardObjectModule() {
        return cardObjectModule;
    }

    @JsonIgnore
    public void setCardObjectModule(CardObjectModule cardObjectModule) {
        this.cardObjectModule = cardObjectModule;
    }

    @JsonIgnore
    public CardObjectComponent getCardObjectComponent() {
        return cardObjectComponent;
    }

    @JsonIgnore
    public void setCardObjectComponent(CardObjectComponent cardObjectComponent) {
        this.cardObjectComponent = cardObjectComponent;
    }

    @JsonIgnore
    public CardObjectNotification getCardObjectNotification() {
        return cardObjectNotification;
    }

    @JsonIgnore
    public void setCardObjectNotification(CardObjectNotification cardObjectNotification) {
        this.cardObjectNotification = cardObjectNotification;
    }

    @JsonIgnore
    public CardObjectKPI getCardObjectKPI() {
        return cardObjectKPI;
    }

    @JsonIgnore
    public void setCardObjectKPI(CardObjectKPI cardObjectKPI) {
        this.cardObjectKPI = cardObjectKPI;
    }

    @JsonIgnore
    public List<AbstractCardObject> getAllCardObjects() {
        return new LinkedList<AbstractCardObject>() {
            {
                add(cardObjectModule);
                add(cardObjectComponent);
                add(cardObjectNotification);
                add(cardObjectKPI);
            }
        };
    }

    public void initCardObjects(SQLiteDB sqLiteDB) {
        CardObjectModule.init(sqLiteDB, this);
        CardObjectComponent.init(sqLiteDB, this);
        cardObjectNotification.init(sqLiteDB, this);
        cardObjectKPI.init(sqLiteDB, this);
    }

    // ** Others ******************************************************************************** //

    public void loadFromNetwork() {
        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, getDefaultResponseApplication().getResult());
        this.projectName = responseApplication.getProject().getName();
        this.projectLocation = responseApplication.getProject().getLocation();
        this.projectOrderNr = responseApplication.getProject().getOrderNr();
        this.projectVersion = responseApplication.getBuild().getVersion();
    }

    public void detectApplicationStatus(RequestHandler requestHandler) {
        DetectorProject.detectApplicationStatus(requestHandler, this);
    }

    public void detectProjectStatus(SQLiteDB sqLiteDB, RequestHandler requestHandler) {
        DetectorProject.detectProjectStatus(sqLiteDB, requestHandler, this);
    }

    public String toString() {
        return JsonHelper.toJson(this);
    }

    public String identity() {
        return "[" + get_id() + ";" + getProjectName() + ";" + getProjectLocation() + ";" + getProjectOrderNr() + "]";
    }

    public String designation() {
        return getProjectName() + " - " + getProjectLocation() + " - " + getProjectOrderNr() + "\n" + "IP: " + getServerAddress();
    }
}
