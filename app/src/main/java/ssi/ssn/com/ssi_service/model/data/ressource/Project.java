package ssi.ssn.com.ssi_service.model.data.ressource;

public class Project extends AbstractProject{

    private long _id;
    private String serverAddress;
    private String userName;
    private String password;

    public Project(String serverAddress, String userName, String password) {
        this.serverAddress = serverAddress;
        this.userName = userName;
        this.password = password;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String toString(){
        return new StringBuilder()
                .append("[")
                .append("ServerAddress: ").append(serverAddress).append(";")
                .append("UserName: ").append(userName).append(";")
                .append("Password: ").append(password).append(";")
                .append("]").toString();
    }
}
