package ssi.ssn.com.ssi_service.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.overview.launchboard.FragmentLaunchBoardNotification;
import ssi.ssn.com.ssi_service.fragment.list.project.FragmentProjectListNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class UpdateService extends Service {

    private static String TAG = UpdateService.class.getSimpleName();

    private final IBinder mBinder = new Binder();
    private int startID;

    protected SQLiteDB sqliteDB;
    protected RequestHandler requestHandler;
    private AndroidNotificationHelper androidNotificationHelper;
    private boolean isDelayRunning = true;

    private List<Project> projects = new ArrayList<>();

    private int count = 0;

    public class Binder extends android.os.Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    @Override
    public void onCreate() {
        androidNotificationHelper = new AndroidNotificationHelper();
        sqliteDB = new SQLiteDB(this);
        requestHandler = RequestHandler.initRequestHandler(Executors.newSingleThreadExecutor());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startID = startId;
        if(startId != 1){
            return START_STICKY;
        }

        //Toast.makeText(this, "Services Started", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Received start id " + startId + ": " + intent);

        projects = sqliteDB.project().getALL();

        Log.d(TAG, "Project list size is [" + projects.size() + "]");
        startDelay();
        //test();
        return START_STICKY;
    }

    public void test() {
        final Handler handler = new Handler();
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Log.i(TAG, "service is running... [" + count++ + "]");
                        handler.postDelayed(this, 1000);
                    }
                };
                handler.postDelayed(timerTask, 1000);
                return null;
            }
        }.execute();
    }

    public void startDelay() {
        for (final Project project : projects) {
            if (!project.isProjectObservation()) {
                continue;
            }
            final Handler handler = new Handler();
            final long interval = project.getObservationInterval();
            long ageInMillis = new Date().getTime() - project.getLastObservationTime();
            long firstStartAt;
            if (ObservationHelper.isProjectOutOfDate(project)) {
                firstStartAt = interval;
                detectStatusAndNotify(project);
            } else {
                firstStartAt = interval - ageInMillis;
            }
            Log.i(TAG, "First Start in [" + FormatHelper.formatMillisecondsToMinutes(firstStartAt) + "] minutes. Interval is [" + FormatHelper.formatMillisecondsToMinutes(interval) + "]. Project: [" + project + "]");

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!isDelayRunning) {
                        return;
                    }
                    Log.i(TAG, "Interval ended. Interval is [" + FormatHelper.formatMillisecondsToMinutes(interval) + "] minute. Project: [" + project + "]");
                    detectStatusAndNotify(project);
                    handler.postDelayed(this, interval);
                }
            };
            handler.postDelayed(timerTask, firstStartAt);
        }
        Log.i(TAG, "Delay started.");
    }

    private void detectStatusAndNotify(final Project project) {
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                project.detectProjectStatus(sqliteDB, requestHandler);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return;
                }

                switch (project.getApplicationStatus()) {
                    case NOT_AVAILABLE:
                        Log.w(TAG, "Throw notification - Project Status: [" + project.getApplicationStatus() + "]. Project: [" + project + "]");
                        androidNotificationHelper.throwNotification(
                                getBaseContext(),
                                project.get_id() * 1000,
                                new FragmentProjectListNotification().createResultIntent(getBaseContext(), project.get_id()),
                                project.getStatus().getColor(getBaseContext()),
                                R.drawable.icon_project,
                                SourceHelper.getString(getApplicationContext(), R.string.project_status) + " " + project.getApplicationStatus().name(),
                                SourceHelper.getString(getApplicationContext(), R.string.notification_project_is_not_available) + " \n" + project.designation(),
                                SourceHelper.getString(getApplicationContext(), R.string.project_status) + " " + project.getStatus());
                        return;
                    case ERROR:
                        Log.w(TAG, "Throw notification - Project Status: [" + project.getApplicationStatus() + "]. Project: [" + project + "]");
                        androidNotificationHelper.throwNotification(
                                getBaseContext(),
                                project.get_id() * 1000,
                                new FragmentProjectListNotification().createResultIntent(getBaseContext(), project.get_id()),
                                project.getStatus().getColor(getBaseContext()),
                                R.drawable.icon_project,
                                SourceHelper.getString(getApplicationContext(), R.string.project_status) + " " + project.getApplicationStatus().name(),
                                SourceHelper.getString(getApplicationContext(), R.string.notification_project_application_status_is_not_running) + " \n" + project.designation(),
                                SourceHelper.getString(getApplicationContext(), R.string.project_status) + " " + project.getStatus());
                        return;
                }

                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR)) {
                    return;
                }

                for (AbstractCardObject cardObject : project.getAllCardObjects()) {
                    if(!cardObject.isObservation()){
                        continue;
                    }
                    switch (cardObject.getStatus()) {
                        case NOT_AVAILABLE:
                            Log.w(TAG, "Throw notification - Project Status: [" + project.getStatus() + "]. " + cardObject.getTitle() + " Status: [" + cardObject.getStatus() + "]. Project: [" + project + "]");
                            androidNotificationHelper.throwNotification(
                                    getBaseContext(),
                                    project.get_id() * 1000 + cardObject.getNotificationID(),
                                    new FragmentLaunchBoardNotification().createResultIntent(getBaseContext(), project.get_id()),
                                    cardObject.getStatus().getColor(getBaseContext()),
                                    cardObject.getIcon(),
                                    SourceHelper.getString(getApplicationContext(), cardObject.getTitle()) + " " + SourceHelper.getString(getApplicationContext(), R.string.status) + ": " + cardObject.getStatus().name(),
                                    project.designation() + "\n" + SourceHelper.getString(getApplicationContext(), cardObject.getTitle()) + " " + SourceHelper.getString(getApplicationContext(), R.string.notification_not_available),
                                    SourceHelper.getString(getApplicationContext(), R.string.project_status) + " " + project.getStatus());
                            break;
                        case ERROR:
                            List<String> messages = cardObject.getNotificationMessages(getBaseContext());
                            StringBuilder notificationMessageBuilder = new StringBuilder();
                            for (String message : messages) {
                                notificationMessageBuilder.append(message).append("\n");
                            }
                            String notificationMessage = notificationMessageBuilder.toString();
                            Log.w(TAG, "Throw notification - Project Status: [" + project.getStatus() + "]. " + SourceHelper.getString(getBaseContext(), cardObject.getTitle()) + " Status: [" + cardObject.getStatus() + "]. Project: [" + project + "]. Message: " + notificationMessage);

                            androidNotificationHelper.throwNotification(
                                    getBaseContext(),
                                    project.get_id() * 1000 + cardObject.getNotificationID(),
                                    cardObject.getNotificationClass().createResultIntent(getBaseContext(), project.get_id()),
                                    cardObject.getStatus().getColor(getBaseContext()),
                                    cardObject.getIcon(),
                                    SourceHelper.getString(getApplicationContext(), cardObject.getTitle()) + " " + SourceHelper.getString(getApplicationContext(), R.string.status) + ": " + cardObject.getStatus().name(),
                                    project.designation() + "\n" + notificationMessage,
                                    SourceHelper.getString(getApplicationContext(), cardObject.getTitle()) + " " + SourceHelper.getString(getApplicationContext(), R.string.notification_includes_errors) + " [" + messages.size() + "]");
                            break;
                    }
                }
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Services Stopped", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Services Stopped. Start id: " + startID);
        stopDelay();
    }

    public void stopDelay() {
        isDelayRunning = false;
        Log.i(TAG, "Delay stopped.");
    }
}
