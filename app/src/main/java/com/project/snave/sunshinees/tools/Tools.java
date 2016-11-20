package com.project.snave.sunshinees.tools;

import android.database.Cursor;

import com.project.snave.sunshinees.data.City;
import com.project.snave.sunshinees.data.db.MeiContract;

import java.util.ArrayList;

/**
 * Created by Snave on 19/11/2016.
 */
public class Tools {

    public static void feedList(ArrayList<City> toFeed, Cursor c) {
        //if (c != null) {
            if(c.moveToFirst()) {
                String city, country, date, wind, pressure, temperature;
                do {
                    city = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_CITY));
                    country = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_COUNTRY));
                    date = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_DATE));
                    wind = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_WIND));
                    pressure = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_PRESSURE));
                    temperature = c.getString(c.getColumnIndexOrThrow(MeiContract.FeedEntry.COLUMN_NAME_TEMPERATURE));

                    if(pressure == "missing" || pressure == null)
                        toFeed.add(new City(city, country));
                    else
                        toFeed.add(new City(city, country, date, wind, pressure, temperature));
                } while (c.moveToNext());
            }
        //}
    }
    public static String toFahrenheit(String unit, String data) {
        return unit.equals("f") ?
                (Double.parseDouble(data) * 1.8 + 32) + " F"
                : data + " °C";
    }

    public static String toMiles(String unit, String data) {
        String[] wind = data.split(" ");
        return unit.equals("mph") ?
                (Double.parseDouble(wind[0]) * 1.609344) + wind[1] + " mph"
                : data + " km/h";
    }
}
