package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.view.View;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Status;

public class AbstractCardObject {

    private long _ProjectID;
    private int title;
    private int icon;
    private boolean observation;
    @JsonIgnore private Status status;
    @JsonIgnore private View statusView;

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

    @JsonIgnore
    public Status getStatus() {
        return status;
    }

    @JsonIgnore
    public void setStatus(Status status, Activity activity) {
        this.status = status;
        if (statusView != null) {
            statusView.setBackgroundColor(status.getColor(activity));
        }
    }

    @JsonIgnore
    public View getStatusView() {
        return statusView;
    }

    @JsonIgnore
    public void setStatusView(View statusView) {
        this.statusView = statusView;
    }

}
