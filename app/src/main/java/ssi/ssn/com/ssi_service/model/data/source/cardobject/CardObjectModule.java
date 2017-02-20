package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.content.Context;
import android.widget.Toast;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.overview.launchboard.source.DetectorCardObjectModule;
import ssi.ssn.com.ssi_service.fragment.list.module.FragmentModuleListNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectModule;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class CardObjectModule extends AbstractCardObject {

    private static int NOTIFICATION_ID = 1;

    private List<ResponseModule> responseModuleList = new ArrayList<>();

    public CardObjectModule(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_module;
        this.icon = R.drawable.icon_modul;
    }

    public CardObjectModule() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectModule dbCardObject = sqLiteDB.cardObjectModule();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectModule cardObject = new CardObjectModule(project);
            project.setCardObjectModule(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectModule(dbCardObject.getByProjectID(project.get_id()));
        }
    }

    public List<ResponseModule> getResponseModuleList() {
        return responseModuleList;
    }

    public void setResponseModuleList(List<ResponseModule> responseModuleList) {
        this.responseModuleList = responseModuleList;
    }

    public void addResponseModule(ResponseModule responseModule) {
        responseModuleList.add(responseModule);
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        return sqLiteDB.cardObjectModule();
    }

    @Override
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        DetectorCardObjectModule.loadFromNetwork(requestHandler, project, this);
    }

    @Override
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        DetectorCardObjectModule.detectCardStatus(sqLiteDB, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE) || getResponseModuleList().isEmpty()) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_module), Toast.LENGTH_SHORT).show();
        } else {
            activity.showModuleListFragment(project.get_id());
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
        return new FragmentModuleListNotification();
    }

    @Override
    @JsonIgnore
    public List<String> getNotificationMessages(Context context){
        List<String> messages = new LinkedList<>();
        for (ResponseModule responseModule : responseModuleList) {
            String status = responseModule.getStatus();
            if (!Boolean.valueOf(responseModule.getEnabled())) {
                continue;
            } else if (!status.equals(Status.TEXT_RUNNING) &&
                    !status.equals(Status.TEXT_UNKNOWN)) {
                messages.add(responseModule.getName() + " " + SourceHelper.getString(context, R.string.status) + ": " + status);
            }
        }
        return messages;
    }
}
