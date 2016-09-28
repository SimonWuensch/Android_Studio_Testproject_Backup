package ssi.ssn.com.ssi_service.model.data.ressource;

import ssi.ssn.com.ssi_service.model.network.DefaultResponse;

public class AbstractProject {

    private DefaultResponse defaultResponseApplication;
    private DefaultResponse defaultResponseLogin;
    private DefaultResponse defaultResponseLogout;
    private DefaultResponse defaultResponseNotification;
    private DefaultResponse defaultResponseOs;
    private DefaultResponse defaultResponseScada;
    private DefaultResponse defaultResponseSessionsCurrent;

    public DefaultResponse getDefaultResponseApplication() {
        return defaultResponseApplication;
    }

    public void setDefaultResponseApplication(DefaultResponse defaultResponseApplication) {
        this.defaultResponseApplication = defaultResponseApplication;
    }

    public DefaultResponse getDefaultResponseLogin() {
        return defaultResponseLogin;
    }

    public void setDefaultResponseLogin(DefaultResponse defaultResponseLogin) {
        this.defaultResponseLogin = defaultResponseLogin;
    }

    public DefaultResponse getDefaultResponseLogout() {
        return defaultResponseLogout;
    }

    public void setDefaultResponseLogout(DefaultResponse defaultResponseLogout) {
        this.defaultResponseLogout = defaultResponseLogout;
    }

    public DefaultResponse getDefaultResponseNotification() {
        return defaultResponseNotification;
    }

    public void setDefaultResponseNotification(DefaultResponse defaultResponseNotification) {
        this.defaultResponseNotification = defaultResponseNotification;
    }

    public DefaultResponse getDefaultResponseOs() {
        return defaultResponseOs;
    }

    public void setDefaultResponseOs(DefaultResponse defaultResponseOs) {
        this.defaultResponseOs = defaultResponseOs;
    }

    public DefaultResponse getDefaultResponseScada() {
        return defaultResponseScada;
    }

    public void setDefaultResponseScada(DefaultResponse defaultResponseScada) {
        this.defaultResponseScada = defaultResponseScada;
    }

    public DefaultResponse getDefaultResponseSessionsCurrent() {
        return defaultResponseSessionsCurrent;
    }

    public void setDefaultResponseSessionsCurrent(DefaultResponse defaultResponseSessionsCurrent) {
        this.defaultResponseSessionsCurrent = defaultResponseSessionsCurrent;
    }
}
