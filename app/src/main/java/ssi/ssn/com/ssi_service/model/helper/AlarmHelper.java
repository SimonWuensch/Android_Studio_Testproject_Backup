package ssi.ssn.com.ssi_service.model.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;

public class AlarmHelper {

    private static String TAG = AlarmHelper.class.getSimpleName();

    public static void startAlertPerString(final Context context, List<Project> projects) {
        for (final Project project : projects) {
            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    startAlert(context, project);
                    return null;
                }
            }.execute();
        }
    }

    public static void startAlert(Context context, Project project) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(AlarmBroadcastReceiver.EXTRA_Project_ID, project.get_id());

        int requestCode = project.get_id() * 1000;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        long nextObservationInMillis = 0;
        if (!ObservationHelper.isProjectOutOfDate(project)) {
            nextObservationInMillis = project.getObservationInterval() - (System.currentTimeMillis() - project.getLastObservationTime());
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + project.getObservationInterval(), pendingIntent);
        Log.i(TAG, "Background observation interval started. Project: " + project.identity() + ", "
                + "interval: " + FormatHelper.formatMillisecondsToMinutes(project.getObservationInterval()) + ", "
                + "Next observation at " + FormatHelper.formatDate(System.currentTimeMillis() + nextObservationInMillis));
    }
}
