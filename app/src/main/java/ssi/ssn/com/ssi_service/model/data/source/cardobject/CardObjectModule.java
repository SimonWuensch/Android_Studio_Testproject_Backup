package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class CardObjectModule extends AbstractCardObject {

    private List<ResponseModule> responseModuleList = new ArrayList<>();

    public CardObjectModule(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_module;
        this.icon = R.drawable.icon_modul;
    }

    public CardObjectModule() {
    }

    public List<ResponseModule> getResponseModuleList() {
        return responseModuleList;
    }

    public void setResponseModuleList(List<ResponseModule> responseModuleList) {
        this.responseModuleList = responseModuleList;
    }

    public static AbstractCardObject generate(Activity activity) {
        return new CardObjectModule();
    }
}
