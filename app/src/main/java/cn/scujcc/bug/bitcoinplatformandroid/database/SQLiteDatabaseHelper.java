package cn.scujcc.bug.bitcoinplatformandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lilujia on 16/5/3.
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {


    private static final String TEXT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_AT_ENTRIES =
            "CREATE TABLE " + DatabaseEntry.ATOrdersEntry.TABLE_NAME + " (" +
                    DatabaseEntry.ATOrdersEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseEntry.ATOrdersEntry.COLUMN_NAME_ORDER_ID + TEXT_TYPE + COMMA_SEP +
                    DatabaseEntry.ATOrdersEntry.COLUMN_NAME_ORDER_TIME + TEXT_TYPE + " )";


    private static final String SQL_DELETE_AT_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseEntry.ATOrdersEntry.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bit.db";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AT_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_AT_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
