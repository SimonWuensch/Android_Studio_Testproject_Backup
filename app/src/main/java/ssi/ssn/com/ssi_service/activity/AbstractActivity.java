package ssi.ssn.com.ssi_service.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentList;
import ssi.ssn.com.ssi_service.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleList;
import ssi.ssn.com.ssi_service.fragment.notificatonlist.FragmentNotificationList;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class AbstractActivity extends Activity {

    public static String TAG = AbstractActivity.class.getSimpleName();

    public static String ACCEPTED_PROJECT_VERSION = "2.";
    protected SQLiteDB sqliteDB;
    protected RequestHandler requestHandler;
    protected AndroidNotificationHelper androidNotificationHelper;
    protected View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqliteDB = new SQLiteDB(this);
        requestHandler = RequestHandler.initRequestHandler(Executors.newSingleThreadExecutor());
        androidNotificationHelper = new AndroidNotificationHelper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                List<Project> projects = sqliteDB.project().getALL();
                Log.d(TAG, "Start sending request logout for [" + projects.size() + "]");
                for (Project project : projects)
                    getRequestHandler().sendRequestLogout(project);
                return null;
            }
        }.execute();
    }

    public SQLiteDB getSQLiteDB() {
        return sqliteDB;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public AndroidNotificationHelper getAndroidNotificationHelper() {
        return androidNotificationHelper;
    }

    public void setLoadingViewVisible(boolean isVisible) {
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    // ** FRAGMENT ****************************************************************************** //
    public void showCreateProjectFragment() {
        showCreateProjectFragment(null);
    }

    public void showCreateProjectFragment(Project project) {
        FragmentCreateProject fragmentCreateProject = FragmentCreateProject.newInstance(project);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateProject,
                        FragmentCreateProject.TAG)
                .addToBackStack(FragmentCreateProject.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCreateProject.TAG + "].");
    }

    public void showCustomListFragment(int headlineStringID, FragmentCustomList.Type type, String jsonResponse) {
        FragmentCustomList fragmentCustomList = FragmentCustomList.newInstance(headlineStringID, type, jsonResponse);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCustomList,
                        FragmentCustomList.TAG)
                .addToBackStack(FragmentCustomList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCustomList.TAG + "].");
    }

    public void showProjectListFragment() {
        FragmentProjectList fragmentProjectList = FragmentProjectList.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentProjectList,
                        FragmentProjectList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentProjectList.TAG + "].");
    }

    public void showLaunchBoardFragment(long projectID) {
        FragmentLaunchBoard fragmentLaunchBoard = FragmentLaunchBoard.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentLaunchBoard,
                        FragmentLaunchBoard.TAG)
                .addToBackStack(FragmentLaunchBoard.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentLaunchBoard.TAG + "].");
    }

    public void showModuleListFragment(long projectID) {
        FragmentModuleList fragmentModuleList = FragmentModuleList.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentModuleList,
                        FragmentModuleList.TAG)
                .addToBackStack(FragmentModuleList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentModuleList.TAG + "].");
    }

    public void showComponentListFragment(long projectID) {
        FragmentComponentList fragmentComponentList = FragmentComponentList.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentComponentList,
                        FragmentComponentList.TAG)
                .addToBackStack(FragmentComponentList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentComponentList.TAG + "].");
    }

    public void showNotificationListFragment(long projectID) {
        FragmentNotificationList fragmentNotificationList = FragmentNotificationList.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentNotificationList,
                        FragmentNotificationList.TAG)
                .addToBackStack(FragmentNotificationList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentNotificationList.TAG + "].");
    }
}
