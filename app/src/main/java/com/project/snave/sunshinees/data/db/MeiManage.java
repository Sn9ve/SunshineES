package com.project.snave.sunshinees.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.snave.sunshinees.data.City;

import java.util.ArrayList;
import java.util.ListIterator;

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

        String[] projection = {
                MeiContract.FeedEntry._ID,
                MeiContract.FeedEntry.COLUMN_NAME_CITY,
                MeiContract.FeedEntry.COLUMN_NAME_COUNTRY,
                MeiContract.FeedEntry.COLUMN_NAME_DATE,
                MeiContract.FeedEntry.COLUMN_NAME_WIND,
                MeiContract.FeedEntry.COLUMN_NAME_PRESSURE,
                MeiContract.FeedEntry.COLUMN_NAME_TEMPERATURE
        };
        // How you want the results sorted in the resulting Cursor
        String sortOrder = MeiContract.FeedEntry.COLUMN_NAME_CITY + " DESC";

        Cursor c = db.query(
                MeiContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if(c == null)
            return null;
        return c;
    }

    public Cursor getCity(String city, String country){
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MeiContract.FeedEntry._ID,
                MeiContract.FeedEntry.COLUMN_NAME_CITY,
                MeiContract.FeedEntry.COLUMN_NAME_COUNTRY,
                MeiContract.FeedEntry.COLUMN_NAME_DATE,
                MeiContract.FeedEntry.COLUMN_NAME_WIND,
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

    public long addCity(String city, String country){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MeiContract.FeedEntry.COLUMN_NAME_CITY, city);
        values.put(MeiContract.FeedEntry.COLUMN_NAME_COUNTRY, country);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MeiContract.FeedEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteCity(String city, String country){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = MeiContract.FeedEntry.COLUMN_NAME_CITY + " LIKE ? AND "
                + MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { city, country };
        // Issue SQL statement.
        db.delete(MeiContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public long updateCity(City city){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();

        // Which row to update, based on the title
        String selection = MeiContract.FeedEntry.COLUMN_NAME_CITY + " LIKE ? AND "
                + MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + " LIKE ?";
        String[] selectionArgs = { city.getName(), city.getCountry() };

        values.put(MeiContract.FeedEntry.COLUMN_NAME_CITY, city.getName());
        values.put(MeiContract.FeedEntry.COLUMN_NAME_COUNTRY, city.getCountry());
        values.put(MeiContract.FeedEntry.COLUMN_NAME_DATE, city.getLastDate());
        values.put(MeiContract.FeedEntry.COLUMN_NAME_WIND, city.getWind());
        values.put(MeiContract.FeedEntry.COLUMN_NAME_PRESSURE, city.getPressure());
        values.put(MeiContract.FeedEntry.COLUMN_NAME_TEMPERATURE, city.getTemperature());

        int count = db.update(
                MeiContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void updateCities(ArrayList<City> toUpDate) {
        ListIterator i = toUpDate.listIterator();
        while(i.hasNext()) {
            updateCity((City) i.next());
        }
    }
}
