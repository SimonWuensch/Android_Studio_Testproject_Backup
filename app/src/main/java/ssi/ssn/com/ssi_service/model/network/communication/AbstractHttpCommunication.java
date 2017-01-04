package ssi.ssn.com.ssi_service.model.network.communication;

import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.CookieHandler;
import ssi.ssn.com.ssi_service.test.TestRestResponse;
import ssi.ssn.com.ssi_service.test.VersionRest_2_0_9_0;

public class AbstractHttpCommunication {

    protected static String PROTOCOLL = "http://";
    protected static int READ_TIME_OUT_INTERVAL = 10000;
    protected static int GET_COMMUNICATION_ERROR = 900;
    protected static int GET_COMMUNICATION_ERROR_TIMEOUT = 901;

    protected CookieHandler cookieHandler;
    protected String address;

    private boolean isTestVersion = true;
    private TestRestResponse testRestResponse = new VersionRest_2_0_9_0();

    public AbstractHttpCommunication(CookieHandler cookieHandler, String address) {
        this.cookieHandler = cookieHandler;
        this.address = address;
    }

    public AbstractHttpCommunication(String address){
        this.address = address;
    }

    public DefaultResponse sendRequest(boolean resetCookie) {
        if(isTestVersion){
            String testResponse = testRestResponse.getResponseByAddress(address);
            return new DefaultResponse(200, testResponse);
        }
        return send(resetCookie);
    }

    public int exists() {
        if(isTestVersion){
            return 200;
        }
        return send();
    }

    protected DefaultResponse send(boolean resetCookie){
        throw new NullPointerException("No request settings found...");
    }

    protected int send(){
        throw new NullPointerException("No request settings found...");
    }

}
