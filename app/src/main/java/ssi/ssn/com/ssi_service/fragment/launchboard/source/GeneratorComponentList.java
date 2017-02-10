package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.AbstractActivity;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class GeneratorComponentList extends AbstractGenerator {

    /*private static String TAG = GeneratorComponentList.class.getSimpleName();

    private static String XML_START_TAG_COMPONENTS_MODULE = "components-module";
    private static String XML_SEARCHED_TAG_SERVER = "server";
    public static String XML_ATTRIBUTE_MANAGE = "manage";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    private List<ResponseComponent> responseComponentList = new ArrayList<>();
    private List<XMLHelper.XMLObject> componentObjects;

    public GeneratorComponentList(MainActivity activity, Project project, int title, int icon, boolean observation) {
        super(activity, project, title, icon, observation);
    }

    public List<XMLHelper.XMLObject> searchObjectsInResponseXML(String responseApplicationConfig) {
        if (componentObjects != null) {
            return componentObjects;
        }

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
        componentObjects = xmlHelper.startSearching(responseApplicationConfig);
        for (int i = 0; i < componentObjects.size(); i++) {
            if (!componentObjects.get(i).getAttributes().containsKey(XML_ATTRIBUTE_MANAGE)) {
                componentObjects.remove(i);
            }
        }
        return componentObjects;
    }

    @Override
    public void loadFromNetwork() {
        if (!isOutOfTime() && project.getDefaultResponseApplicationConfig() != null) {
            return;
        }
        setLoadingViewVisible(true);
        project.getDefaultResponseComponentList().clear();
        final List<String> notEnabledComponents = new ArrayList<>();
        final RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
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
                    String componentName = tagName.substring(0, tagName.indexOf("-"));
                    requestHandler.sendRequestComponent(project, componentName);

                    if (xmlObject.getAttributes().containsKey(GeneratorComponentList.XML_ATTRIBUTE_ENABLED)) {
                        String isEnabled = xmlObject.getAttributes().get(GeneratorComponentList.XML_ATTRIBUTE_ENABLED);
                        if (!Boolean.valueOf(isEnabled)) {
                            notEnabledComponents.add(componentName);
                        }
                    }
                }

                responseComponentList = new ArrayList<>();
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
                Log.d(TAG, "ResponseComponentList size [" + responseComponentList.size() + "]");
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
        ExecutorService executor = ((MainActivity) activity).getExecutor();
        final AbstractGenerator cardObject = this;
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (cardObject.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE)) {
                    Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_component), Toast.LENGTH_SHORT).show();
                } else {
                    activity.showComponentListFragment(project);
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
                if (responseComponentList.isEmpty()) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }

                ssi.ssn.com.ssi_service.model.data.source.Status overAllState = ssi.ssn.com.ssi_service.model.data.source.Status.OK;
                for (ResponseComponent responseComponent : responseComponentList) {
                    String componentStatus = responseComponent.getState().getStatus();
                    if (!componentStatus.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_ONLINE) &&
                            !componentStatus.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_UNKNOWN)) {
                        overAllState = ssi.ssn.com.ssi_service.model.data.source.Status.ERROR;
                    }
                }
                setStatus(overAllState, activity);
            }
        }.executeOnExecutor(executor);
    }*/
}
