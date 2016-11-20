package com.project.snave.sunshinees.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.project.snave.sunshinees.data.City;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Snave on 09/11/2016.
 */
public class MeiManage {
    private MeiHelper mDbHelper;
    // Gets the data repository in read mode
    private SQLiteDatabase read;
    // Gets the data repository in write mode
    private SQLiteDatabase write;

    public MeiManage(Context context) {
        mDbHelper = new MeiHelper(context);
        read = mDbHelper.getReadableDatabase();
        write = mDbHelper.getWritableDatabase();
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

        Cursor c = read.query(
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

    public long addCity(String city, String country){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MeiContract.FeedEntry.COLUMN_NAME_CITY, city);
        values.put(MeiContract.FeedEntry.COLUMN_NAME_COUNTRY, country);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = write.insert(MeiContract.FeedEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteCity(String city, String country){
        // Define 'where' part of query.
        String selection = MeiContract.FeedEntry.COLUMN_NAME_CITY + " LIKE ? AND "
                + MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { city, country };

        write.delete(MeiContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public long updateCity(City city){
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

        int count = read.update(
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

    /******************
     * PROVIDER
    ******************/
    public Cursor citiesProvider(String[] projection, String selection,
                                 String[] selectionArgs, String sortOrder){

        Cursor c = read.query(
                MeiContract.FeedEntry.TABLE_NAME,       // The table to query
                projection,                             // The columns to return
                selection,                              // The columns for the WHERE clause
                selectionArgs,                          // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );
        if(c == null)
            return null;
        return c;
    }

    public Cursor cityProvider(Uri uri, String[] projection, String sortOrder){
        String selection = MeiContract.FeedEntry.COLUMN_NAME_CITY + " LIKE ? AND "
                + MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + " LIKE ?";
        Cursor c = read.query(
                MeiContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{uri.getPathSegments().get(1), uri.getLastPathSegment()},
                null,
                null,
                sortOrder
        );
        if(c == null)
            return null;
        return c;
    }

    public long addProvider(Uri uri, ContentValues values){
        return write.insert(MeiContract.FeedEntry.TABLE_NAME, null, values);
    }

    public int deleteProvider(Uri uri, String selection, String[] selectionArgs){
       return write.delete(
               MeiContract.FeedEntry.TABLE_NAME,
               selection,
               new String[]{uri.getPathSegments().get(1), uri.getLastPathSegment()});
    }

    public int updateProvider(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        return read.update(
                MeiContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                new String[] { uri.getLastPathSegment()});
    }

    public void updateAllProvider(ArrayList<City> toUpDate) {
        ListIterator i = toUpDate.listIterator();
        while(i.hasNext()) {
            updateCity((City) i.next());
        }
    }
}
