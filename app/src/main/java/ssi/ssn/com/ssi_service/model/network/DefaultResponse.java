package ssi.ssn.com.ssi_service.model.network;

public class DefaultResponse {

    private long code;
    private String result;

    public DefaultResponse(long code, String result) {
        this.code = code;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public long getCode() {
        return code;
    }
}
