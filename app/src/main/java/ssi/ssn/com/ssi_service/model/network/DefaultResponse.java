package ssi.ssn.com.ssi_service.model.network;

public class DefaultResponse {

    private long code;
    private String result;

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
}
