package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;

import ssi.ssn.com.ssi_service.model.data.source.Project;

public class CardObjectNotification extends AbstractCardObject {

    public CardObjectNotification(){}

    public CardObjectNotification(int title, int icon, boolean observation) {
        super(title, icon, observation);
    }

    @Override
    public void detectCardStatus(Activity activity, Project project) {
        setLoadingViewVisible(false);
    }
}
