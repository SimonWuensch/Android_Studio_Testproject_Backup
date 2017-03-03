package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.list.module.FragmentModuleList;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class FragmentNotificationListNotification extends AbstractAndroidNotification {

    @Override
    public void newInstanceWithIntent(Activity activity, Intent intent) {
        String tag = intent.getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (!tag.equals(FragmentModuleList.TAG)) {
            return;
        }

        ((MainActivity) activity).showProjectListFragment();
        int projectID = intent.getIntExtra(FragmentNotificationFilterList.PROJECT_ID, 0);
        ((MainActivity) activity).showLaunchBoardFragment(projectID);
        ((MainActivity) activity).showNotificationFilterListFragment(projectID);
    }

    @Override
    public Intent createResultIntent(Context context, long projectID) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentNotificationFilterList.TAG);
        resultIntent.putExtra(FragmentNotificationFilterList.PROJECT_ID, projectID);
        return resultIntent;
    }
}
