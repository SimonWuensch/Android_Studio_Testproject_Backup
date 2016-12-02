package ssi.ssn.com.ssi_service.fragment.projectlist.source;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractCardObject;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectComponent;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectKPI;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class ProjectStatusDetector {

    private Project project;
    private List<AbstractCardObject> cardObjects;

    public ProjectStatusDetector(Project project) {
        this.project = project;
        this.cardObjects = initDefaultInputs();
    }

    public ProjectStatusDetector(Project project, List<AbstractCardObject> cardObjects) {
        this.project = project;
        this.cardObjects = cardObjects;
    }

    public List<AbstractCardObject> getCardObjects() {
        return cardObjects;
    }

    private List<AbstractCardObject> initDefaultInputs() {
        return new LinkedList<AbstractCardObject>() {
            {
                add(new CardObjectModule(
                        R.string.fragment_launch_board_card_module,
                        R.drawable.icon_modul,
                        true
                ));
                add(new CardObjectComponent(
                        R.string.fragment_launch_board_card_component,
                        R.drawable.icon_component,
                        true
                ));
                add(new CardObjectNotification(
                        R.string.fragment_launch_board_card_notification,
                        R.drawable.icon_notification,
                        true
                ));
                add(new CardObjectKPI(
                        R.string.fragment_launch_board_card_kpi,
                        R.drawable.icon_kpi,
                        true
                ));
            }
        };
    }

    private boolean isOutOfDate(long observationInterval, long lastObservationTime) {
        return new Date().getTime() - lastObservationTime > observationInterval;
    }

    public ExecutorService detectProjectStatus(final Activity activity, final View vProjectState) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();

        requestHandler.getRequestApplicationTask(project).executeOnExecutor(executor);

        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                if (project.getDefaultResponseApplication().getCode() != 200) {
                    project.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE);
                    return null;
                }

                ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, project.getDefaultResponseApplication().getResult());
                if (!responseApplication.getState().getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.RUNNING)) {
                    project.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR);
                    return null;
                }
                project.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.OK);
                return null;
            }
        }.executeOnExecutor(executor);

        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... object) {
                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return null;
                }

                for (AbstractCardObject cardObject : cardObjects) {
                    cardObject.detectCardStatus(activity, project);
                }
                return null;
            }
        }.executeOnExecutor(executor);

        new AsyncTask<Object, Void, Status>() {
            @Override
            protected ssi.ssn.com.ssi_service.model.data.source.Status doInBackground(Object... objects) {
                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return project.getStatus();
                }

                for (AbstractCardObject cardObject : cardObjects) {
                    if (!cardObject.isObservation()) {
                        continue;
                    }

                    if (!cardObject.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                        project.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR);
                    }
                }
                return project.getStatus();
            }

            @Override
            protected void onPostExecute(ssi.ssn.com.ssi_service.model.data.source.Status status) {
                vProjectState.setBackgroundColor(status.getColor(activity));
                project.setLastObservationTime(new Date().getTime());
            }
        }.executeOnExecutor(executor);
        return executor;
    }

    //todo noch nicht fertig
    public ExecutorService detectCardObjectStatus(final Activity activity, final AbstractCardObject cardObject) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return null;
                }
                cardObject.detectCardStatus(activity, project);
                return null;
            }
        }.executeOnExecutor(executor);

        new AsyncTask<Object, Void, Status>() {
            @Override
            protected ssi.ssn.com.ssi_service.model.data.source.Status doInBackground(Object... objects) {
                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return project.getStatus();
                }

                for (AbstractCardObject cardObject : cardObjects) {
                    if (!cardObject.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                        project.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR);
                    }
                }
                return project.getStatus();
            }

            @Override
            protected void onPostExecute(ssi.ssn.com.ssi_service.model.data.source.Status status) {
                project.setLastObservationTime(new Date().getTime());
            }
        }.executeOnExecutor(executor);
        return executor;
    }
}
