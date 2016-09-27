package ssi.ssn.com.ssi_service.model.network;

public class Response {

    private long code;
    private String result;

    public Response(long code, String result) {
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
