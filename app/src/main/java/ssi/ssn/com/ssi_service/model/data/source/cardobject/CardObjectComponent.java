package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.DetectorCardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectComponent;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class CardObjectComponent extends AbstractCardObject{

    private List<ResponseComponent> responseComponentList = new ArrayList<>();

    public CardObjectComponent(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_component;
        this.icon = R.drawable.icon_component;
    }

    public CardObjectComponent() {
    }

    public static void init(MainActivity activity, Project project) {
        if (project.getCardObjectComponent() == null) {
            DBCardObjectComponent dbCardObject = activity.getSQLiteDB().cardObjectComponent();
            if (dbCardObject.getCount(project.get_id()) == 0) {
                CardObjectComponent cardObject = new CardObjectComponent(project);
                project.setCardObjectComponent(cardObject);
                dbCardObject.add(cardObject);
            } else {
                project.setCardObjectComponent(dbCardObject.getByProjectID(project.get_id()));
            }
        }
    }

    public List<ResponseComponent> getResponseComponentList() {
        return responseComponentList;
    }

    public void setResponseComponentList(List<ResponseComponent> responseComponentList) {
        this.responseComponentList = responseComponentList;
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(MainActivity activity){
        return activity.getSQLiteDB().cardObjectComponent();
    }

    @Override
    public void loadFromNetwork(MainActivity activity, Project project) {
        DetectorCardObjectComponent.loadFromNetwork(activity, project, this);
    }

    @Override
    public void detectCardStatus(MainActivity activity) {
        DetectorCardObjectComponent.detectCardStatus(activity, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE) || getResponseComponentList().isEmpty()) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_component), Toast.LENGTH_SHORT).show();
        } else {
            activity.showComponentListFragment(project);
        }
    }
}
