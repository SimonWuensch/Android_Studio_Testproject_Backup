package ssi.ssn.com.ssi_service.fragment.list.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class FragmentNotificationListNotification extends AbstractAndroidNotification {

    @Override
    public void newInstanceWithIntent(Activity activity, Intent intent) {
        String tag = intent.getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (!tag.equals(FragmentNotificationList.TAG)) {
            return;
        }

        ((MainActivity) activity).showProjectListFragment();
        int projectID = intent.getIntExtra(FragmentNotificationList.PROJECT_ID, 0);
        ((MainActivity) activity).showLaunchBoardFragment(projectID);
        ((MainActivity) activity).showNotificationFilterListFragment(projectID);
        int filterID = intent.getIntExtra(FragmentNotificationList.FILTER_ID, -1);
        ((MainActivity) activity).showNotificationListFragment(projectID, filterID);
    }

    @Override
    public Intent createResultIntent(Context context, int... ids) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentNotificationList.TAG);
        resultIntent.putExtra(FragmentNotificationList.PROJECT_ID, ids[0]);
        resultIntent.putExtra(FragmentNotificationList.FILTER_ID, ids[1]);
        return resultIntent;
    }
}
