package ssi.ssn.com.ssi_service.fragment.launchboard.source.module;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractCardObject;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class NewCardObjectModule extends AbstractCardObject {

    private static String TAG = CardObjectModule.class.getSimpleName();

    private List<ResponseModule> responseList = new ArrayList<>();
    private ModuleFinder moduleFinder;

    public NewCardObjectModule() {
        moduleFinder = ModuleFinder.init();
    }

    public NewCardObjectModule(int title, int icon, boolean observation) {
        super(title, icon, observation);
        moduleFinder = ModuleFinder.init();
    }

    public List<ResponseModule> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<ResponseModule> responseList) {
        this.responseList = responseList;
    }

    public void loadInfosFromNetwork(Activity activity, Project project) {
        moduleFinder.loadFromNetwork(activity, project);
    }

    public void loadInfosFromSource(Activity activity, Project project) {
        detectCardStatus(activity, project);
    }

    @Override
    public void detectCardStatus(Activity activity, Project project) {
        if (responseList.isEmpty()) {
            setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
            return;
        }

        ssi.ssn.com.ssi_service.model.data.source.Status overAllStatus = ssi.ssn.com.ssi_service.model.data.source.Status.OK;
        for (ResponseModule responseModule : responseList) {
            String status = responseModule.getStatus();
            if (!Boolean.valueOf(responseModule.getEnabled())) {
                continue;
            } else if (!status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_RUNNING) &&
                    !status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_UNKNOWN)) {
                overAllStatus = ssi.ssn.com.ssi_service.model.data.source.Status.ERROR;
            }
        }
        setStatus(overAllStatus, activity);
    }

    @Override
    public void onClick(final Activity activity, final Project project) {
        if (getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE)) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_module), Toast.LENGTH_SHORT).show();
        } else {
            ((AbstractActivity) activity).showModuleListFragment(project, responseList);
        }
    }

}
