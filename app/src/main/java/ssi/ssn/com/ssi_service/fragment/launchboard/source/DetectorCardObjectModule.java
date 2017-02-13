package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.request.RequestModule;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;

public class DetectorCardObjectModule {

    private static String TAG = DetectorCardObjectModule.class.getSimpleName();

    private static String XML_START_TAG_PLATFORM_MODULES = "platform-modules";
    private static String XML_START_TAG_PLUGIN_MODULES = "plugin-modules";
    private static String XML_SEARCHED_TAG_MODULE = "module";
    private static String XML_ATTRIBUTE_ENABLED = "enabled";

    public static List<XMLHelper.XMLObject> searchObjectsInResponseXML(String responseApplicationConfig) {
        XMLHelper xmlHelper = new XMLHelper(XML_START_TAG_PLATFORM_MODULES,
                new ArrayList<String>() {
                    {
                        add(XML_SEARCHED_TAG_MODULE);
                    }
                },
                new ArrayList<String>());
        List<XMLHelper.XMLObject> moduleObjects = xmlHelper.startSearching(responseApplicationConfig);

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

    public static void loadFromNetwork(MainActivity activity, Project project, CardObjectModule cardObject) {
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load ard object module information from network...");
        project.getDefaultResponseModuleList().clear();
        final RequestHandler requestHandler = activity.getRequestHandler();
        final Map<String, String> enabledModuleList = new HashMap<>();
        requestHandler.sendRequestLoginWithSessionCurrentCheck(project);
        requestHandler.sendRequestApplicationConfig(project);

        if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
            cardObject.setResponseModuleList(new ArrayList<ResponseModule>());
            return;
        }

        List<XMLHelper.XMLObject> xmlObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
        for (XMLHelper.XMLObject xmlObject : xmlObjects) {
            String tagName = xmlObject.getTagName();
            String xmlModuleName = tagName.substring(0, tagName.indexOf("-"));
            xmlModuleName = xmlModuleName.substring(0, xmlModuleName.length() - (xmlModuleName.endsWith("s") ? 1 : 0));
            if (xmlObject.getAttributes().containsKey(XML_ATTRIBUTE_ENABLED)) {
                String isEnabled = xmlObject.getAttributes().get(XML_ATTRIBUTE_ENABLED);
                enabledModuleList.put(xmlModuleName, isEnabled);
            }
            requestHandler.sendRequestModule(project, xmlModuleName);
        }

        List<ResponseModule> responseModuleList = new ArrayList<>();
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
        cardObject.setResponseModuleList(responseModuleList);
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), "Response module list size is [" + cardObject.getResponseModuleList().size() + "], [" + cardObject.getResponseModuleList() + "]");
    }

    public static void detectCardStatus(MainActivity activity, CardObjectModule cardObject) {
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start detecting card object module status...");
        Status overAllState = Status.OK;
        List<ResponseModule> responseModuleList = cardObject.getResponseModuleList();
        if (responseModuleList.isEmpty()) {
            overAllState = Status.NOT_AVAILABLE;
        } else {
            for (ResponseModule responseModule : responseModuleList) {
                String status = responseModule.getStatus();
                if (!Boolean.valueOf(responseModule.getEnabled())) {
                    continue;
                } else if (!status.equals(Status.TEXT_RUNNING) &&
                        !status.equals(Status.TEXT_UNKNOWN)) {
                    overAllState = Status.ERROR;
                }
            }
        }
        cardObject.setStatus(overAllState);
        boolean isSuccessful = cardObject.getDBSQLiteCardObject(activity).update(cardObject);
        if (!isSuccessful) {
            isSuccessful = cardObject.getDBSQLiteCardObject(activity).update(cardObject);
            if (!isSuccessful) {
                throw new NullPointerException("Can not update " + cardObject.getClass().getSimpleName() + " [" + cardObject + "]");
            }
        }
        Log.i(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " status: " + cardObject.getStatus());
    }
}

