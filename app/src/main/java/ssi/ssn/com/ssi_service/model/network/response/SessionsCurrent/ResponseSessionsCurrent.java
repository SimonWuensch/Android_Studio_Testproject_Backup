package ssi.ssn.com.ssi_service.model.network.response.sessionscurrent;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;
import ssi.ssn.com.ssi_service.model.network.response.sessionscurrent.objects.ResponseActor;

public class ResponseSessionsCurrent extends AbstractResponse {

    public long id;
    public String key;
    public String status;
    public ResponseActor actor;
    public String[] rights;
    public long createdOn;
    public long lastModifiedOn;
    public String clientAddress;
    public String clientAgent;

    public ResponseSessionsCurrent() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseActor getActor() {
        return actor;
    }

    public void setActor(ResponseActor actor) {
        this.actor = actor;
    }

    public String[] getRights() {
        return rights;
    }

    public void setRights(String[] rights) {
        this.rights = rights;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public long getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(long lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(String clientAgent) {
        this.clientAgent = clientAgent;
    }
}
