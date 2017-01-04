package ssi.ssn.com.ssi_service.activity;

import android.os.Bundle;

import ssi.ssn.com.ssi_service.R;

public class MainActivity extends AbstractActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (sqLiteHelper.getProjectCount() == 0) {
            showCreateProjectFragment();
        } else {
            showProjectListFragment();
        }
        loadingView = findViewById(R.id.activity_main_view_loading);
        setLoadingViewVisible(false);
    }
}
