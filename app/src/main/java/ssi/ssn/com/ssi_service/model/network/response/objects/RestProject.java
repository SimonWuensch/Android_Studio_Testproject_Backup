package ssi.ssn.com.ssi_service.model.network.response.objects;

public class RestProject {

    private String name;
    private String location;
    private String orderNr;

    public RestProject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }
}
