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


        /*XMLHelper xmlHelper;
        List<XMLHelper.XMLObject> xmlObjectList;
        //TODO TEST IT
        Log.d(TAG, "components-module");
        xmlHelper = new XMLHelper("components-module",
                new ArrayList<String>() {
                    {
                        add("server");
                    }
                },
                new ArrayList<String>() {
                    {
                        add("manage");
                        add("enabled");
                    }
                });
        xmlObjectList = xmlHelper.startSearching(RESTResponseTEST.restApplicationConfig);
        for (XMLHelper.XMLObject object : xmlObjectList) {
            for (String attributeName : object.getAttributes().keySet())
                Log.d(TAG, object.getTagName() + ": " + attributeName + " - " + object.getAttributes().get(attributeName));
        }*/

        /*
        Log.d(TAG, "platform-modules");
        xmlHelper = new XMLHelper("platform-modules",
                new ArrayList<String>() {
                    {
                        add("module");
                    }
                },
                new ArrayList<String>());
        xmlObjectList = xmlHelper.startSearching(RESTResponseTEST.restApplicationConfig);
        for (XMLHelper.XMLObject object : xmlObjectList) {
                Log.d(TAG, object.getTagName());
        }

        Log.d(TAG, "plugin-modules");
        xmlHelper = new XMLHelper("plugin-modules",
                new ArrayList<String>() {
                    {
                        add("module");
                    }
                },
                new ArrayList<String>(){
                    {
                        add("enabled");
                    }
                });
        xmlObjectList = xmlHelper.startSearching(RESTResponseTEST.restApplicationConfig);
        for (XMLHelper.XMLObject object : xmlObjectList) {
            for (String attributeName : object.getAttributes().keySet())
                Log.d(TAG, object.getTagName() + ": " + attributeName + " - " + object.getAttributes().get(attributeName));
        }
        */
    }
}
