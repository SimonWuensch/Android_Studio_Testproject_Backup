package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class CardObjectModule extends AbstractCardObject{

    private List<ResponseModule> responseModuleList = new ArrayList<>();

    public CardObjectModule(Project project, int title, int icon) {
        super(project, title, icon);
    }

    public CardObjectModule() {
    }

    public List<ResponseModule> getResponseModuleList() {
        return responseModuleList;
    }

    public void setResponseModuleList(List<ResponseModule> responseModuleList) {
        this.responseModuleList = responseModuleList;
    }
}
