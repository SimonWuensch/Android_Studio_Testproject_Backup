package ssi.ssn.com.ssi_service.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.create.notificationfilter.FragmentCreateNotificationFilter;
import ssi.ssn.com.ssi_service.fragment.list.component.FragmentComponentList;
import ssi.ssn.com.ssi_service.fragment.create.project.FragmentCreateProject;
import ssi.ssn.com.ssi_service.fragment.list.custom.FragmentCustomList;
import ssi.ssn.com.ssi_service.fragment.list.notificationfilter.FragmentNotificationFilterList;
import ssi.ssn.com.ssi_service.fragment.overview.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.fragment.list.module.FragmentModuleList;
import ssi.ssn.com.ssi_service.fragment.list.notification.FragmentNotificationList;
import ssi.ssn.com.ssi_service.fragment.list.project.FragmentProjectList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;
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
        if(sqliteDB == null){
            return sqliteDB = new SQLiteDB(this);
        }
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
        showCreateProjectFragment(0);
    }

    public void showCreateProjectFragment(int projectID) {
        FragmentCreateProject fragmentCreateProject = FragmentCreateProject.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateProject,
                        FragmentCreateProject.TAG)
                .addToBackStack(FragmentCreateProject.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCreateProject.TAG + "]. + Project ID: " + projectID);
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
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentLaunchBoard.TAG + "]. Project ID: " + projectID);
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
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentModuleList.TAG + "]. + Project ID: " + projectID);
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
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentComponentList.TAG + "]. + Project ID: " + projectID);
    }

    public void showNotificationListFragment(int projectID) {
        FragmentNotificationList fragmentNotificationList = FragmentNotificationList.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentNotificationList,
                        FragmentNotificationList.TAG)
                .addToBackStack(FragmentNotificationList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentNotificationList.TAG + "]. + Project ID: " + projectID);
    }

    public void showNotificationListFragment(int projectID, FilterNotification filter) {
        FragmentNotificationList fragmentNotificationList = FragmentNotificationList.newInstance(projectID, filter.getId());
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentNotificationList,
                        FragmentNotificationList.TAG)
                .addToBackStack(FragmentNotificationList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentNotificationList.TAG + "]. + Project ID: " + projectID + " Filter: " + filter.identity());
    }

    public void showCreateNotificationFilterFragment(int projectID){
        FragmentCreateNotificationFilter fragmentCreateNotificationFilter = FragmentCreateNotificationFilter.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateNotificationFilter,
                        FragmentCreateNotificationFilter.TAG)
                .addToBackStack(FragmentCreateNotificationFilter.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCreateNotificationFilter.TAG + "]. + Project ID: " + projectID + "].");
    }

    public void showCreateNotificationFilterFragment(int projectID, ResponseNotification notification){
        FragmentCreateNotificationFilter fragmentCreateNotificationFilter = FragmentCreateNotificationFilter.newInstance(projectID, notification);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateNotificationFilter,
                        FragmentCreateNotificationFilter.TAG)
                .addToBackStack(FragmentCreateNotificationFilter.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCreateNotificationFilter.TAG + "]. + Project ID: " + projectID);
    }

    public void showCreateNotificationFilterFragment(int projectID, FilterNotification filter){
        FragmentCreateNotificationFilter fragmentCreateNotificationFilter = FragmentCreateNotificationFilter.newInstance(projectID, filter);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateNotificationFilter,
                        FragmentCreateNotificationFilter.TAG)
                .addToBackStack(FragmentCreateNotificationFilter.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentCreateNotificationFilter.TAG + "]. + Project ID: " + projectID + ", Filter: " + filter.identity());
    }

    public void showNotificationFilterListFragment(int projectID){
        FragmentNotificationFilterList fragmentNotificationFilterList = FragmentNotificationFilterList.newInstance(projectID);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentNotificationFilterList,
                        FragmentNotificationFilterList.TAG)
                .addToBackStack(FragmentNotificationFilterList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + FragmentNotificationFilterList.TAG + "]. + Project ID: " + projectID);
    }
}
