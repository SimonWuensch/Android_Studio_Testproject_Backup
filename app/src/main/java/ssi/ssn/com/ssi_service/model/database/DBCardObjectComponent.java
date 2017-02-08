package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;

public class DBCardObjectComponent extends SQLiteOpenHelper {

    private final String TAG = DBCardObjectComponent.class.getSimpleName();

    private static final String DATABASE_NAME = "service_ssi.db";

    private static final String TABLE_COMPONENT = "cardObjectComponent";
    private static final String _ID = "_id";
    private static final String _ID_PROJECT = "_id_project";
    private static final String LAST_OBSERVATION_TIME = "lastObservationTime";
    private static final String IS_OBSERVATION = "isObservation";
    private static final String STATUS = "status";
    private static final String JSON_COMPONENT = "jsonCardObjectComponent";

    private static final String CREATE_TABLE = //
            "CREATE TABLE "//
                    + TABLE_COMPONENT + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + _ID_PROJECT + " INTEGER, " //
                    + LAST_OBSERVATION_TIME + " DATETIME, " //
                    + IS_OBSERVATION + " NUMERIC, " //
                    + STATUS + " NUMERIC, " //
                    + JSON_COMPONENT + " TEXT" +
                    ");";

    private static final String DROP_TABLE = //
            "DROP TABLE IF EXISTS " + TABLE_COMPONENT;

    public DBCardObjectComponent(int version, Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "DB UPGRADE: From version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE);
        this.onCreate(db);
    }

    public void add(CardObjectComponent cardObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID_PROJECT, cardObject.get_id_project());
        values.put(IS_OBSERVATION, cardObject.isObservation());
        values.put(JSON_COMPONENT, JsonHelper.toJson(cardObject));

        db.insert(TABLE_COMPONENT,
                null,
                values);

        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| ADD: Card Object Component [" + cardObject + "]");
        db.close();
    }

    // ** GET *********************************************************************************** //

    public CardObjectComponent getByID(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_COMPONENT + " " +
                "WHERE " + _ID + " = " + id + "", null);

        if (cursor != null)
            cursor.moveToFirst();

        String json = cursor.getString(cursor.getColumnIndex(JSON_COMPONENT));
        CardObjectComponent cardObject = (CardObjectComponent) JsonHelper.fromJsonGeneric(CardObjectComponent.class, json);
        cardObject.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
        cardObject.set_id_project(cursor.getInt(cursor.getColumnIndex(_ID_PROJECT)));
        cardObject.setObservation(cursor.getInt(cursor.getColumnIndex(IS_OBSERVATION)) == 1 ? true : false);
        cardObject.setLastObservationTime(cursor.getLong(cursor.getColumnIndex(LAST_OBSERVATION_TIME)));

        Status status = Status.getStatusByID(cursor.getInt(cursor.getColumnIndex(STATUS)));
        cardObject.setStatus(status);

        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| GET: Card Object Component [" + cardObject + "]");
        return cardObject;
    }

    // ** Update ******************************************************************************** //
    public boolean update(CardObjectComponent cardObject) {
        ContentValues values = new ContentValues();
        values.put(_ID_PROJECT, cardObject.get_id_project());
        values.put(IS_OBSERVATION, cardObject.isObservation());
        values.put(LAST_OBSERVATION_TIME, cardObject.getLastObservationTime());
        values.put(STATUS, cardObject.getStatus().getId());
        values.put(JSON_COMPONENT, JsonHelper.toJson(cardObject));
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - OVERALL: Card Object Component [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    public boolean updateIsObservation(CardObjectComponent cardObject) {
        ContentValues values = new ContentValues();
        values.put(IS_OBSERVATION, cardObject.isObservation());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - IS_OBSERVATION: Card Object Component [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    public boolean updateLastObservationTime(CardObjectComponent cardObject) {
        ContentValues values = new ContentValues();
        values.put(LAST_OBSERVATION_TIME, cardObject.getLastObservationTime());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - LAST_OBSERVATION_TIME: Card Object Component [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    public boolean updateStatus(CardObjectComponent cardObject) {
        ContentValues values = new ContentValues();
        values.put(STATUS, cardObject.getStatus().getId());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - STATUS: Card Object Component [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    public boolean updateJson(CardObjectComponent cardObject) {
        ContentValues values = new ContentValues();
        values.put(JSON_COMPONENT, JsonHelper.toJson(cardObject));
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - JSON_COMPONENT: Card Object Component [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    private boolean updateValue(CardObjectComponent cardObject, ContentValues values) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int affectedRows = db.update(TABLE_COMPONENT, values, _ID + " = ?", new String[]{
                    String.valueOf(cardObject.get_id())
            });

            if (affectedRows > 0) {
                Log.i(TAG, "ID Project: " + cardObject.get_id_project() + "| [OK] UPDATE: Card Object Component [" + cardObject + "]");
                return true;
            } else {
                Log.e(TAG, "ID Project: " + cardObject.get_id_project() + "| [ERROR] UPDATE: Card Object Component [" + cardObject + "]");
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "ID Project: " + cardObject.get_id_project() + "| [ERROR] UPDATE: Card Object Component [" + cardObject + "]. \n" + ex.getMessage() + " \n" + ex.getStackTrace());
            return false;
        }
    }

    // ** DELETE ******************************************************************************** //
    public void delete(CardObjectComponent cardObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPONENT,
                _ID + " = ?",
                new String[]{String.valueOf(cardObject.get_id())});
        db.close();
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| DELETE: Card Object Component + [" + cardObject + "]");
    }
}
