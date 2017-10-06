package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBApplicationStatus extends SQLiteOpenHelper {

    private static final String TAG = DBApplicationStatus.class.getSimpleName();

    private static final String DATABASE_NAME = "service_ssi.db";
    private static final String TABLE_APPLICATION_STATUS = "application_status";
    public static final String DROP_TABLE_APPLICATION_STATUS = //
            "DROP TABLE IF EXISTS " + TABLE_APPLICATION_STATUS;
    private static final String _ID = "_id";
    private static final String STATUS = "status";
    public static final String CREATE_TABLE_APPLICATION_STATUS = //
            "CREATE TABLE "//
                    + TABLE_APPLICATION_STATUS + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                    + STATUS + " NUMERIC" +
                    ");";

    public DBApplicationStatus(int version, Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_APPLICATION_STATUS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "DB UPGRADE: From version " + oldVersion + " to " + newVersion + ".");
        db.execSQL(DROP_TABLE_APPLICATION_STATUS);
        this.onCreate(db);
    }

    public boolean add(ApplicationStatus status) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(STATUS, status.getId());

            long inserID = db.insert(TABLE_APPLICATION_STATUS,
                    null,
                    values);

            Log.d(TAG, "[" + inserID + "] ADD: Application Status [" + status + "]");
            return true;
        } catch (Throwable t) {
            return false;
        } finally {
            db.close();
        }
    }

    public boolean isApplicationInForeground() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM " + TABLE_APPLICATION_STATUS + " " +
                "WHERE " + _ID + " = " + 1 + "", null);
        if (cursor != null)
            cursor.moveToFirst();
        int statusID = cursor.getInt(cursor.getColumnIndex(STATUS));
        Log.d(TAG, "GET - Application Status: [" + statusID + "]");
        db.close();
        return statusID == 2;
    }

    public boolean updateApplicationStatus(ApplicationStatus status) {
        if (getCount() == 0) {
            return add(status);
        }

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(STATUS, status.getId());
            int affectedRows = db.update(TABLE_APPLICATION_STATUS, values, _ID + " = ?", new String[]{
                    String.valueOf(1)
            });
            if (affectedRows > 0) {
                Log.i(TAG, "[OK] UPDATE: Application status to [" + status + "]");
                return true;
            } else {
                Log.e(TAG, "[ERROR] Application status to [" + status + "]");
                return false;
            }
        } catch (SQLiteException ex) {
            Log.e(TAG, "[ERROR] UPDATE: Application status to [" + status + "]" + ex.getMessage() + " \n" + ex.getStackTrace());
            return false;
        } finally {
            db.close();
        }
    }

    public long getCount() {
        SQLiteDatabase db = getReadableDatabase();
        long value = DatabaseUtils.queryNumEntries(db, TABLE_APPLICATION_STATUS,
                _ID + "=?", new String[]{String.valueOf(1)});
        db.close();
        return value;
    }

    // ** ENUM ********************************************************************************** //
    public enum ApplicationStatus {
        BACKGROUND(1),
        FOREGROUND(2);

        private int id;

        private ApplicationStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}