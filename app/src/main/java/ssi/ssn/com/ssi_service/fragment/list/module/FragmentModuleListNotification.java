package ssi.ssn.com.ssi_service.fragment.list.module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class FragmentModuleListNotification extends AbstractAndroidNotification {

    @Override
    public void newInstanceWithIntent(Activity activity, Intent intent) {
        String tag = intent.getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (!tag.equals(FragmentModuleList.TAG)) {
            return;
        }

        ((MainActivity) activity).showProjectListFragment();
        long projectID = intent.getLongExtra(FragmentModuleList.PROJECT_ID, 0);
        ((MainActivity) activity).showLaunchBoardFragment(projectID);
        ((MainActivity) activity).showModuleListFragment(projectID);
    }

    @Override
    public Intent createResultIntent(Context context, int... ids) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentModuleList.TAG);
        resultIntent.putExtra(FragmentModuleList.PROJECT_ID, ids[0]);
        return resultIntent;
    }
}
