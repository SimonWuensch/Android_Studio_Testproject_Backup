package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.content.Context;
import android.widget.Toast;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentListNotification;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.DetectorCardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectComponent;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.notification.AbstractAndroidNotification;

public class CardObjectComponent extends AbstractCardObject {

    private static int NOTIFICATION_ID = 2;

    private List<ResponseComponent> responseComponentList = new ArrayList<>();

    public CardObjectComponent(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_component;
        this.icon = R.drawable.icon_component;
    }

    public CardObjectComponent() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectComponent dbCardObject = sqLiteDB.cardObjectComponent();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectComponent cardObject = new CardObjectComponent(project);
            project.setCardObjectComponent(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectComponent(dbCardObject.getByProjectID(project.get_id()));
        }
    }

    public List<ResponseComponent> getResponseComponentList() {
        return responseComponentList;
    }

    public void setResponseComponentList(List<ResponseComponent> responseComponentList) {
        this.responseComponentList = responseComponentList;
    }


    @Override
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        return sqLiteDB.cardObjectComponent();
    }

    @Override
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        DetectorCardObjectComponent.loadFromNetwork(requestHandler, project, this);
    }

    @Override
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        DetectorCardObjectComponent.detectCardStatus(sqLiteDB, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE) || getResponseComponentList().isEmpty()) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_component), Toast.LENGTH_SHORT).show();
        } else {
            activity.showComponentListFragment(project.get_id());
        }
    }

    // ** Notification settings ***************************************************************** //
    @Override
    @JsonIgnore
    public int getNotificationID(){
        return NOTIFICATION_ID;
    }

    @Override
    @JsonIgnore
    public AbstractAndroidNotification getNotificationClass(){
        return new FragmentComponentListNotification();
    }

    @Override
    @JsonIgnore
    public List<String> getNotificationMessages(Context context){
        List<String> messages = new LinkedList<>();
        for (ResponseComponent responseComponent : responseComponentList) {
            String status = responseComponent.getState().getStatus();
            if (!status.equals(Status.TEXT_ONLINE) &&
                    !status.equals(Status.TEXT_UNKNOWN)) {
                messages.add(responseComponent.getState().getName() + " " + SourceHelper.getString(context, R.string.status) + ": " + status);
            }
        }
        return  messages;
    }
}
