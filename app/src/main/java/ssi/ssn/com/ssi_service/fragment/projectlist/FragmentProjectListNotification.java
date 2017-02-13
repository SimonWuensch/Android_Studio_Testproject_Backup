package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.notification.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.model.notification.AndroidNotificationHelper;


public class FragmentProjectListNotification extends AbstractAndroidNotification {

    @Override
    public void newInstanceWithIntent(Activity activity, Intent intent) {
        String tag = intent.getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (!tag.equals(FragmentProjectList.TAG)) {
            return;
        }
        ((MainActivity) activity).showProjectListFragment();
    }

    @Override
    public Intent createResultIntent(Activity activity, long projectID) {
        Intent resultIntent = new Intent(activity, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentProjectList.TAG);

        return resultIntent;
    }
}