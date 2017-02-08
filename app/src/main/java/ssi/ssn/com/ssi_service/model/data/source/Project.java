package ssi.ssn.com.ssi_service.model.data.source;

import com.owlike.genson.annotation.JsonIgnore;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class Project extends NetworkProject {

    private long _id;
    private String serverAddress;
    private String userName;
    private String password;
    private long observationInterval;
    private boolean projectObservation = true;

    private Status status;
    private long lastObservationTime;

    private String projectName;
    private String projectLocation;
    private String projectOrderNr;

    @JsonIgnore
    private CardObjectModule cardObjectModule;
    @JsonIgnore
    private CardObjectComponent cardObjectComponent;


    public Project(String serverAddress, String userName, String password, long observationInterval) {
        this.serverAddress = serverAddress;
        this.userName = userName;
        this.password = password;
        this.observationInterval = observationInterval;
    }

    public Project() {
    }

    // ** User Settings ************************************************************************* //


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
    public void setCardObjectModule(CardObjectModule cardObjectModule) {
        this.cardObjectModule = cardObjectModule;
    }

    @JsonIgnore
    public void setCardObjectComponent(CardObjectComponent cardObjectComponent) {
        this.cardObjectComponent = cardObjectComponent;
    }

    @JsonIgnore
    public CardObjectModule getCardObjectModule() {
        return cardObjectModule;
    }

    @JsonIgnore
    public CardObjectComponent getCardObjectComponent() {
        return cardObjectComponent;
    }

    public void loadCardObjects(MainActivity activity) {
        generateCardObject(activity.getSQLiteDB().cardObjectModule(), cardObjectModule);
        generateCardObject(activity.getSQLiteDB().cardObjectComponent(), cardObjectComponent);
    }

    private AbstractCardObject generateCardObject(DBCardObject dbCardObject, AbstractCardObject cardObject) {
        if (dbCardObject.getCount(_id) == 0) {
            cardObject = new AbstractCardObject(this);
            dbCardObject.add(cardObject);
            return cardObject;
        }
        return dbCardObject.getByProjectID(_id);
    }

    // ** Others ******************************************************************************** //

    public void loadFromNetwork() {
        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, getDefaultResponseApplication().getResult());
        this.projectName = responseApplication.getProject().getName();
        this.projectLocation = responseApplication.getProject().getLocation();
        this.projectOrderNr = responseApplication.getProject().getOrderNr();
    }

    public String toString() {
        return JsonHelper.toJson(this);
    }

    public String identity() {
        return "[" + get_id() + ";" + getProjectName() + ";" + getProjectLocation() + ";" + getProjectOrderNr() + "]";
    }
}
