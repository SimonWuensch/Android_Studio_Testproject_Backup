package ssi.ssn.com.ssi_service.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private RequestHandler requestHandler;

    private Project currentProject;

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
                //.addToBackStack(FragmentListProject.TAG)
                .commit();
        Log.i(TAG, "Show Fragment [" + fragmentCreateProject.TAG + "].");
    }


}
