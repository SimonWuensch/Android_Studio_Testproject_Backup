package ssi.ssn.com.ssi_service.model.data.source;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.network.DefaultResponse;

public class NetworkProject {

    private DefaultResponse defaultResponseApplication;
    @JsonIgnore
    private DefaultResponse defaultResponseApplicationConfig;
    @JsonIgnore
    private DefaultResponse defaultResponseLogin;
    @JsonIgnore
    private DefaultResponse defaultResponseLogout;
    @JsonIgnore
    private DefaultResponse defaultResponseNotification;
    @JsonIgnore
    private DefaultResponse defaultResponseNotificationCount;
    @JsonIgnore
    private DefaultResponse defaultResponseOs;
    @JsonIgnore
    private DefaultResponse defaultResponseScada;
    @JsonIgnore
    private DefaultResponse defaultResponseSessionsCurrent;
    @JsonIgnore
    private List<DefaultResponse> defaultResponseComponentList;
    @JsonIgnore
    private List<DefaultResponse> defaultResponseModuleList;

    public NetworkProject() {
    }

    public DefaultResponse getDefaultResponseApplication() {
        return defaultResponseApplication;
    }

    public void setDefaultResponseApplication(DefaultResponse defaultResponseApplication) {
        this.defaultResponseApplication = defaultResponseApplication;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseApplicationConfig() {
        return defaultResponseApplicationConfig;
    }

    @JsonIgnore
    public void setDefaultResponseApplicationConfig(DefaultResponse defaultResponseApplicationConfig) {
        this.defaultResponseApplicationConfig = defaultResponseApplicationConfig;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseLogin() {
        return defaultResponseLogin;
    }

    @JsonIgnore
    public void setDefaultResponseLogin(DefaultResponse defaultResponseLogin) {
        this.defaultResponseLogin = defaultResponseLogin;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseLogout() {
        return defaultResponseLogout;
    }

    @JsonIgnore
    public void setDefaultResponseLogout(DefaultResponse defaultResponseLogout) {
        this.defaultResponseLogout = defaultResponseLogout;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseNotification() {
        return defaultResponseNotification;
    }

    @JsonIgnore
    public void setDefaultResponseNotification(DefaultResponse defaultResponseNotification) {
        this.defaultResponseNotification = defaultResponseNotification;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseNotificationCount() {
        return defaultResponseNotificationCount;
    }

    @JsonIgnore
    public void setDefaultResponseNotificationCount(DefaultResponse defaultResponseNotificationCount) {
        this.defaultResponseNotificationCount = defaultResponseNotificationCount;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseOs() {
        return defaultResponseOs;
    }

    @JsonIgnore
    public void setDefaultResponseOs(DefaultResponse defaultResponseOs) {
        this.defaultResponseOs = defaultResponseOs;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseScada() {
        return defaultResponseScada;
    }

    @JsonIgnore
    public void setDefaultResponseScada(DefaultResponse defaultResponseScada) {
        this.defaultResponseScada = defaultResponseScada;
    }

    @JsonIgnore
    public DefaultResponse getDefaultResponseSessionsCurrent() {
        return defaultResponseSessionsCurrent;
    }

    @JsonIgnore
    public void setDefaultResponseSessionsCurrent(DefaultResponse defaultResponseSessionsCurrent) {
        this.defaultResponseSessionsCurrent = defaultResponseSessionsCurrent;
    }

    @JsonIgnore
    public List<DefaultResponse> getDefaultResponseComponentList() {
        return defaultResponseComponentList == null ? new ArrayList<DefaultResponse>() : defaultResponseComponentList;
    }

    @JsonIgnore
    public void setDefaultResponseComponentList(List<DefaultResponse> defaultResponseComponentList) {
        this.defaultResponseComponentList = defaultResponseComponentList;
    }

    public void addDefaultResponseComponent(DefaultResponse defaultResponseComponent) {
        if (defaultResponseComponentList == null) {
            defaultResponseComponentList = new ArrayList<>();
        }
        defaultResponseComponentList.add(defaultResponseComponent);
    }

    @JsonIgnore
    public List<DefaultResponse> getDefaultResponseModuleList() {
        return defaultResponseModuleList == null ? new ArrayList<DefaultResponse>() : defaultResponseModuleList;
    }

    @JsonIgnore
    public void setDefaultResponseModuleList(List<DefaultResponse> defaultResponseModuleList) {
        this.defaultResponseModuleList = defaultResponseModuleList;
    }

    public void addDefaultResponseModule(DefaultResponse defaultResponseModule) {
        if (defaultResponseModuleList == null) {
            defaultResponseModuleList = new ArrayList<>();
        }
        defaultResponseModuleList.add(defaultResponseModule);
    }
}
