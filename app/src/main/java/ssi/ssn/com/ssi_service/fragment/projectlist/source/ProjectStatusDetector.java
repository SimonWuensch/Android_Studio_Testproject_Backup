package ssi.ssn.com.ssi_service.fragment.projectlist.source;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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

    private static String TAG = ProjectStatusDetector.class.getSimpleName();

    private MainActivity activity;
    private Project project;
    private List<AbstractCardObject> cardObjects;

    private View vProjectStatus;

    public ProjectStatusDetector(MainActivity activity, Project project, View vProjectStatus) {
        this.activity = activity;
        this.project = project;
        this.cardObjects = initDefaultInputs();
        this.vProjectStatus = vProjectStatus;
    }

    public List<AbstractCardObject> getCardObjects() {
        return cardObjects;
    }

    private List<AbstractCardObject> initDefaultInputs() {
        return new LinkedList<AbstractCardObject>() {
            {
                add(new CardObjectModule(
                        activity,
                        project,
                        R.string.fragment_launch_board_card_module,
                        R.drawable.icon_modul,
                        true
                ));
                add(new CardObjectComponent(
                        activity,
                        project,
                        R.string.fragment_launch_board_card_component,
                        R.drawable.icon_component,
                        true
                ));
                /*add(new CardObjectNotification(
                        activity,
                        project,
                        R.string.fragment_launch_board_card_notification,
                        R.drawable.icon_notification,
                        true
                ));
                add(new CardObjectKPI(
                        activity,
                        project,
                        R.string.fragment_launch_board_card_kpi,
                        R.drawable.icon_kpi,
                        true
                ));*/
            }
        };
    }

    public void detectProjectStatus(final Activity activity) {
        final RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
        ExecutorService executor = ((MainActivity) activity).getExecutor();
        new AsyncTask<Object, Void, Status>() {
            @Override
            protected ssi.ssn.com.ssi_service.model.data.source.Status doInBackground(Object... objects) {
                requestHandler.sendRequestApplication(project);
                if (project.getDefaultResponseApplication().getCode() != 200) {
                    return ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE;
                }

                ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, project.getDefaultResponseApplication().getResult());
                if (!responseApplication.getState().getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_RUNNING)) {
                    return ssi.ssn.com.ssi_service.model.data.source.Status.ERROR;
                }

                return ssi.ssn.com.ssi_service.model.data.source.Status.OK;
            }

            @Override
            protected void onPostExecute(ssi.ssn.com.ssi_service.model.data.source.Status status) {
                project.setStatus(status);
                Log.d(TAG, "STATUS Project: " + project.identity() + " - Status: " + project.getStatus());
                vProjectStatus.setBackgroundColor(status.getColor(activity));

                if(!status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return;
                }

                for (AbstractCardObject cardObject : cardObjects) {
                    cardObject.set_ProjectID(project.get_id());
                    cardObject.setProjectStatusView(vProjectStatus);
                    cardObject.loadFromNetwork();
                    ((MainActivity) activity).addCardToMap(project, cardObject);
                }
            }
        }.executeOnExecutor(executor);
    }
}
