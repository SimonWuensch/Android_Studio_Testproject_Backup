package ssi.ssn.com.ssi_service.model.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.Project;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "ssi_service.db";
    private static final int DATABASE_VERSION = 11;

    private static final String _ID = "_id";
    private static final String TABLE_PROJECT = "project";
    private static final String PROJECT_JSON = "projectjson";
    private static final String PROJECT_IDENTIFIER = "identifier";

    private static final String CREATE_TABLE_PROJECT = //
            "CREATE TABLE "//
                    + TABLE_PROJECT + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + PROJECT_JSON + " VARCHAR(500), " //
                    + PROJECT_IDENTIFIER + " VARCHAR(100));";

    private static final String DROP_TABLE_PROJECT = //
            "DROP TABLE IF EXISTS " + TABLE_PROJECT;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Database upgrade from version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE_PROJECT);
        onCreate(db);
    }

    private Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_PROJECT, null, null, null, null, null, _ID + " DESC");
    }

    public boolean addProject(Project project) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            String json = JsonHelper.toJson(project);
            values.put(PROJECT_JSON, JsonHelper.toJson(project));

            long id = db.insert(TABLE_PROJECT, null, values);
            Log.i(TAG, "[OK] ADDED PROJECT [" + project + "].");
            project.set_id(id);
            return true;

        } catch (SQLiteException ex) {
            Log.e(TAG, "[ERROR]: PROJECT ADD [" + project + "]:");
            return false;
        }
    }

    public Project getProjectByID(long id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_PROJECT + " " +
                "WHERE " + _ID + " = " + id + "", null);

        Project project = null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String jsonString = cursor.getString(cursor.getColumnIndex(PROJECT_JSON));
                project = (Project) JsonHelper.fromJsonGeneric(Project.class, jsonString);
                project.set_id(id);
                cursor.moveToNext();
                //TODO TEST this method
            }
        }
        cursor.close();

        if (project == null) {
            Log.e(TAG, "[ERROR]. PROJECT LOAD BY ID [" + id + "]:");
            throw new NullPointerException("[ERROR] PROJECT LOAD BY ID [" + id + "]:");
        }
        Log.i(TAG, "[OK] FOUND PROJECT [" + project + "]");
        return project;
    }

    public boolean updateProject(Project project) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROJECT_JSON, JsonHelper.toJson(project));

            int affectedRows = db.update(TABLE_PROJECT, values, _ID + " = ?", new String[]{
                    Long.toString(project.get_id())
            });

            if (affectedRows > 0) {
                Log.i(TAG, "[OK] UPDATED PROJECT [" + project + "]");
                return true;
            } else {
                Log.e(TAG, "[ERROR] UPDATE PROJECT - no projects being updated. Project: " + project);
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "[ERROR] UPDATE PROJECT [" + project + "]");
            return false;
        }
    }

    public boolean deleteProject(Project project) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int affectedRows = db.delete(TABLE_PROJECT, _ID + " = ?", new String[]{
                    Long.toString(project.get_id())
            });

            if (affectedRows > 0) {
                Log.i(TAG, "[OK] REMOVED PROJECT [" + project + "]");
                return true;
            } else {
                Log.e(TAG, "[ERROR] REMOVE PROJECT - no projects being deleted. Project: " + project);
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "[ERROR] REMOVE PROJECT [" + project + "]");
            return false;
        }
    }

    public List<Project> getProjectList() {
        List<Project> projects = new ArrayList<>();
        Cursor cursor = query();

        if (cursor == null) {
            return projects;
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String jsonString = cursor.getString(cursor.getColumnIndex(PROJECT_JSON));
                long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                Project project = (Project) JsonHelper.fromJsonGeneric(Project.class, jsonString);
                project.set_id(id);
                projects.add(project);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.i(TAG, "FOUND [" + projects.size() + "] PROJECTS. [" + projects + "].");
        return projects;
    }

    public long getProjectCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, TABLE_PROJECT);
        db.close();
        Log.i(TAG, "PROJECT COUNT [" + cnt + "].");
        return cnt;
    }
}
