package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.notification.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.notification.AndroidNotificationHelper;

public class FragmentLaunchBoardNotification extends AbstractAndroidNotification {

    // ** Android Notification ****************************************************************** //
    @Override
    public void newInstanceWithIntent(Activity activity, Intent intent) {
        String tag = intent.getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (!tag.equals(FragmentLaunchBoard.TAG)) {
            return;
        }

        ((MainActivity) activity).showProjectListFragment();
        long projectID = intent.getLongExtra(FragmentLaunchBoard.PROJECT_ID, 0);
        ((MainActivity) activity).showLaunchBoardFragment(projectID);
    }

    @Override
    public Intent createResultIntent(Context context, long projectID) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentLaunchBoard.TAG);
        resultIntent.putExtra(FragmentLaunchBoard.PROJECT_ID, projectID);
        return resultIntent;
    }
}
