package ssi.ssn.com.ssi_service.model.network;

import java.util.HashMap;
import java.util.Map;

public class DefaultResponse {

    private long code;
    private String result;
    private Map<String, String> additional;

    public DefaultResponse() {
    }

    public DefaultResponse(long code, String result) {
        this.code = code;
        this.result = result;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void addAdditional(String key, String value) {
        if (additional == null) {
            additional = new HashMap<>();
        }
        additional.put(key, value);
    }

    public Map<String, String> getAdditional() {
        return additional == null ? new HashMap<String, String>() : additional;
    }

    public void setAdditional(Map<String, String> additional) {
        this.additional = additional;
    }
}
