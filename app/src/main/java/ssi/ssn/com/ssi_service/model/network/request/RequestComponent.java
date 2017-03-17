package ssi.ssn.com.ssi_service.model.network.request;

import java.util.HashMap;
import java.util.Map;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.communication.HttpGET;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;

public class RequestComponent extends AbstractRequest {

    private static String TAG = RequestOs.class.getSimpleName();

    private Map<String, VersionValue> versionSettings = new HashMap<String, VersionValue>() {
        {
            put("opcua", new VersionValue("2.1", "OpcUa"));
        }
    };

    private static String ADDRESS = "/services/component/{name}";

    private Project project;
    private String name;

    private RequestComponent(Project project, String name) {
        this.project = project;
        this.name = name;

        if (!versionSettings.containsKey(name)) {
            return;
        }

        VersionValue versionValue = versionSettings.get(name);
        if (project.getProjectVersion().startsWith(versionValue.getVersion())) {
            this.name = versionValue.getValue();
        }
    }

    public static RequestComponent init(Project project, String name) {
        return new RequestComponent(project, name);
    }

    private String getAddress() {
        return project.getServerAddress() + ADDRESS
                .replace("{name}", name);
    }

    public void getTaskGET(final CookieHandler cookieHandler) {
        HttpGET httpGET = new HttpGET(cookieHandler, getAddress());
        DefaultResponse defaultResponse = httpGET.sendRequest(false);
        project.addDefaultResponseComponent(defaultResponse);
    }
}
