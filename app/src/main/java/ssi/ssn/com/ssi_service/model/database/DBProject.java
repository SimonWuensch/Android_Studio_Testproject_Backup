package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;

public class DBProject extends SQLiteOpenHelper {

    private static final String TAG = DBProject.class.getSimpleName();

    private static final String DATABASE_NAME = "service_ssi.db";

    private static final String TABLE_PROJECT = "project";
    public static final String DROP_TABLE_PROJECT = //
            "DROP TABLE IF EXISTS " + TABLE_PROJECT;
    private static final String _ID = "_id";
    private static final String OBSERVATION_INTERVAL = "observationInterval";
    private static final String LAST_OBSERVATION_TIME = "lastObservationTime";
    private static final String IS_OBSERVATION = "isObservation";
    private static final String STATUS = "status";
    private static final String JSON_PROJECT = "jsonProject";
    public static final String CREATE_TABLE_PROJECT = //
            "CREATE TABLE "//
                    + TABLE_PROJECT + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + OBSERVATION_INTERVAL + " NUMERIC, " //
                    + LAST_OBSERVATION_TIME + " DATETIME, " //
                    + IS_OBSERVATION + " NUMERIC, " //
                    + STATUS + " NUMERIC, " //
                    + JSON_PROJECT + " TEXT" +
                    ");";

    private SQLiteDB sqLiteDB;

