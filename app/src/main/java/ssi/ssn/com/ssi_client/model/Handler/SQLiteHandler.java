package ssi.ssn.com.ssi_client.model.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_client.model.data.ressource.Project;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final String DATABASE_NAME = "ssi_client.db";
    private static final int DATABASE_VERSION = 1;

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

    public SQLiteHandler(Context context) {
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

    private Cursor query(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_PROJECT, null, null, null, null, null, _ID + " DESC");
    }

    public boolean addProject(Project project){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROJECT_JSON, JsonHandler.toJson(project));

            long id = db.insert(TABLE_PROJECT, null, values);
            Log.i(TAG, "[OK] ADDED PROJECT [" + project + "].");
            project.setId(id);
            return true;

        }catch(SQLiteException ex){
            Log.e(TAG, "[ERROR]: PROJECT ADD [" + project + "]:");
            return false;
        }
    }

    /*public Project getProjectByIdentifier(String identifier){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_PROJECT + " " +
                "WHERE " + PROJECT_IDENTIFIER + " = \"" + identifier +"\"", null);

        Project project = null;
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String jsonString = cursor.getString(cursor.getColumnIndex(PROJECT_JSON));
                long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                project = (Project) JsonHandler.fromJsonGeneric(Project.class, jsonString);
                project.setId(id);
                cursor.moveToNext();
            }
        }
        cursor.close();

        if(project == null){
            Log.e(TAG, "ERROR PROJECT LOAD BY IDENTIFIER [" + identifier +  "]");
            throw new NullPointerException("ERROR PROJECT LOAD BY IDENTIFIER [" + identifier + "]");
        }
        Log.i(TAG, "FOUND PROJECT [" + project + "]");
        return project;
    }*/

    /*public long getProjectIdByIdentifier(String identifier){
        return getProjectByIdentifier(identifier).getId();
    }*/

    public Project getProjectByID(long id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_PROJECT + " " +
                "WHERE " + _ID + " = " + id + "", null);

        Project project = null;
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String jsonString = cursor.getString(cursor.getColumnIndex(PROJECT_JSON));
                project = (Project) JsonHandler.fromJsonGeneric(Project.class, jsonString);
                project.setId(id);
                cursor.moveToNext();
                //TODO TEST this method
            }
        }
        cursor.close();

        if(project == null){
            Log.e(TAG, "[ERROR]. PROJECT LOAD BY ID [" + id + "]:");
            throw new NullPointerException("[ERROR] PROJECT LOAD BY ID [" + id + "]:");
        }
        Log.i(TAG, "[OK] FOUND PROJECT [" + project + "]");
        return project;
    }

    public boolean updateProject(Project project){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PROJECT_JSON, JsonHandler.toJson(project));

            int affectedRows = db.update(TABLE_PROJECT, values, _ID + " = ?", new String[]{
                    Long.toString(project.getId())
            });

            if(affectedRows > 0) {
                Log.i(TAG, "[OK] UPDATED PROJECT [" + project + "]");
                return true;
            }else{
                Log.e(TAG, "[ERROR] UPDATE PROJECT - no projects being updated. Project: " + project);
                return false;
            }
        }catch(SQLiteException ex){
            Log.e(TAG, "[ERROR] UPDATE PROJECT [" + project + "]");
            return false;
        }
    }

    public boolean removeProject(Project project){
        try{
            SQLiteDatabase db = getWritableDatabase();
            int affectedRows = db.delete(TABLE_PROJECT, _ID + " = ?", new String[]{
                    Long.toString(project.getId())
            });

            if(affectedRows > 0) {
                Log.i(TAG, "[OK] REMOVED PROJECT [" + project + "]");
                return true;
            }else{
                Log.e(TAG, "[ERROR] REMOVE PROJECT - no projects being removed. Project: " + project);
                return false;
            }
        }catch(SQLiteException ex){
            Log.e(TAG, "[ERROR] REMOVE PROJECT [" + project + "]");
            return false;
        }
    }

    public List<Project> getProjectList(){
        List<Project> projects = new ArrayList<>();
        Cursor cursor = query();

        if(cursor == null){
            return projects;
        }

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String jsonString = cursor.getString(cursor.getColumnIndex(PROJECT_JSON));
                long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                Project project = (Project) JsonHandler.fromJsonGeneric(Project.class, jsonString);
                project.setId(id);
                projects.add(project);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.i(TAG, "FOUND " + projects.size() + "] PROJECTS");
        return projects;
    }

    /*public List<String> getProjectIdentifierList() {
        List<String> identifiers = new ArrayList<String>();
        Cursor cursor = query();

        if(cursor == null){
            return identifiers;
        }

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String identifier = cursor.getString(cursor.getColumnIndex(PROJECT_IDENTIFIER));
                identifiers.add(identifier);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.i(TAG, "FOUND [" + identifiers.size() + "] PROJECT IDENTIFIERS");
        return identifiers;
    }*/
}
