package ssi.ssn.com.ssi_service.activity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.test.TestClass;

public class MainActivity extends AbstractActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(sqLiteHelper.getProjectCount() == 0){
            showCreateProjectFragment();
        }else{
            showProjectListFragment();
        }
        loadingView = (View) findViewById(R.id.activity_main_view_loading);
        loadingView.setVisibility(View.GONE);

        //TODO TEST IT
        TestClass.xmlParser();
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




}
