package ssi.ssn.com.ssi_service.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.list.project.FragmentProjectListNotification;
import ssi.ssn.com.ssi_service.fragment.overview.launchboard.FragmentLaunchBoardNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.notification.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.NetworkConnectionChecker;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class NotificationCreator {

    private static String TAG = NotificationCreator.class.getSimpleName();
    private int startID;

    private Context context;

    private SQLiteDB sqliteDB;
    private AndroidNotificationHelper androidNotificationHelper;
    private RequestHandler requestHandler;
    private List<Project> projects = new ArrayList<>();

    private boolean isDelayRunning = true;

    private int count = 0;

    public static NotificationCreator init(Context context){
        return new NotificationCreator(context);
    }

    public NotificationCreator(Context context){
        this.context = context;
        this.sqliteDB = new SQLiteDB(context);
        androidNotificationHelper = new AndroidNotificationHelper();
        requestHandler = RequestHandler.initRequestHandler(Executors.newSingleThreadExecutor());
        projects = sqliteDB.project().getALL();
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
            Log.i(TAG + " " + project.identity(), "First Start in [" + FormatHelper.formatMillisecondsToMinutes(firstStartAt) + "] minutes. Interval is [" + FormatHelper.formatMillisecondsToMinutes(interval) + "]. Project: [" + project + "]");

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!isDelayRunning) {
                        return;
                    }
                    Log.i(TAG, "Interval ended. Interval is [" + FormatHelper.formatMillisecondsToMinutes(interval) + "] minute.");
                    detectStatusAndNotify(project);
                    handler.postDelayed(this, interval);
                }
            };
            handler.postDelayed(timerTask, firstStartAt);
        }
        Log.i(TAG, "Delay started.");
    }

    private void detectStatusAndNotify(final Project project) {
        if (!NetworkConnectionChecker.getInstance(context).isOnline()) {
            Log.d(NotificationCreator.TAG, "No network connection found...");
            return;
        }
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
                                context,
                                project.get_id() * 1000,
                                new FragmentProjectListNotification().createResultIntent(context, project.get_id()),
                                project.getStatus().getColor(context),
                                R.drawable.icon_project,
                                SourceHelper.getString(context, R.string.project_status) + " " + project.getApplicationStatus().name(),
                                SourceHelper.getString(context, R.string.notification_project_is_not_available) + " \n" + project.designation(),
                                SourceHelper.getString(context, R.string.project_status) + " " + project.getStatus());
                        return;
                    case ERROR:
                        Log.w(TAG, "Throw notification - Project Status: [" + project.getApplicationStatus() + "]. Project: [" + project + "]");
                        androidNotificationHelper.throwNotification(
                                context,
                                project.get_id() * 1000,
                                new FragmentProjectListNotification().createResultIntent(context, project.get_id()),
                                project.getStatus().getColor(context),
                                R.drawable.icon_project,
                                SourceHelper.getString(context, R.string.project_status) + " " + project.getApplicationStatus().name(),
                                SourceHelper.getString(context, R.string.notification_project_application_status_is_not_running) + " \n" + project.designation(),
                                SourceHelper.getString(context, R.string.project_status) + " " + project.getStatus());
                        return;
                }

                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR)) {
                    return;
                }

                for (AbstractCardObject cardObject : project.getAllCardObjects()) {
                    if (!cardObject.isObservation()) {
                        continue;
                    }
                    switch (cardObject.getStatus()) {
                        case NOT_AVAILABLE:
                            Log.w(TAG, "Throw notification - Project Status: [" + project.getStatus() + "]. " + cardObject.getTitle() + " Status: [" + cardObject.getStatus() + "]. Project: [" + project + "]");
                            androidNotificationHelper.throwNotification(
                                    context,
                                    project.get_id() * 1000 + cardObject.getNotificationID(),
                                    new FragmentLaunchBoardNotification().createResultIntent(context, project.get_id()),
                                    cardObject.getStatus().getColor(context),
                                    cardObject.getIcon(),
                                    SourceHelper.getString(context, cardObject.getTitle()) + " " + SourceHelper.getString(context, R.string.status) + ": " + cardObject.getStatus().name(),
                                    SourceHelper.getString(context, R.string.project) + ": " + project.designation() + "\n" + SourceHelper.getString(context, cardObject.getTitle()) + " " + SourceHelper.getString(context, R.string.notification_not_available),
                                    SourceHelper.getString(context, R.string.project_status) + " " + project.getStatus());
                            break;
                        case ERROR:
                            List<String> messages = cardObject.getNotificationMessages(context);
                            StringBuilder notificationMessageBuilder = new StringBuilder();
                            for (String message : messages) {
                                notificationMessageBuilder.append(message).append("\n");
                            }
                            String notificationMessage = notificationMessageBuilder.toString();
                            Log.w(TAG, "Throw notification - Project Status: [" + project.getStatus() + "]. " + SourceHelper.getString(context, cardObject.getTitle()) + " Status: [" + cardObject.getStatus() + "]. Project: [" + project + "]. Message: " + notificationMessage);

                            String title = SourceHelper.getString(context, cardObject.getTitle());
                            if (!(cardObject instanceof CardObjectNotification)) {
                                androidNotificationHelper.throwNotification(
                                        context,
                                        project.get_id() * 1000 + cardObject.getNotificationID(),
                                        cardObject.getNotificationClass().createResultIntent(context, project.get_id()),
                                        cardObject.getStatus().getColor(context),
                                        cardObject.getIcon(),
                                        title + " " + SourceHelper.getString(context, R.string.status) + ": " + cardObject.getStatus().name(),
                                        SourceHelper.getString(context, R.string.project) + ": " + project.designation() + "\n" + notificationMessage,
                                        title + " " + SourceHelper.getString(context, R.string.notification_includes_errors) + " [" + messages.size() + "]");
                                break;
                            }

                            for (FilterNotification filter : ((CardObjectNotification) cardObject).getNotificationFilters()) {
                                if (filter.getActiveTimeReachedNotificationTable().getCount() <= 0) {
                                    continue;
                                }

                                String filterDesignation = filter.getNote() + " - " + filter.getActiveTime() + " - " + filter.getSeverity() + " - " + filter.getText();
                                String message = title + " " + SourceHelper.getString(context, R.string.found) + ": " + filter.getActiveTimeReachedNotificationTable().getCount();

                                androidNotificationHelper.throwNotification(
                                        context,
                                        project.get_id() * 1000 + cardObject.getNotificationID() * 1000 + filter.getId(),
                                        cardObject.getNotificationClass().createResultIntent(context, project.get_id(), filter.getId()),
                                        cardObject.getStatus().getColor(context),
                                        cardObject.getIcon(),
                                        title + " " + SourceHelper.getString(context, R.string.status) + ": " + cardObject.getStatus().name(),
                                        SourceHelper.getString(context, R.string.project) + ": " + project.designation() + "\n" + SourceHelper.getString(context, R.string.filter) + ": " + filterDesignation + "\n" + message,
                                        filter.getActiveTimeReachedNotificationTable().getCount() + " " + title + " " + SourceHelper.getString(context, R.string.notification_filter));
                            }
                            break;
                    }
                }
            }
        }.execute();
    }
}