    public DBProject(int version, Context context, SQLiteDB sqLiteDB) {
        super(context, DATABASE_NAME, null, version);
        this.sqLiteDB = sqLiteDB;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECT);
        db.execSQL(DBCardObjectModule.CREATE_TABLE_CARD_OBJECT_MODULE);
        db.execSQL(DBCardObjectComponent.CREATE_TABLE_CARD_OBJECT_COMPONENT);
        db.execSQL(DBCardObjectNotification.CREATE_TABLE_CARD_OBJECT_NOTIFICATION);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "DB UPGRADE: From version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE_PROJECT);
        db.execSQL(DBCardObjectModule.DROP_TABLE_CARD_OBJECT_MODULE);
        db.execSQL(DBCardObjectComponent.DROP_TABLE_CARD_OBJECT_COMPONENT);
        db.execSQL(DBCardObjectNotification.DROP_TABLE_CARD_OBJECT_NOTIFICATION);
        this.onCreate(db);
    }

    public void add(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBSERVATION_INTERVAL, project.getObservationInterval());
        values.put(IS_OBSERVATION, project.isProjectObservation());
        values.put(JSON_PROJECT, JsonHelper.toJson(project));

        db.insert(TABLE_PROJECT,
                null,
                values);

        Log.d(TAG, "ADD: Project [" + project + "]");
        db.close();
    }

    // ** GET *********************************************************************************** //

    public Project getByID(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_PROJECT + " " +
                "WHERE " + _ID + " = " + id + "", null);

        if (cursor != null)
            cursor.moveToFirst();

        String jsonProject = cursor.getString(cursor.getColumnIndex(JSON_PROJECT));
        Project project = (Project) JsonHelper.fromJsonGeneric(Project.class, jsonProject);
        project.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
        project.setObservationInterval(cursor.getInt(cursor.getColumnIndex(OBSERVATION_INTERVAL)));
        project.setProjectObservation(cursor.getInt(cursor.getColumnIndex(IS_OBSERVATION)) == 1 ? true : false);
        project.setLastObservationTime(cursor.getLong(cursor.getColumnIndex(LAST_OBSERVATION_TIME)));

        Status status = Status.getStatusByID(cursor.getInt(cursor.getColumnIndex(STATUS)));
        project.setStatus(status);

        Log.d(TAG, "GET: Project [" + project + "]");
        return project;
    }

    public List<Project> getALL() {
        List<Project> projects = new LinkedList<>();
        String query = "SELECT  * FROM " + TABLE_PROJECT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String jsonProject = cursor.getString(cursor.getColumnIndex(JSON_PROJECT));
                Project project = (Project) JsonHelper.fromJsonGeneric(Project.class, jsonProject);
                project.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
                project.setObservationInterval(cursor.getInt(cursor.getColumnIndex(OBSERVATION_INTERVAL)));
                project.setProjectObservation(cursor.getInt(cursor.getColumnIndex(IS_OBSERVATION)) == 1 ? true : false);
                project.setLastObservationTime(cursor.getLong(cursor.getColumnIndex(LAST_OBSERVATION_TIME)));

                Status status = Status.getStatusByID(cursor.getInt(cursor.getColumnIndex(STATUS)));
                project.setStatus(status);

                projects.add(project);

            } while (cursor.moveToNext());
        }

        Log.d(TAG, "GET - ALL: Project count [" + projects.size() + "]" + projects);
        return projects;
    }

    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, TABLE_PROJECT);
        //db.close();
        Log.i(TAG, "PROJECT COUNT [" + cnt + "].");
        return cnt;
    }

    // ** Update ******************************************************************************** //
    public boolean update(Project project) {
        ContentValues values = new ContentValues();
        values.put(OBSERVATION_INTERVAL, project.getObservationInterval());
        values.put(IS_OBSERVATION, project.isProjectObservation());
        values.put(LAST_OBSERVATION_TIME, project.getLastObservationTime());
        values.put(STATUS, project.getStatus().getId());
        values.put(JSON_PROJECT, JsonHelper.toJson(project));
        Log.d(TAG, "UPDATE - OVERALL: Project [" + project + "]");
        return updateValue(project, values);
    }

    public boolean updateObservationInterval(Project project) {
        ContentValues values = new ContentValues();
        values.put(OBSERVATION_INTERVAL, project.getObservationInterval());
        Log.d(TAG, "UPDATE - OBSERVATION_INTERVAL: Project [" + project + "]");
        return updateValue(project, values);
    }

    public boolean updateIsObservation(Project project) {
        ContentValues values = new ContentValues();
        values.put(IS_OBSERVATION, project.isProjectObservation());
        Log.d(TAG, "UPDATE - IS_OBSERVATION: Project [" + project + "]");
        return updateValue(project, values);
    }

    public boolean updateLastObservationTime(Project project) {
        ContentValues values = new ContentValues();
        values.put(LAST_OBSERVATION_TIME, project.getLastObservationTime());
        Log.d(TAG, "UPDATE - LAST_OBSERVATION_TIME: Project [" + project + "]");
        return updateValue(project, values);
    }

    public boolean updateStatus(Project project) {
        ContentValues values = new ContentValues();
        values.put(STATUS, project.getStatus().getId());
        Log.d(TAG, "UPDATE - STATUS: Project [" + project + "]");
        return updateValue(project, values);
    }

    public boolean updateJson(Project project) {
        ContentValues values = new ContentValues();
        values.put(JSON_PROJECT, JsonHelper.toJson(project));
        Log.d(TAG, "UPDATE - JSON_PROJECT: Project [" + project + "]");
        return updateValue(project, values);
    }

    private boolean updateValue(Project project, ContentValues values) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int affectedRows = db.update(TABLE_PROJECT, values, _ID + " = ?", new String[]{
                    String.valueOf(project.get_id())
            });

            if (affectedRows > 0) {
                Log.i(TAG, "[OK] UPDATE: Project [" + project + "]");
                return true;
            } else {
                Log.e(TAG, "[ERROR] UPDATE: Project [" + project + "]");
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "[ERROR] UPDATE: Project [" + project + "]. \n" + ex.getMessage() + " \n" + ex.getStackTrace());
            return false;
        }
    }

    // ** DELETE ******************************************************************************** //
    public void delete(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECT,
                _ID + " = ?",
                new String[]{String.valueOf(project.get_id())});
        Log.d(TAG, "DELETE: Project + [" + project + "]");

        project.initCardObjects(sqLiteDB);
        for(AbstractCardObject cardObject : project.getAllCardObjects()){
            cardObject.getDBSQLiteCardObject(sqLiteDB).delete(cardObject);
        }
    }
}