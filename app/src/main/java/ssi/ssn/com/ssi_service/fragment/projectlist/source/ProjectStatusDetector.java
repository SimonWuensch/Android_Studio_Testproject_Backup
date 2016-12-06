package ssi.ssn.com.ssi_service.fragment.projectlist.source;

import android.app.Activity;
import android.os.AsyncTask;
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
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class ProjectStatusDetector {

    private Project project;
    private List<AbstractCardObject> cardObjects;

    private View vProjectStatus;

    public ProjectStatusDetector(Project project, View vProjectStatus) {
        this.project = project;
        this.cardObjects = initDefaultInputs();
        this.vProjectStatus = vProjectStatus;
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

    public void detectProjectStatus(final Activity activity) {
        RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
        final ExecutorService executor = requestHandler.getExecutor();
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

            @Override
            protected void onPostExecute(Object o) {
                vProjectStatus.setBackgroundColor(project.getStatus().getColor(activity));
            }
        }.executeOnExecutor(executor);

        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... object) {
                if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.OK)) {
                    return null;
                }

                for (AbstractCardObject cardObject : cardObjects) {
                    cardObject.set_ProjectID(project.get_id());
                    cardObject.setProjectStatusView(vProjectStatus);
                    cardObject.detectCardStatus(activity, project);
                    ((MainActivity) activity).addCardToMap(project, cardObject);
                }
                return null;
            }
        }.executeOnExecutor(executor);
    }
}
