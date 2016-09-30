package ssi.ssn.com.ssi_service.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;
import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.test.TestClass;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private RequestHandler requestHandler;
    private Project currentProject;

    //Todo delete after Test
    private static String restApplication = "{\"state\":{\"since\":1474986112465,\"status\":\"RUNNING\"},\"enabledModules\":[\"Scada\",\"Kpi.UserCharts\",\"Kpi.UserDashboards\"],\"project\":{\"name\":\"AntSimDemo\",\"location\":\"Giebelstadt\",\"orderNr\":\"2x0\"},\"build\":{\"version\":\"2.0.0.0-DEV\",\"number\":\"7755\",\"builtBy\":\"scott.hady\",\"builtOn\":1474880244488},\"time\":{\"stamp\":1475065035240,\"offset\":7200000}}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCreateProjectFragment();
        requestHandler = RequestHandler.initRequestHandler();

        currentProject = new Project("172.26.78.235:8180", "admin", "admin");
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

    // ** FRAGMENT ****************************************************************************** //
    public void showCreateProjectFragment() {
        FragmentCreateProject fragmentCreateProject = FragmentCreateProject.newInstance();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCreateProject,
                        fragmentCreateProject.TAG)
                .addToBackStack(fragmentCreateProject.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentCreateProject.TAG + "].");
    }

    public void showCustomListFragment(FragmentCustomList.Type type, String jsonResponse){
        FragmentCustomList fragmentCustomList = FragmentCustomList.newInstance(type, jsonResponse);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_fragment_container,
                        fragmentCustomList,
                        fragmentCustomList.TAG)
                .addToBackStack(fragmentCustomList.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentCustomList.TAG + "].");
    }


}
