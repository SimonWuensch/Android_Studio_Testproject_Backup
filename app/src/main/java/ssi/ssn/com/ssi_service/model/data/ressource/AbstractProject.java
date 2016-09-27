package ssi.ssn.com.ssi_service.model.data.ressource;

import ssi.ssn.com.ssi_service.model.network.Response;

public class AbstractProject {

    private Response responseApplication;
    private Response responseLogin;
    private Response responseLogout;
    private Response responseNotification;
    private Response responseOs;
    private Response responseScada;
    private Response responseSessionsCurrent;

    public Response getResponseApplication() {
        return responseApplication;
    }

    public void setResponseApplication(Response responseApplication) {
        this.responseApplication = responseApplication;
    }

    public Response getResponseLogin() {
        return responseLogin;
    }

    public void setResponseLogin(Response responseLogin) {
        this.responseLogin = responseLogin;
    }

    public Response getResponseLogout() {
        return responseLogout;
    }

    public void setResponseLogout(Response responseLogout) {
        this.responseLogout = responseLogout;
    }

    public Response getResponseNotification() {
        return responseNotification;
    }

    public void setResponseNotification(Response responseNotification) {
        this.responseNotification = responseNotification;
    }

    public Response getResponseOs() {
        return responseOs;
    }

    public void setResponseOs(Response responseOs) {
        this.responseOs = responseOs;
    }

    public Response getResponseScada() {
        return responseScada;
    }

    public void setResponseScada(Response responseScada) {
        this.responseScada = responseScada;
    }

    public Response getResponseSessionsCurrent() {
        return responseSessionsCurrent;
    }

    public void setResponseSessionsCurrent(Response responseSessionsCurrent) {
        this.responseSessionsCurrent = responseSessionsCurrent;
    }
}
