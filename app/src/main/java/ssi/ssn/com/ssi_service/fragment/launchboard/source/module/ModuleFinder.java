package ssi.ssn.com.ssi_service.fragment.launchboard.source.module;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.request.RequestModule;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class ModuleFinder {

    private static String TAG = ModuleFinder.class.getSimpleName();

    private static String XML_START_TAG_PLATFORM_MODULES = "platform-modules";
    private static String XML_START_TAG_PLUGIN_MODULES = "plugin-modules";
    private static String XML_SEARCHED_TAG_MODULE = "module";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    private NewCardObjectModule cardObject;

    public static ModuleFinder init(){
        return new ModuleFinder();
    }

    private List<XMLHelper.XMLObject> moduleObjects;

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

    public void loadFromNetwork(final Activity activity, final Project project) {
        /*if (!project.isOutOfDate()) {
            return ;
        }

        final List<ResponseModule> responseList;
        final RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
        final ExecutorService executor = requestHandler.getExecutor();
        requestHandler.sendRequestLoginWithSessionCurrentCheck(project);
        requestHandler.sendRequestApplicationConfig(project).executeOnExecutor(executor);

        final Map<String, String> enabledModuleList = new HashMap<>();
        new AsyncTask<Object, Void, Objects>() {
            @Override
            protected Objects doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Objects objects) {
                if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
                    cardObject.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }

                List<XMLHelper.XMLObject> xmlObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
                for (XMLHelper.XMLObject xmlObject : xmlObjects) {
                    String tagName = xmlObject.getTagName();
                    String xmlModuleName = tagName.substring(0, tagName.indexOf("-"));
                    if (xmlModuleName.endsWith("s")) {
                        xmlModuleName = xmlModuleName.substring(0, xmlModuleName.length() - 1);
                    }
                    if (xmlObject.getAttributes().containsKey(CardObjectModule.XML_ATTRIBUTE_ENABLED)) {
                        String isEnabled = xmlObject.getAttributes().get(CardObjectModule.XML_ATTRIBUTE_ENABLED);
                        enabledModuleList.put(xmlModuleName, isEnabled);
                    }
                    requestHandler.sendRequestModule(project, xmlModuleName).executeOnExecutor(executor);
                }

                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object... objects) {
                        cardObject.setResponseList(new ArrayList<ResponseModule>());
                        for (DefaultResponse defaultResponse : project.getDefaultResponseModuleList()) {
                            ResponseModule responseModule;
                            String xmlModuleName = defaultResponse.getAdditional().get(RequestModule.ADDITIONAL_MODULE_NAME);
                            if (defaultResponse.getCode() != 200) {
                                responseModule = new ResponseModule();
                                responseModule.setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.UNKNOWN);
                                responseModule.setName(xmlModuleName);
                            } else {
                                responseModule = (ResponseModule) JsonHelper.fromJsonGeneric(ResponseModule.class, defaultResponse.getResult());
                            }

                            responseModule.setEnabled(enabledModuleList.containsKey(xmlModuleName) ? enabledModuleList.get(xmlModuleName) : "true");
                            cardObject.getResponseList().add(responseModule);
                        }
                        Log.d(getClass().getSimpleName(), "ResponseModuleList size [" + cardObject.getResponseList().size() + "]");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        cardObject.detectCardStatus(activity, project);
                    }
                }.executeOnExecutor(executor);
            }
        }.executeOnExecutor(executor);*/
    }
}