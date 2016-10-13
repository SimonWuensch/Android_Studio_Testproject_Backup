package ssi.ssn.com.ssi_service.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;
import ssi.ssn.com.ssi_service.fragment.launchboard.FragmentLaunchBoard;
import ssi.ssn.com.ssi_service.fragment.projectlist.FragmentProjectList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.handler.SQLiteHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private SQLiteHelper sqLiteHelper;
    private RequestHandler requestHandler;
    private Project currentProject;

    private View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestHandler = RequestHandler.initRequestHandler();
        sqLiteHelper = new SQLiteHelper(this);

        //currentProject = new Project("172.26.78.235:8180", "admin", "admin");

        if(sqLiteHelper.getProjectCount() == 0){
            showCreateProjectFragment();
        }else{
            showProjectListFragment();
        }
        loadingView = (View) findViewById(R.id.activity_main_view_loading);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentProject != null) {
            requestHandler.getRequestLoginTask(currentProject).execute();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(currentProject != null) {
            requestHandler.getRequestLogoutTask(currentProject).execute();
        }
    }

    public SQLiteHelper getSQLiteHelper(){
        return sqLiteHelper;
    }

    public RequestHandler getRequestHandler(){
        return requestHandler;
    }

    public Project getCurrentProject(){
        return currentProject;
    }

    public void setCurrentProject(Project project){
        if(currentProject != null){
            requestHandler.getRequestLogoutTask(project);
        }

        this.currentProject = project;
        requestHandler.getRequestLoginTask(currentProject).execute();
    }

    public void setLoadingViewVisible(boolean isVisible){
        loadingView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    // ** FRAGMENT ****************************************************************************** //
    public void showCreateProjectFragment(){
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
        Log.i(TAG, "Show Fragment [" + fragmentCreateProject.TAG + "].");
    }

    public void showCustomListFragment(int headlineStringID, FragmentCustomList.Type type, String jsonResponse){
        FragmentCustomList fragmentCustomList = FragmentCustomList.newInstance(headlineStringID, type, jsonResponse);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCustomList,
                        fragmentCustomList.TAG)
                .addToBackStack(fragmentCustomList.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentCustomList.TAG + "].");
    }

    public void showProjectListFragment(){
        FragmentProjectList fragmentProjectList = FragmentProjectList.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentProjectList,
                        fragmentProjectList.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentProjectList.TAG + "].");
    }

    public void showLaunchBoardFragment(Project project){
        FragmentLaunchBoard fragmentLaunchBoard = FragmentLaunchBoard.newInstance(project);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentLaunchBoard,
                        fragmentLaunchBoard.TAG)
                .addToBackStack(fragmentLaunchBoard.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentLaunchBoard.TAG + "].");
    }


}
