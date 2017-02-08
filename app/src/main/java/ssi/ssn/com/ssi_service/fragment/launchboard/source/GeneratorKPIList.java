package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;

public class GeneratorKPIList extends AbstractGenerator {

    public GeneratorKPIList(){}

    public GeneratorKPIList(MainActivity activity, Project project, int title, int icon, boolean observation) {
        super(activity, project, title, icon, observation);
    }

    @Override
    public void detectCardStatus() {
        setLoadingViewVisible(false);
    }
}
