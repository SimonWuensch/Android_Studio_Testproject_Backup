package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.request.RequestModule;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class GeneratorModuleList extends AbstractGenerator {
    private static String TAG = GeneratorModuleList.class.getSimpleName();

    private static String XML_START_TAG_PLATFORM_MODULES = "platform-modules";
    private static String XML_START_TAG_PLUGIN_MODULES = "plugin-modules";
    private static String XML_SEARCHED_TAG_MODULE = "module";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    private List<ResponseModule> responseModuleList = new ArrayList<>();
    private List<XMLHelper.XMLObject> moduleObjects;

    public GeneratorModuleList(MainActivity activity, Project project, int title, int icon, boolean observation) {
        super(activity, project, title, icon, observation);
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
    public void loadFromNetwork() {
        if (!isOutOfTime() && project.getDefaultResponseApplicationConfig() != null) {
            return;
        }
        setLoadingViewVisible(true);
        project.getDefaultResponseModuleList().clear();
        final RequestHandler requestHandler = activity.getRequestHandler();
        final Map<String, String> enabledModuleList = new HashMap<>();
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                requestHandler.sendRequestLoginWithSessionCurrentCheck(project);
                requestHandler.sendRequestApplicationConfig(project);

                if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
                    return null;
                }

                List<XMLHelper.XMLObject> xmlObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
                for (XMLHelper.XMLObject xmlObject : xmlObjects) {
                    String tagName = xmlObject.getTagName();
                    String xmlModuleName = tagName.substring(0, tagName.indexOf("-"));
                    if (xmlModuleName.endsWith("s")) {
                        xmlModuleName = xmlModuleName.substring(0, xmlModuleName.length() - 1);
                    }
                    if (xmlObject.getAttributes().containsKey(GeneratorModuleList.XML_ATTRIBUTE_ENABLED)) {
                        String isEnabled = xmlObject.getAttributes().get(GeneratorModuleList.XML_ATTRIBUTE_ENABLED);
                        enabledModuleList.put(xmlModuleName, isEnabled);
                    }
                    requestHandler.sendRequestModule(project, xmlModuleName);
                }

                responseModuleList = new ArrayList<>();
                for (DefaultResponse defaultResponse : project.getDefaultResponseModuleList()) {
                    ResponseModule responseModule;
                    String xmlModuleName = defaultResponse.getAdditional().get(RequestModule.ADDITIONAL_MODULE_NAME);
                    if (defaultResponse.getCode() != 200) {
                        responseModule = new ResponseModule();
                        responseModule.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_UNKNOWN);
                        responseModule.setName(xmlModuleName);
                    } else {
                        responseModule = (ResponseModule) JsonHelper.fromJsonGeneric(ResponseModule.class, defaultResponse.getResult());
                    }

                    responseModule.setEnabled(enabledModuleList.containsKey(xmlModuleName) ? enabledModuleList.get(xmlModuleName) : "true");
                    responseModuleList.add(responseModule);
                }
                Log.d(TAG, "ResponseModuleList size [" + responseModuleList.size() + "]");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                setLoadingViewVisible(false);
                detectCardStatus();
            }
        }.execute();
    }

    @Override
    public void onClick() {
        loadFromNetwork();
        ExecutorService executor = activity.getExecutor();
        final AbstractGenerator cardObject = this;
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (cardObject.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE)) {
                    Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_module), Toast.LENGTH_SHORT).show();
                } else {
                    activity.showModuleListFragment(project);
                }
            }
        }.executeOnExecutor(executor);
    }

    @Override
    public void detectCardStatus() {
        loadFromNetwork();
        ExecutorService executor = activity.getExecutor();
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (responseModuleList.isEmpty()) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }

                ssi.ssn.com.ssi_service.model.data.source.Status overAllState = ssi.ssn.com.ssi_service.model.data.source.Status.OK;
                for (ResponseModule responseModule : responseModuleList) {
                    String status = responseModule.getStatus();
                    if (!Boolean.valueOf(responseModule.getEnabled())) {
                        continue;
                    } else if (!status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_RUNNING) &&
                            !status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_UNKNOWN)) {
                        overAllState = ssi.ssn.com.ssi_service.model.data.source.Status.ERROR;
                    }
                }
                setStatus(overAllState, activity);
            }
        }.executeOnExecutor(executor);
    }
}