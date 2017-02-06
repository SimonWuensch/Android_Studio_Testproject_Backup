package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SQLiteHelper;

public class SQLiteDB extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "service_ssi.db";

    private static final String TABLE_PROJECT = "project";
    private static final String _ID = "_id";
    private static final String OBSERVATION_INTERVAL = "observationInterval";
    private static final String LAST_OBSERVATION_TIME = "lastObservationTime";
    private static final String IS_OBSERVATION = "isObservation";
    private static final String STATUS  = "status";
    private static final String JSON_PROJECT = "jsonProject";

    private static final String CREATE_TABLE_PROJECT = //
            "CREATE TABLE "//
                    + TABLE_PROJECT + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + OBSERVATION_INTERVAL + " NUMERIC, " //
                    + LAST_OBSERVATION_TIME + " NUMERIC, " //
                    + IS_OBSERVATION + " NUMERIC, " //
                    + STATUS + " TEXT, " //
                    + JSON_PROJECT + " TEXT" +
                    ");";

    private static final String DROP_TABLE_PROJECT = //
            "DROP TABLE IF EXISTS " + TABLE_PROJECT;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECT);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Database upgrade from version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE_PROJECT);
        this.onCreate(db);
    }

    private Cursor query() {
        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_PROJECT, null, null, null, null, null, _ID + " DESC");
    }


    public void addProject(Project project){
        Log.d(TAG, "ADD PROJECT" + project.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBSERVATION_INTERVAL, project.getObservationInterval());
        values.put(IS_OBSERVATION, project.isProjectObservation());
        values.put(JSON_PROJECT, JsonHelper.toJson(project));

        db.insert(TABLE_PROJECT,
                null,
                values);

        db.close();
    }
}

//http://hmkcode.com/android-simple-sqlite-database-tutorial/


