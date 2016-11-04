package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class CardObjectModule extends AbstractCardObject {

    private static String TAG = CardObjectModule.class.getSimpleName();

    private static String XML_START_TAG_PLATFORM_MODULES = "platform-modules";
    private static String XML_START_TAG_PLUGIN_MODULES = "plugin-modules";
    private static String XML_SEARCHED_TAG_MODULE = "module";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    private Map<String, String> enabledModuleList = new HashMap<>();
    private List<ResponseModule> responseModuleList = new ArrayList<>();
    private List<XMLHelper.XMLObject> moduleObjects;

    public CardObjectModule(int title, int icon, boolean observation) {
        super(title, icon, observation);
    }

    public List<XMLHelper.XMLObject> searchObjectsInResponseXML(String responseApplicationConfig) {
        if (moduleObjects != null) {
            return moduleObjects;
        }

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

    public ExecutorService loadInformationFromApplicationServer2(final Activity activity, final Project project) {
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
                if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
                    return;
                }

                List<XMLHelper.XMLObject> xmlObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
                for (XMLHelper.XMLObject xmlObject : xmlObjects) {
                    String tagName = xmlObject.getTagName();
                    String moduleName = tagName.substring(0, tagName.indexOf("-"));
                    requestHandler.getRequestModuleTask(project, moduleName).executeOnExecutor(executor);

                    if (xmlObject.getAttributes().containsKey(CardObjectModule.XML_ATTRIBUTE_ENABLED)) {
                        String isEnabled = xmlObject.getAttributes().get(CardObjectModule.XML_ATTRIBUTE_ENABLED);
                        enabledModuleList.put(moduleName, isEnabled);
                    }
                }

                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object... objects) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        responseModuleList = new ArrayList<>();
                        for (DefaultResponse defaultResponse : project.getDefaultResponseModuleList()) {
                            if (defaultResponse.getCode() != 200) {
                                continue;
                            }

                            ResponseModule responseModule = (ResponseModule) JsonHelper.fromJsonGeneric(ResponseModule.class, defaultResponse.getResult());
                            responseModule.setEnabled(enabledModuleList.get(responseModule.getName().toLowerCase()));
                            responseModuleList.add(responseModule);
                        }
                        Log.d(TAG, "ResponseModuleList size [" + responseModuleList.size() + "]");

                    }
                }.executeOnExecutor(executor);

                new AsyncTask<Object, Void, Objects>() {
                    @Override
                    protected Objects doInBackground(Object... objects) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Objects objects) {
                        setLoadingViewVisible(false);
                        checkStatus(activity, project);
                    }
                }.executeOnExecutor(executor);
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
                if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }

                boolean allModuleStatusOnline = responseModuleList.isEmpty() ? false : true;
                if(!allModuleStatusOnline){
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }


                for (ResponseModule responseModule : responseModuleList) {
                    String status = responseModule.getStatus();
                    if (status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.RUNNING) &&
                            status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.UNKNOWN)) {
                        allModuleStatusOnline = false;
                    }
                }

                if (!allModuleStatusOnline) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR, activity);
                } else {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.OK, activity);
                }
            }
        }.executeOnExecutor(executor);
    }
}
