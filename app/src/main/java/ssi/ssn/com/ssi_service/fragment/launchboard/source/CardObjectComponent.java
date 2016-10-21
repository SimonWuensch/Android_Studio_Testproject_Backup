package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;

import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;

public class CardObjectComponent extends AbstractCardObject {

    public CardObjectComponent(int title, int icon, boolean observation) {
        super(title, icon, observation);
    }

    @Override
    public void onClick(Activity activity, Project project) {
        ((AbstractActivity)activity).showComponentListFragment(project);
    }
}
