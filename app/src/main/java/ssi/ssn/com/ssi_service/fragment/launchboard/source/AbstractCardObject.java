package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;

public class AbstractCardObject {

    private Date lastStatusCheck;

    private long _ProjectID;
    private int title;
    private int icon;
    private boolean observation;
    private Status status;

    @JsonIgnore
    private View cardStatusView;
    @JsonIgnore
    private View projectStatusView;
    @JsonIgnore
    private View loadingView;

    public AbstractCardObject(int title, int icon, boolean observation) {
        this.title = title;
        this.icon = icon;
        this.observation = observation;
    }

    public AbstractCardObject() {
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

    public void onClick(Activity activity, Project project) {
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
        if (projectStatusView != null && !status.equals(Status.OK)){

            projectStatusView.setBackgroundColor(Status.ERROR.getColor(activity));
        }
    }

    public void detectCardStatus(Activity activity, Project project) {
    }

    public void reloadStatus(Activity activity, Project project) {
        lastStatusCheck = null;
        detectCardStatus(activity, project);
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
        if(this.status != null){
            this.cardStatusView.setBackgroundColor(this.status.getColor(activity));
            setLoadingViewVisible(false);
        }
    }

    @JsonIgnore
    public void setProjectStatusView(View statusView){
        this.projectStatusView = statusView;
    }

    @JsonIgnore
    public void loadFromNetwork(Activity activity, Project project) {
    }

    boolean isOutOfTime(Project project) {
        //TODO save lastStatusCheck in DB
        if (lastStatusCheck == null) {
            lastStatusCheck = new Date();
            Log.d(getClass().getSimpleName(), "First status Check at [" + lastStatusCheck + "].");
            return true;
        } else {
            long millis = new Date().getTime() - lastStatusCheck.getTime();
            long minutes = FormatHelper.formatMillisecondsToMinutes(millis);
            long observationInterval = FormatHelper.formatMillisecondsToMinutes(project.getObservationInterval());
            if (minutes < observationInterval) {
                Log.d(getClass().getSimpleName(), "Is not out of time. Last status check [" + minutes + "] minutes ago. Next status check in [" + (observationInterval - minutes) + "] minutes.");
                return false;
            } else {
                lastStatusCheck = new Date();
                Log.d(getClass().getSimpleName(), "Out of time. Last status check [" + minutes + "] minutes ago. Observation interval is [" + observationInterval + "] minutes.");
                return true;
            }
        }
    }
}
