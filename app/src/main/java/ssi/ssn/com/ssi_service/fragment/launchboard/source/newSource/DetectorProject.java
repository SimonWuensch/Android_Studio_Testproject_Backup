package ssi.ssn.com.ssi_service.fragment.launchboard.source.newSource;

import android.util.Log;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class DetectorProject {

    public static String TAG = DetectorProject.class.getSimpleName();

    public static void detectApplicationStatus(MainActivity activity, Project project) {
        if (!ObservationHelper.isProjectOutOfDate(project) && project.getApplicationStatus() != null) {
            return;
        }

        final RequestHandler requestHandler = activity.getRequestHandler();
        requestHandler.sendRequestApplication(project);
        if (project.getDefaultResponseApplication().getCode() != 200) {
            project.setApplicationStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE);
            return;
        }

        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, project.getDefaultResponseApplication().getResult());
        if (!responseApplication.getState().getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_RUNNING)) {
            project.setApplicationStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR);
            return;
        }
        project.setApplicationStatus(ssi.ssn.com.ssi_service.model.data.source.Status.OK);
    }

    public static void detectProjectStatus(MainActivity activity, Project project){
        Log.d(TAG, "Start detecting project status...");
        project.initCardObjects(activity);

        project.detectApplicationStatus(activity);
        if(!project.getApplicationStatus().equals(Status.OK)){
            project.setStatus(project.getApplicationStatus());
            activity.getSQLiteDB().project().update(project);
            for (AbstractCardObject cardObject : project.getAllCardObjects()) {
                cardObject.setStatus(Status.NOT_AVAILABLE);
                cardObject.getDBSQLiteCardObject(activity).update(cardObject);
            }
            return;
        }

        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            if (!cardObject.isObservation() || !ObservationHelper.isCardObjectOutOfDate(project, cardObject)) {
                if(cardObject.getStatus() == null){
                    throw new NullPointerException("Card object status may not be null");
                }
                Log.d(TAG + " - " + project.identity(), cardObject.getClass().getSimpleName() + " status [" + cardObject.getStatus() + "]");
                continue;
            }

            cardObject.loadFromNetwork(activity, project);
            cardObject.detectCardStatus(activity);
        }

        Status overAllStatus = Status.OK;
        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            if(cardObject.getStatus().equals(Status.ERROR) ||
                    cardObject.getStatus().equals(Status.NOT_AVAILABLE)){
                overAllStatus = Status.ERROR;
            }
        }
        project.setStatus(overAllStatus);
        activity.getSQLiteDB().project().update(project);
        ObservationHelper.setLastObservationTimeToNOW(activity, project);
        Log.i(TAG, "Project status detected. [" + project.getStatus() + "]");
    }
}
