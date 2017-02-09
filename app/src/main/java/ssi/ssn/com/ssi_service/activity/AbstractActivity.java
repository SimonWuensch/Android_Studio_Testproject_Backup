package ssi.ssn.com.ssi_service.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.componentlist.FragmentComponentList;
import ssi.ssn.com.ssi_service.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractGenerator;
import ssi.ssn.com.ssi_service.fragment.modulelist.FragmentModuleList;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class AbstractActivity extends Activity {

    public static String ACCEPTED_PROJECT_VERSION = "2.";

    private ExecutorService executor;

    protected SQLiteDB sqliteDB;
    protected RequestHandler requestHandler;
    protected View loadingView;

    private Map<Long, List<AbstractGenerator>> projectCardObjectMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executor = Executors.newSingleThreadExecutor();
        requestHandler = RequestHandler.initRequestHandler(executor);
        sqliteDB = new SQLiteDB(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (final long projectID : projectCardObjectMap.keySet()) {
            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object... objects) {
                    Log.e(getClass().getSimpleName(), "PROJECT ID: " + projectID);
                    Project project = sqliteDB.project().getByID(projectID);
                    getRequestHandler().sendRequestLogout(project);
                    projectCardObjectMap.remove(projectID);
                    return null;
                }
            }.execute();
        }
    }

    public ExecutorService getExecutor(){
        return executor;
    }

    public SQLiteDB getSQLiteDB() {
        return sqliteDB;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void setLoadingViewVisible(boolean isVisible) {
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public List<AbstractGenerator> getCardObjects(Project project){
        if(projectCardObjectMap.containsKey(project.get_id())){
            return projectCardObjectMap.get(project.get_id());
        }
        return new LinkedList<>();
    }

    public void addCardToMap(Project project, AbstractGenerator cardObject){
        if(!projectCardObjectMap.containsKey(project.get_id())){
            projectCardObjectMap.put(project.get_id(), new LinkedList<AbstractGenerator>());
        }
        List<AbstractGenerator> cardObjects = projectCardObjectMap.get(project.get_id());
        cardObjects.add(cardObject);
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
                        fragmentCreateProject.TAG)
                .addToBackStack(fragmentCreateProject.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentCreateProject.TAG + "].");
    }

    public void showCustomListFragment(int headlineStringID, FragmentCustomList.Type type, String jsonResponse) {
        FragmentCustomList fragmentCustomList = FragmentCustomList.newInstance(headlineStringID, type, jsonResponse);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCustomList,
                        fragmentCustomList.TAG)
                .addToBackStack(fragmentCustomList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentCustomList.TAG + "].");
    }

    public void reloadProjectListFragment(FragmentProjectList fragmentProjectList) {
        getFragmentManager().beginTransaction().remove(fragmentProjectList).commit();
        showProjectListFragment();
    }

    public void showProjectListFragment() {
        FragmentProjectList fragmentProjectList = FragmentProjectList.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentProjectList,
                        fragmentProjectList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentProjectList.TAG + "].");
    }

    public void reloadLaunchBoardFragment(FragmentLaunchBoard fragmentLaunchBoard, Project project) {
        getFragmentManager().beginTransaction().remove(fragmentLaunchBoard).commit();
        showLaunchBoardFragment(project);
    }

    public void showLaunchBoardFragment(final Project project) {
        requestHandler.sendRequestLoginWithSessionCurrentCheck(project);
        final ExecutorService executor = requestHandler.getExecutor();
        new AsyncTask<Object, Void, Object>(){
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                FragmentLaunchBoard fragmentLaunchBoard = FragmentLaunchBoard.newInstance(project);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_fragment_container,
                                fragmentLaunchBoard,
                                fragmentLaunchBoard.TAG)
                        .addToBackStack(fragmentLaunchBoard.TAG)
                        .commit();
                Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentLaunchBoard.TAG + "].");
            }
        }.executeOnExecutor(executor);
    }

    public void showModuleListFragment(Project project) {
        FragmentModuleList fragmentModuleList = FragmentModuleList.newInstance(project);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentModuleList,
                        fragmentModuleList.TAG)
                .addToBackStack(fragmentModuleList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentModuleList.TAG + "].");
    }

    public void showComponentListFragment(Project project, List<ResponseComponent> responseComponentList) {
        FragmentComponentList fragmentComponentList = FragmentComponentList.newInstance(project, responseComponentList);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentComponentList,
                        fragmentComponentList.TAG)
                .addToBackStack(fragmentComponentList.TAG)
                .commit();
        Log.i(getClass().getSimpleName(), "Show Fragment [" + fragmentComponentList.TAG + "].");
    }
}
