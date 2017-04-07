package ssi.ssn.com.ssi_service.model.database;

import android.content.Context;

public class SQLiteDB {

    private static int DATABASE_VERSION = 5;

    private DBProject dbProject;
    private DBCardObjectModule dbCardObjectModule;
    private DBCardObjectComponent dbCardObjectComponent;
    private DBCardObjectNotification dbCardObjectNotification;

    public SQLiteDB(Context context) {
        dbProject = new DBProject(DATABASE_VERSION, context, this);
        dbCardObjectModule = new DBCardObjectModule(DATABASE_VERSION, context);
        dbCardObjectComponent = new DBCardObjectComponent(DATABASE_VERSION, context);
        dbCardObjectNotification = new DBCardObjectNotification(DATABASE_VERSION, context);
    }

    public DBProject project() {
        return dbProject;
    }

    public DBCardObjectModule cardObjectModule() {
        return dbCardObjectModule;
    }

    public DBCardObjectComponent cardObjectComponent() {
        return dbCardObjectComponent;
    }

    public DBCardObjectNotification cardObjectNotification(){
        return dbCardObjectNotification;
    }
}
