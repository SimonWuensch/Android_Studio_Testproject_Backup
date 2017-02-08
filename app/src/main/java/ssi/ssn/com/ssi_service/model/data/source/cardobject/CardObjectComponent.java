package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class CardObjectComponent extends AbstractCardObject{

    private List<ResponseComponent> responseComponentList = new ArrayList<>();

    public CardObjectComponent(Project project, int title, int icon) {
        super(project, title, icon);
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
