package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class DetectorCardObjectComponent {

    public static String XML_ATTRIBUTE_MANAGE = "manage";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";
    private static String TAG = DetectorCardObjectComponent.class.getSimpleName();
    private static String XML_START_TAG_COMPONENTS_MODULE = "components-module";
    private static String XML_SEARCHED_TAG_SERVER = "server";

    public static List<XMLHelper.XMLObject> searchObjectsInResponseXML(String responseApplicationConfig) {
        XMLHelper xmlHelper = new XMLHelper(XML_START_TAG_COMPONENTS_MODULE,
                new ArrayList<String>() {
                    {
                        add(XML_SEARCHED_TAG_SERVER);
                    }
                },
                new ArrayList<String>() {
                    {
                        add(XML_ATTRIBUTE_MANAGE);
                        add(XML_ATTRIBUTE_ENABLED);
                    }
                });
        List<XMLHelper.XMLObject> componentObjects = xmlHelper.startSearching(responseApplicationConfig);
        for (int i = 0; i < componentObjects.size(); i++) {
            if (!componentObjects.get(i).getAttributes().containsKey(XML_ATTRIBUTE_MANAGE)) {
                componentObjects.remove(i);
            }
        }
        return componentObjects;
    }

    public static void loadFromNetwork(MainActivity activity, Project project, CardObjectComponent cardObject) {
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load card object component information from network...");
        project.getDefaultResponseComponentList().clear();
        final List<String> notEnabledComponents = new ArrayList<>();
        final RequestHandler requestHandler = activity.getRequestHandler();
        requestHandler.sendRequestLoginWithSessionCurrentCheck(project);
        requestHandler.sendRequestApplicationConfig(project);

        if (project.getDefaultResponseApplicationConfig().getCode() != 200) {
            cardObject.setResponseComponentList(new ArrayList<ResponseComponent>());
            return;
        }

        List<XMLHelper.XMLObject> xmlObjects = searchObjectsInResponseXML(project.getDefaultResponseApplicationConfig().getResult());
        for (XMLHelper.XMLObject xmlObject : xmlObjects) {
            String tagName = xmlObject.getTagName();
            String componentName = tagName.substring(0, tagName.indexOf("-"));
            requestHandler.sendRequestComponent(project, componentName);

            if (xmlObject.getAttributes().containsKey(XML_ATTRIBUTE_ENABLED)) {
                String isEnabled = xmlObject.getAttributes().get(XML_ATTRIBUTE_ENABLED);
                if (!Boolean.valueOf(isEnabled)) {
                    notEnabledComponents.add(componentName);
                }
            }
        }

        List<ResponseComponent> responseComponentList = new ArrayList<>();
        for (DefaultResponse defaultResponse : project.getDefaultResponseComponentList()) {
            if (defaultResponse.getCode() != 200) {
                continue;
            }

            ResponseComponent responseComponent = (ResponseComponent) JsonHelper.fromJsonGeneric(ResponseComponent.class, defaultResponse.getResult());
            if (notEnabledComponents.contains(responseComponent.getName())) {
                responseComponent.getState().setEnabled(false);
            }
            responseComponentList.add(responseComponent);
        }
        cardObject.setResponseComponentList(responseComponentList);
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), "Response component list size is [" + cardObject.getResponseComponentList().size() + "], [" + cardObject.getResponseComponentList() + "]");
    }

    public static void detectCardStatus(MainActivity activity, CardObjectComponent cardObject) {
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start detecting card object component status...");
        Status overAllState = Status.OK;
        if (cardObject.getResponseComponentList().isEmpty()) {
            overAllState = Status.NOT_AVAILABLE;
        } else {
            for (ResponseComponent responseComponent : cardObject.getResponseComponentList()) {
                String componentStatus = responseComponent.getState().getStatus();
                if (!componentStatus.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_ONLINE) &&
                        !componentStatus.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_UNKNOWN)) {
                    overAllState = ssi.ssn.com.ssi_service.model.data.source.Status.ERROR;
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
