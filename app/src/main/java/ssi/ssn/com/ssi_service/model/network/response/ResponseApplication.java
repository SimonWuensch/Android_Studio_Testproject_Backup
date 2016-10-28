package ssi.ssn.com.ssi_service.model.network.response;

import android.app.Activity;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.network.response.objects.ResponseBuild;
import ssi.ssn.com.ssi_service.model.network.response.objects.ResponseProject;
import ssi.ssn.com.ssi_service.model.network.response.objects.ResponseState;
import ssi.ssn.com.ssi_service.model.network.response.objects.ResponseTime;

public class ResponseApplication extends AbstractResponse {

    private ResponseState state;
    private String[] enabledModules;
    private ResponseProject project;
    private ResponseBuild build;
    private ResponseTime time;

    public ResponseApplication() {
    }

    public ResponseState getState() {
        return state;
    }

    public void setState(ResponseState state) {
        this.state = state;
    }

    private String[] getEnabledModules() {
        return enabledModules;
    }

    public void setEnabledModules(String[] enabledModules) {
        this.enabledModules = enabledModules;
    }

    public ResponseProject getProject() {
        return project;
    }

    public void setProject(ResponseProject project) {
        this.project = project;
    }

    public ResponseBuild getBuild() {
        return build;
    }

    public void setBuild(ResponseBuild build) {
        this.build = build;
    }

    private ResponseTime getTime() {
        return time;
    }

    public void setTime(ResponseTime time) {
        this.time = time;
    }

    @JsonIgnore
    @Override
    public List<CustomListObject> getCustomList(final Activity activity) {
        return new LinkedList<CustomListObject>() {
            {
                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_project_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_name), getProject().getName()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_location), getProject().getLocation()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_project_order_nr), getProject().getOrderNr()));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_state_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_state_status), getState().getStatus()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_state_since), FormatHelper.formatDate(getState().getSince())));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_build_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_version), getBuild().getVersion()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_number), getBuild().getNumber() + ""));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_builtBy), getBuild().getBuiltBy()));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_build_builtOn), FormatHelper.formatDate(getBuild().getBuiltOn())));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_time_headline)));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_time_stamp), FormatHelper.formatDate(getTime().getStamp())));
                add(new CustomListObject.Key_Value(activity.getString(R.string.fragment_custom_list_application_info_time_offset),
                        FormatHelper.formatMillisecondsToHours(getTime().getOffset()) + " " + activity.getString(R.string.hours)));

                add(new CustomListObject.HeadLine(activity.getString(R.string.fragment_custom_list_application_info_enabled_modules_headline)));
                String[] enabledModules = getEnabledModules();
                for (String module : enabledModules) {
                    add(new CustomListObject.Only_Key(module));
                }
            }
        };
    }
}
