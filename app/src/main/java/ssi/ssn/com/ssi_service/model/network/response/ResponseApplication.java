package ssi.ssn.com.ssi_service.model.network.response;

import com.owlike.genson.annotation.JsonProperty;

import ssi.ssn.com.ssi_service.model.network.response.objects.Build;
import ssi.ssn.com.ssi_service.model.network.response.objects.RestProject;
import ssi.ssn.com.ssi_service.model.network.response.objects.State;
import ssi.ssn.com.ssi_service.model.network.response.objects.Time;

/**
 * Created by wuens on 28.09.2016.
 */

public class ResponseApplication extends ResponseAbstract {

    public State state;
    public String [] enabledModules;
    @JsonProperty(value="project")
    public RestProject restProject;
    public Build build;
    public Time time;

    public ResponseApplication() {
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String[] getEnabledModules() {
        return enabledModules;
    }

    public void setEnabledModules(String[] enabledModules) {
        this.enabledModules = enabledModules;
    }

    public RestProject getRestProject() {
        return restProject;
    }

    public void setRestProject(RestProject restProject) {
        this.restProject = restProject;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
