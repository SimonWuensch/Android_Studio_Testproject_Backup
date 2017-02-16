package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.util.Log;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class DetectorProject {

    public static String TAG = DetectorProject.class.getSimpleName();

    public static void detectApplicationStatus(RequestHandler requestHandler, Project project) {
        if (!ObservationHelper.isProjectOutOfDate(project) && project.getApplicationStatus() != null) {
            return;
        }

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

    public static void detectProjectStatus(SQLiteDB sqLiteDB, RequestHandler requestHandler, Project project) {
        Log.d(TAG, "Start detecting project status...");
        project.initCardObjects(sqLiteDB);

        project.detectApplicationStatus(requestHandler);
        if (!project.getApplicationStatus().equals(Status.OK)) {
            project.setStatus(project.getApplicationStatus());
            sqLiteDB.project().update(project);
            for (AbstractCardObject cardObject : project.getAllCardObjects()) {
                cardObject.setStatus(Status.NOT_AVAILABLE);
                cardObject.getDBSQLiteCardObject(sqLiteDB).update(cardObject);
            }
            return;
        }

        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            if (!cardObject.isObservation()) {
                Log.d(TAG + " - " + project.identity(), cardObject.getClass().getSimpleName() + " observation is disabled");
                continue;
            }

            if (!ObservationHelper.isCardObjectOutOfDate(project, cardObject)) {
                Log.d(TAG + " - " + project.identity(), cardObject.getClass().getSimpleName() + " status [" + cardObject.getStatus() + "]");
                continue;
            }

            cardObject.loadFromNetwork(requestHandler, project);
            cardObject.detectCardStatus(sqLiteDB);
        }

        Status overAllStatus = Status.OK;
        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            if (!cardObject.isObservation()) {
                continue;
            }

            if (cardObject.getStatus().equals(Status.ERROR) ||
                    cardObject.getStatus().equals(Status.NOT_AVAILABLE)) {
                overAllStatus = Status.ERROR;
            }
        }
        project.setStatus(overAllStatus);
        sqLiteDB.project().update(project);
        ObservationHelper.setLastObservationTimeToNOW(sqLiteDB, project);
        Log.i(TAG, "Project status detected - [" + project.getStatus() + "].");
    }
}
