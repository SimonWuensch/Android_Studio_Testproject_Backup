package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.newSource.DetectorCardObjectModule;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectModule;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class CardObjectModule extends AbstractCardObject{

    private List<ResponseModule> responseModuleList = new ArrayList<>();

    public CardObjectModule(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_module;
        this.icon = R.drawable.icon_modul;
    }

    public CardObjectModule() {
    }

    public static void init(MainActivity activity, Project project){
        if (project.getCardObjectModule() == null){
            DBCardObjectModule dbCardObject = activity.getSQLiteDB().cardObjectModule();
            if (dbCardObject.getCount(project.get_id()) == 0) {
                CardObjectModule cardObject = new CardObjectModule(project);
                project.setCardObjectModule(cardObject);
                dbCardObject.add(cardObject);
            }else{
                project.setCardObjectModule(dbCardObject.getByProjectID(project.get_id()));
            }
        }
    }

    public List<ResponseModule> getResponseModuleList() {
        return responseModuleList;
    }

    public void setResponseModuleList(List<ResponseModule> responseModuleList) {
        this.responseModuleList = responseModuleList;
    }

    public void addResponseModule(ResponseModule responseModule){
        responseModuleList.add(responseModule);
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(MainActivity activity){
        return activity.getSQLiteDB().cardObjectModule();
    }

    @Override
    public void loadFromNetwork(MainActivity activity, Project project){
        DetectorCardObjectModule.loadFromNetwork(activity, project, this);
    }

    @Override
    public void detectCardStatus(MainActivity activity){
        DetectorCardObjectModule.detectCardStatus(activity, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE) || getResponseModuleList().isEmpty()) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_module), Toast.LENGTH_SHORT).show();
        } else {
            activity.showModuleListFragment(project);
        }
    }
}
