package ssi.ssn.com.ssi_service.fragment.launchboard.source;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
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
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

public class CardObjectComponent extends AbstractCardObject {

    private static String TAG = CardObjectComponent.class.getSimpleName();

    private static String XML_START_TAG_COMPONENTS_MODULE = "components-module";
    private static String XML_SEARCHED_TAG_SERVER = "server";
    public static String XML_ATTRIBUTE_MANAGE = "manage";
    public static String XML_ATTRIBUTE_ENABLED = "enabled";

    private List<ResponseComponent> responseComponentList = new ArrayList<>();
    private List<XMLHelper.XMLObject> componentObjects;

    public CardObjectComponent(int title, int icon, boolean observation) {
        super(title, icon, observation);
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
    public ExecutorService loadInformationFromApplicationServer(final Activity activity, final Project project) {
        if (!isOutOfTime(project)) {
            return Executors.newSingleThreadExecutor();
        }

        setLoadingViewVisible(true);
        final List<String> notEnabledComponents = new ArrayList<>();
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
                    String componentName = tagName.substring(0, tagName.indexOf("-"));
                    requestHandler.getRequestComponentTask(project, componentName).executeOnExecutor(executor);

                    if (xmlObject.getAttributes().containsKey(CardObjectComponent.XML_ATTRIBUTE_ENABLED)) {
                        String isEnabled = xmlObject.getAttributes().get(CardObjectComponent.XML_ATTRIBUTE_ENABLED);
                        if (!Boolean.valueOf(isEnabled)) {
                            notEnabledComponents.add(componentName);
                        }
                    }
                }

                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object... objects) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
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
                if (getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE)) {
                    Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_component), Toast.LENGTH_SHORT).show();
                } else {
                    ((AbstractActivity) activity).showComponentListFragment(project, responseComponentList);
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

                boolean AllComponentStatusOnline = responseComponentList.isEmpty() ? false : true;
                if(!AllComponentStatusOnline){
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE, activity);
                    return;
                }

                for (ResponseComponent responseComponent : responseComponentList) {
                    String status = responseComponent.getState().getStatus();
                    if (!status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.ONLINE) &&
                            !status.equals(ssi.ssn.com.ssi_service.model.data.source.Status.UNKNOWN)) {
                        AllComponentStatusOnline = false;

                    }
                }

                if (!AllComponentStatusOnline) {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR, activity);
                } else {
                    setStatus(ssi.ssn.com.ssi_service.model.data.source.Status.OK, activity);
                }
            }
        }.executeOnExecutor(executor);
    }
}
