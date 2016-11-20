package com.project.snave.sunshinees.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Snave on 09/11/2016.
 */
public class MeiHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SunshineES.db";

    /** should think about new class **/
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MeiContract.FeedEntry.TABLE_NAME + " (" +
                    MeiContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    MeiContract.FeedEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_WIND + TEXT_TYPE + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_PRESSURE + TEXT_TYPE + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_TEMPERATURE + TEXT_TYPE + COMMA_SEP +

                    " UNIQUE (" + MeiContract.FeedEntry.COLUMN_NAME_CITY + COMMA_SEP +
                    MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + ") )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MeiContract.FeedEntry.TABLE_NAME;

    public MeiHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
