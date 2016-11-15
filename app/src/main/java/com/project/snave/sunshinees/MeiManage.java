package com.project.snave.sunshinees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Snave on 09/11/2016.
 */
public class MeiManage {
    private MeiHelper mDbHelper;
    private SQLiteDatabase db;

    public MeiManage(Context context) {
        mDbHelper = new MeiHelper(context);
        db = mDbHelper.getReadableDatabase();
    }

    public Cursor getAllCities(){

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                MeiContract.FeedEntry.COLUMN_NAME_CITY + " DESC";

        Cursor c = db.rawQuery("select * from table",null);
        return c;
    }

    public Cursor getCity(String city, String country){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MeiContract.FeedEntry._ID,
                MeiContract.FeedEntry.COLUMN_NAME_CITY,
                MeiContract.FeedEntry.COLUMN_NAME_COUNTRY,
                MeiContract.FeedEntry.COLUMN_NAME_WIND_SPEED,
                MeiContract.FeedEntry.COLUMN_NAME_WIND_DIRECTION,
                MeiContract.FeedEntry.COLUMN_NAME_PRESSURE,
                MeiContract.FeedEntry.COLUMN_NAME_TEMPERATURE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = MeiContract.FeedEntry.COLUMN_NAME_CITY + " = ?";
        String[] selectionArgs = { city };

        Cursor c = db.query(
                MeiContract.FeedEntry.TABLE_NAME,             // The table to query
                projection,                                   // The columns to return
                selection,                                    // The columns for the WHERE clause
                selectionArgs,                                // The values for the WHERE clause
                null,                                         // don't group the rows
                null,                                         // don't filter by row groups
                null                                          // The sort order
        );
        return c;
    }
}
