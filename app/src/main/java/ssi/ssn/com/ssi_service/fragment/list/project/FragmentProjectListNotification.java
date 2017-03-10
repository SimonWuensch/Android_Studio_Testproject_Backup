package ssi.ssn.com.ssi_service.fragment.list.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;


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
    public Intent createResultIntent(Context context, int... ids) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra(AndroidNotificationHelper.TAG_FRAGMENT, FragmentProjectList.TAG);

        return resultIntent;
    }
}