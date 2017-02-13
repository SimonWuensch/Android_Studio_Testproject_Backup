package ssi.ssn.com.ssi_service.test;

import java.util.HashMap;
import java.util.Map;

public class TestRestResponse {

    private String TAG = TestRestResponse.class.getSimpleName();

    private String version;
    private Map<String, String> responseList = new HashMap<>();

    public TestRestResponse(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getResponseByAddress(String address) {
        String url = "/services" + address.split("/services")[1];
        if (!responseList.containsKey(url)) {
            throw new NullPointerException("[Version: " + version + "] No response found for address [" + address + "]");
        }
        return responseList.get(url);
    }

    public void addResponse(String address, String response) {
        responseList.put(address, response);
    }

}
