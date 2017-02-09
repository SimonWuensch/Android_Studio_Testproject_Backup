package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;

public class DBCardObjectModule extends SQLiteOpenHelper implements DBCardObject{

    private final String TAG = DBCardObjectModule.class.getSimpleName();

    private static final String DATABASE_NAME = "service_ssi.db";

    private static final String TABLE_MODULE = "cardObjectModule";
    private static final String _ID = "_id";
    private static final String _ID_PROJECT = "_id_project";
    private static final String LAST_OBSERVATION_TIME = "lastObservationTime";
    private static final String IS_OBSERVATION = "isObservation";
    private static final String STATUS = "status";
    private static final String JSON_MODULE = "jsonCardObjectModule";

    public static final String CREATE_TABLE_CARD_OBJECT_MODULE = //
            "CREATE TABLE "//
                    + TABLE_MODULE + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + _ID_PROJECT + " INTEGER, " //
                    + LAST_OBSERVATION_TIME + " DATETIME, " //
                    + IS_OBSERVATION + " NUMERIC, " //
                    + STATUS + " NUMERIC, " //
                    + JSON_MODULE + " TEXT" +
                    ");";

    public static final String DROP_TABLE_CARD_OBJECT_MODULE = //
            "DROP TABLE IF EXISTS " + TABLE_MODULE;

    public DBCardObjectModule(int version, Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CARD_OBJECT_MODULE);
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "DB UPGRADE: From version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE_CARD_OBJECT_MODULE);
        this.onCreate(db);
    }

    @Override
    public void add(AbstractCardObject cardObject) {
        if(getCount(cardObject.get_id_project()) != 0){
            try {
                throw new Exception("Only one module per project may exist");
            }catch (Throwable t){
                t.printStackTrace();
                return;
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ID_PROJECT, cardObject.get_id_project());
        values.put(IS_OBSERVATION, cardObject.isObservation());
        values.put(JSON_MODULE, JsonHelper.toJson(cardObject));

        db.insert(TABLE_MODULE,
                null,
                values);

        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| ADD: Card Object Module [" + cardObject + "]");
        db.close();
    }

    @Override
    public long getCount(long projectID) {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_MODULE,
                _ID_PROJECT + "=?", new String[]{String.valueOf(projectID)});
        db.close();

        return count;
    }

    // ** GET *********************************************************************************** //

    @Override
    public CardObjectModule getByProjectID(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_MODULE + " " +
                "WHERE " + _ID + " = " + id + "", null);

        if (cursor != null)
            cursor.moveToFirst();

        String json = cursor.getString(cursor.getColumnIndex(JSON_MODULE));
        CardObjectModule cardObject = (CardObjectModule) JsonHelper.fromJsonGeneric(CardObjectModule.class, json);
        cardObject.set_id(cursor.getInt(cursor.getColumnIndex(_ID)));
        cardObject.set_id_project(cursor.getInt(cursor.getColumnIndex(_ID_PROJECT)));
        cardObject.setObservation(cursor.getInt(cursor.getColumnIndex(IS_OBSERVATION)) == 1 ? true : false);
        cardObject.setLastObservationTime(cursor.getLong(cursor.getColumnIndex(LAST_OBSERVATION_TIME)));

        Status status = Status.getStatusByID(cursor.getInt(cursor.getColumnIndex(STATUS)));
        cardObject.setStatus(status);

        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| GET: Card Object Module [" + cardObject + "]");
        return cardObject;
    }

    // ** Update ******************************************************************************** //
    @Override
    public boolean update(AbstractCardObject cardObject) {
        ContentValues values = new ContentValues();
        values.put(_ID_PROJECT, cardObject.get_id_project());
        values.put(IS_OBSERVATION, cardObject.isObservation());
        values.put(LAST_OBSERVATION_TIME, cardObject.getLastObservationTime());
        values.put(STATUS, cardObject.getStatus().getId());
        values.put(JSON_MODULE, JsonHelper.toJson(cardObject));
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - OVERALL: Card Object Module [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    @Override
    public boolean updateIsObservation(AbstractCardObject cardObject) {
        ContentValues values = new ContentValues();
        values.put(IS_OBSERVATION, cardObject.isObservation());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - IS_OBSERVATION: Card Object Module [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    @Override
    public boolean updateLastObservationTime(AbstractCardObject cardObject) {
        ContentValues values = new ContentValues();
        values.put(LAST_OBSERVATION_TIME, cardObject.getLastObservationTime());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - LAST_OBSERVATION_TIME: Card Object Module [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    @Override
    public boolean updateStatus(AbstractCardObject cardObject) {
        ContentValues values = new ContentValues();
        values.put(STATUS, cardObject.getStatus().getId());
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - STATUS: Card Object Module [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    @Override
    public boolean updateJson(AbstractCardObject cardObject) {
        ContentValues values = new ContentValues();
        values.put(JSON_MODULE, JsonHelper.toJson(cardObject));
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| UPDATE - JSON_MODULE: Card Object Module [" + cardObject + "]");
        return updateValue(cardObject, values);
    }

    @Override
    public boolean updateValue(AbstractCardObject cardObject, ContentValues values) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int affectedRows = db.update(TABLE_MODULE, values, _ID + " = ?", new String[]{
                    String.valueOf(cardObject.get_id())
            });

            if (affectedRows > 0) {
                Log.i(TAG, "ID Project: " + cardObject.get_id_project() + "| [OK] UPDATE: Card Object Module [" + cardObject + "]");
                return true;
            } else {
                Log.e(TAG, "ID Project: " + cardObject.get_id_project() + "| [ERROR] UPDATE: Card Object Module [" + cardObject + "]");
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "ID Project: " + cardObject.get_id_project() + "| [ERROR] UPDATE: Card Object Module [" + cardObject + "]. \n" + ex.getMessage() + " \n" + ex.getStackTrace());
            return false;
        }
    }

    // ** DELETE ******************************************************************************** //
    @Override
    public void delete(AbstractCardObject cardObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MODULE,
                _ID + " = ?",
                new String[]{String.valueOf(cardObject.get_id())});
        db.close();
        Log.d(TAG, "ID Project: " + cardObject.get_id_project() + "| DELETE: Card Object Module + [" + cardObject + "]");
    }
}
