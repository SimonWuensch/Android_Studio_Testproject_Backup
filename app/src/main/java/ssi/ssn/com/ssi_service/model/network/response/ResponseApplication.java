package ssi.ssn.com.ssi_service.model.network.response;

import android.app.Activity;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.model.handler.FormatHelper;
import ssi.ssn.com.ssi_service.model.network.response.objects.Build;
import ssi.ssn.com.ssi_service.model.network.response.objects.RestProject;
import ssi.ssn.com.ssi_service.model.network.response.objects.State;
import ssi.ssn.com.ssi_service.model.network.response.objects.Time;

public class ResponseApplication extends ResponseAbstract {

    private State state;
    private String [] enabledModules;
    @JsonProperty(value="project")
    private RestProject restProject;
    private Build build;
    private Time time;

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

    @JsonIgnore
    @Override
    public List<CustomListObject> getCustomList(final Activity activity) {
        return new LinkedList<CustomListObject>() {
            {
                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_project_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_name), getRestProject().getName()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_location), getRestProject().getLocation()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_order_nr), getRestProject().getOrderNr()));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_state_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_state_status), getState().getStatus()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_state_since),  FormatHelper.formatDate(getState().getSince())));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_build_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_version), getBuild().getVersion()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_number), getBuild().getNumber() + ""));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_builtBy), getBuild().getBuiltBy()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_builtOn),  FormatHelper.formatDate(getBuild().getBuiltOn())));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_time_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_time_stamp), FormatHelper.formatDate(getTime().getStamp())));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_time_offset), FormatHelper.formatLongToTime(getTime().getOffset())));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_enabled_modules_headline)));
                String[] enabledModules = getEnabledModules();
                for (String module : enabledModules) {
                    add(new CustomListObject.Only_Key(module));
                }
            }
        };
    }
}
