package ssi.ssn.com.ssi_client.activity;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ssi.ssn.com.ssi_client.R;
import ssi.ssn.com.ssi_client.fragment.createproject.FragmentCreateProject;
import ssi.ssn.com.ssi_client.model.data.ressource.Project;
import ssi.ssn.com.ssi_client.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_client.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_client.model.network.request.NotificationRequest;
import ssi.ssn.com.ssi_client.model.network.request.ScadaRequest;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private RequestHandler requestHandler;

    private Project currentProject = new Project("172.26.78.235:8180", "admin", "admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCreateProjectFragment();
        requestHandler = RequestHandler.initRequestHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestHandler.doLoginRequest(currentProject);
    }

    @Override
    protected void onStop() {
        super.onStop();
        requestHandler.doLogoutRequest(currentProject);
    }

    public RequestHandler getRequestHandler(){
        return requestHandler;
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
