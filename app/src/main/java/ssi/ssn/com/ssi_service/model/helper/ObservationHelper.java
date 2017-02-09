package ssi.ssn.com.ssi_service.model.helper;

import android.util.Log;

import java.util.Date;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;

public class ObservationHelper {

    private static String TAG = ObservationHelper.class.getSimpleName();

    public static boolean isProjectOutOfDate(Project project) {
        long ageInMillis = new Date().getTime() - project.getLastObservationTime();
        long ageInMinutes = FormatHelper.formatMillisecondsToMinutes(ageInMillis);
        long observationIntervalInMinutes = FormatHelper.formatMillisecondsToMinutes(project.getObservationInterval());
        if (ageInMinutes < observationIntervalInMinutes && project.getLastObservationTime() != 0) {
            Log.d(TAG + " - " + project.identity(), "OUT OF TIME - FALSE: Next status check in [" + (observationIntervalInMinutes - ageInMinutes) + "] minutes.");
            return false;
        }

        if (project.getLastObservationTime() == 0) {
            Log.d(TAG + " - " + project.identity(), "OUT OF TIME - TRUE: Last observation time is [0]");
        } else {
            Log.d(TAG + " - " + project.identity(), "OUT OF TIME - TRUE: Project is [" + ageInMinutes + "] minutes old. Last observation time was  at [" + new Date(project.getLastObservationTime()) + "].");
        }
        return true;
    }

    public static boolean isCardObjectOutOfDate(Project project, AbstractCardObject cardObject) {
        long ageInMillis = new Date().getTime() - cardObject.getLastObservationTime();
        long ageInMinutes = FormatHelper.formatMillisecondsToMinutes(ageInMillis);
        long observationIntervalInMinutes = FormatHelper.formatMillisecondsToMinutes(project.getObservationInterval());
        if (ageInMinutes < observationIntervalInMinutes && cardObject.getLastObservationTime() != 0) {
            Log.d(TAG + " - " + project.identity() + " - " + cardObject.getClass().getSimpleName(), "OUT OF TIME - FALSE: Next status check in [" + (observationIntervalInMinutes - ageInMinutes) + "] minutes.");
            return false;
        }

        if (project.getLastObservationTime() == 0) {
            Log.d(TAG + " - " + project.identity() + " - " + cardObject.getClass().getSimpleName(), "OUT OF TIME - TRUE: Last observation time is [0]");
        } else {
            Log.d(TAG + " - " + project.identity() + " - " + cardObject.getClass().getSimpleName(), "OUT OF TIME - TRUE: Project is [" + ageInMinutes + "] minutes old. Last observation time was  at [" + new Date(project.getLastObservationTime()) + "].");
        }
        return true;
    }

    public static void setLastObservationTimeToNOW(MainActivity activity, Project project){
        setLastObservationTime(activity, project, new Date().getTime());
    }

    public static void setLastObservationTimeToOLD(MainActivity activity, Project project) {
        setLastObservationTime(activity, project, 0);
    }

    private static void setLastObservationTime(MainActivity activity, Project project, long millis) {
        if (!project.isProjectObservation()) {
            return;
        }

        project.setLastObservationTime(millis);
        activity.getSQLiteDB().project().updateLastObservationTime(project);
        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            if (!cardObject.isObservation()) {
                continue;
            }
            cardObject.setLastObservationTime(millis);
            cardObject.getDBSQLiteCardObject(activity).updateLastObservationTime(cardObject);
        }
    }
}
