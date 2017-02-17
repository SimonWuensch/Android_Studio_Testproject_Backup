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
import ssi.ssn.com.ssi_service.fragment.launchboard.source.DetectorCardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectComponent;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.model.network.response.notification.ResponseNotificationTable;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class CardObjectNotification extends AbstractCardObject {

    private static int NOTIFICATION_ID = 3;

    private ResponseNotificationTable notificationTable;

    public CardObjectNotification(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_notification;
        this.icon = R.drawable.icon_notification;
    }

    public CardObjectNotification() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectNotification dbCardObject = sqLiteDB.cardObjectNotification();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectNotification cardObject = new CardObjectNotification(project);
            project.setCardObjectNotification(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectNotification(dbCardObject.getByProjectID(project.get_id()));
        }
    }

    public static int getNotificationId() {
        return NOTIFICATION_ID;
    }

    public static void setNotificationId(int notificationId) {
        NOTIFICATION_ID = notificationId;
    }

    public ResponseNotificationTable getNotificationTable() {
        return notificationTable;
    }

    public void setNotificationTable(ResponseNotificationTable notificationTable) {
        this.notificationTable = notificationTable;
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        return sqLiteDB.cardObjectNotification();
    }

    @Override
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        DetectorCardObjectNotification.loadFromNetwork(requestHandler, project, this);
    }

    @Override
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        //DetectorCardObjectComponent.detectCardStatus(sqLiteDB, this);
        setStatus(Status.ERROR);
        getDBSQLiteCardObject(sqLiteDB).update(this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        //TODO NOTIFICATION LIST IS NOT EMPTY EINFÜGEN
        if (getStatus().equals(Status.NOT_AVAILABLE)) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_notification), Toast.LENGTH_SHORT).show();
        } else {
            activity.showNotificationListFragment(project.get_id());
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
        return null;
        // return new FragmentComponentListNotification();
    }

    @Override
    @JsonIgnore
    public List<String> getNotificationMessages(Context context){
        /*
        List<String> messages = new LinkedList<>();
        for (ResponseComponent responseComponent : responseComponentList) {
            String status = responseComponent.getState().getStatus();
            if (!status.equals(Status.TEXT_ONLINE) &&
                    !status.equals(Status.TEXT_UNKNOWN)) {
                messages.add(responseComponent.getState().getName() + " " + SourceHelper.getString(context, R.string.status) + ": " + status);
            }
        }
        return  messages;
        */
        return new ArrayList<>();
    }

}
