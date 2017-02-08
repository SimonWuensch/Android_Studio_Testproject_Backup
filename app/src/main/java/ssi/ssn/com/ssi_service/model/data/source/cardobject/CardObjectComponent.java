package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class CardObjectComponent extends AbstractCardObject {

    private List<ResponseComponent> responseComponentList = new ArrayList<>();

    public CardObjectComponent(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_component;
        this.icon = R.drawable.icon_component;
    }

    public CardObjectComponent() {
    }

    public List<ResponseComponent> getResponseComponentList() {
        return responseComponentList;
    }

    public void setResponseComponentList(List<ResponseComponent> responseComponentList) {
        this.responseComponentList = responseComponentList;
    }
}
