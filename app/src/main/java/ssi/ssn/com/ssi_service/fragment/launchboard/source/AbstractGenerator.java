package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;

public class AbstractGenerator {

    /*private long _ProjectID;
    private int title;
    private int icon;
    private boolean observation;
    private Status status;

    @JsonIgnore
    protected MainActivity activity;
    @JsonIgnore
    protected Project project;

    @JsonIgnore
    private View cardStatusView;
    @JsonIgnore
    private View projectStatusView;
    @JsonIgnore
    private View loadingView;

    public AbstractGenerator(MainActivity activty, Project project, int title, int icon, boolean observation) {
        this._ProjectID = project.get_id();
        this.title = title;
        this.icon = icon;
        this.observation = observation;
        this.activity = activty;
        this.project = project;
    }

    public AbstractGenerator() {
    }

    public long get_ProjectID() {
        return _ProjectID;
    }

    public void set_ProjectID(long _ProjectID) {
        this._ProjectID = _ProjectID;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isObservation() {
        return observation;
    }

    public void setObservation(boolean observation) {
        this.observation = observation;
    }

    // ** Not in Json Object ******************************************************************** //

    public void onClick() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status, Activity activity) {
        this.status = status;
        if (cardStatusView != null) {
            cardStatusView.setBackgroundColor(status.getColor(activity));
            setLoadingViewVisible(false);
        }
        if (projectStatusView != null && !status.equals(Status.OK)) {

            projectStatusView.setBackgroundColor(Status.ERROR.getColor(activity));
        }
    }

    public void detectCardStatus() {
    }

    public void setLastObservationTime(long millis){
        project.setLastObservationTime(millis);
        activity.getSQLiteDB().project().updateLastObservationTime(project);
    }

    public void reloadStatus(Activity activity, Project project) {
        setLastObservationTime(0);
        detectCardStatus();
    }

    @JsonIgnore
    public View getLoadingView() {
        return loadingView;
    }

    @JsonIgnore
    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    public void setLoadingViewVisible(boolean isVisible) {
        if (loadingView == null) {
            return;
        }
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @JsonIgnore
    public void setStatusView(View statusView, Activity activity) {
        this.cardStatusView = statusView;
        if (this.status != null) {
            this.cardStatusView.setBackgroundColor(this.status.getColor(activity));
            setLoadingViewVisible(false);
        }
    }

    @JsonIgnore
    public void setProjectStatusView(View statusView) {
        this.projectStatusView = statusView;
    }

    @JsonIgnore
    public void loadFromNetwork() {
    }

    boolean isOutOfTime() {
        if (project.getLastObservationTime() == 0) {
            Date newDate = new Date();
            Log.d(getClass().getSimpleName(), "STATUS - FIRST CHECK: Project " + project.identity() + " at [" + newDate + "]");
            setLastObservationTime(newDate.getTime());
            return true;
        } else {
            long millis = new Date().getTime() - project.getLastObservationTime();
            long minutes = FormatHelper.formatMillisecondsToMinutes(millis);
            long observationInterval = FormatHelper.formatMillisecondsToMinutes(project.getObservationInterval());
            if (minutes < observationInterval) {
                Log.d(getClass().getSimpleName(), "NOT OUT OF TIME: Last status check [" + minutes + "] minutes ago. Next status check in [" + (observationInterval - minutes) + "] minutes.");
                return false;
            } else {
                Log.d(getClass().getSimpleName(), "OUT OF TIME: Last status check [" + minutes + "] minutes ago. Observation interval is [" + observationInterval + "] minutes.");
                setLastObservationTime(new Date().getTime());
                return true;
            }
        }
    }*/
}
