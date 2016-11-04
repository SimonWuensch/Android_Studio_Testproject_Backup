package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.test.RESTResponseTEST;

public class CardObjectModule extends AbstractCardObject {

    private static String XML_START_TAG_PLATFORM_MODULES = "platform-modules";
    private static String XML_START_TAG_PLUGIN_MODULES = "plugin-modules";
    private static String XML_SEARCHED_TAG_MODULE = "module";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    public CardObjectModule(int title, int icon, boolean observation) {
        super(title, icon, observation);
    }

    public static List<XMLHelper.XMLObject> searchObjectsInResponseXML(String responseApplicationConfig) {
        List<XMLHelper.XMLObject> moduleObjects;
        XMLHelper xmlHelper = new XMLHelper(XML_START_TAG_PLATFORM_MODULES,
                new ArrayList<String>() {
                    {
                        add(XML_SEARCHED_TAG_MODULE);
                    }
                },
                new ArrayList<String>());
        moduleObjects = xmlHelper.startSearching(responseApplicationConfig);

        xmlHelper = new XMLHelper(XML_START_TAG_PLUGIN_MODULES,
                new ArrayList<String>() {
                    {
                        add(XML_SEARCHED_TAG_MODULE);
                    }
                },
                new ArrayList<String>() {
                    {
                        add(XML_ATTRIBUTE_ENABLED);
                    }
                });
        moduleObjects.addAll(xmlHelper.startSearching(responseApplicationConfig));
        return moduleObjects;
    }

    @Override
    public ExecutorService loadInformationFromApplicationServer(Activity activity, Project project) {
        if (!isOutOfTime(project)) {
            return Executors.newSingleThreadExecutor();
        }
        setLoadingViewVisible(true);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        final RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
        requestHandler.getRequestLoginTask(project).executeOnExecutor(executor);
        requestHandler.getRequestApplicationConfigTask(project).executeOnExecutor(executor);
        new AsyncTask<Object, Void, Objects>() {
            @Override
            protected Objects doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Objects objects) {
                setLoadingViewVisible(false);
            }
        }.executeOnExecutor(executor);
        return executor;
    }

    @Override
    public void onClick(final Activity activity, final Project project) {
        ExecutorService executor = loadInformationFromApplicationServer(activity, project);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                //TODO delete set
                //project.setDefaultResponseApplicationConfig(new DefaultResponse(200, RESTResponseTEST.restApplicationConfig));
                if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
                    Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_module), Toast.LENGTH_SHORT).show();
                } else {
                    ((AbstractActivity) activity).showModuleListFragment(project);
                }
            }
        }.executeOnExecutor(executor);
        executor.shutdown();
    }

    @Override
    public void checkStatus(final Activity activity, final Project project) {
        ExecutorService executor = loadInformationFromApplicationServer(activity, project);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                long responseCode = project.getDefaultResponseApplicationConfig().getCode();
                if (responseCode == 404 || responseCode == 900 || responseCode == 901) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR, activity);
                    return;
                }
                List<XMLHelper.XMLObject> moduleObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
                boolean allModuleEnabled = true;
                boolean allModuleStatusRunning = true;
                for (XMLHelper.XMLObject object : moduleObjects) {
                    if (object.getAttributes().containsKey(XML_ATTRIBUTE_ENABLED)) {
                        if (!Boolean.valueOf(object.getAttributes().get(XML_ATTRIBUTE_ENABLED))) {
                            allModuleEnabled = false;
                        }
                    }
                    //TODO CHECK FOR STATUS
                    allModuleStatusRunning = false;
                }

                /*
                if(!allModuleEnabled){
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                }else
                */

                if (!allModuleStatusRunning) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR, activity);
                } else {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.OK, activity);
                }
            }
        }.executeOnExecutor(executor);
        executor.shutdown();
    }
}
