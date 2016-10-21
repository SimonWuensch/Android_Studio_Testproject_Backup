package ssi.ssn.com.ssi_service.fragment.modulelist.source;

public class ModuleListObject {

    private String name;
    private String enabled;
    private String Status;

    public ModuleListObject() {
    }

    public ModuleListObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
