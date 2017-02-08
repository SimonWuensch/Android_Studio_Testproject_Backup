package ssi.ssn.com.ssi_service.model.database;

import android.content.Context;

public class SQLiteDB {

    private static int DATABASE_VERSION = 1;

    private DBProject dbProject;
    private DBCardObjectModule dbCardObjectModule;
    private DBCardObjectComponent dbCardObjectComponent;

    public SQLiteDB(Context context) {
        dbProject = new DBProject(DATABASE_VERSION, context);
        dbCardObjectModule = new DBCardObjectModule(DATABASE_VERSION, context);
        dbCardObjectComponent = new DBCardObjectComponent(DATABASE_VERSION, context);
    }

    public DBProject project(){
        return dbProject;
    }

    public DBCardObjectModule cardObjectModule(){
        return dbCardObjectModule;
    }

    public DBCardObjectComponent cardObjectComponent(){
        return dbCardObjectComponent;
    }
}
