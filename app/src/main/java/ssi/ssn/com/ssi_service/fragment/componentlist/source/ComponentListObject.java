package ssi.ssn.com.ssi_service.fragment.componentlist.source;

public class ComponentListObject {

    private String name;
    private String managed;
    private String status;

    public ComponentListObject() {
    }

    public ComponentListObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaged() {
        return managed;
    }

    public void setManaged(String managed) {
        this.managed = managed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
