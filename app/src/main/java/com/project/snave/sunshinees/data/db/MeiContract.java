package com.project.snave.sunshinees.data.db;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Snave on 09/11/2016.
 */
public class MeiContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MeiContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_WIND = "wind";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";

        public static String AUTHORITY = "com.project.snave.sunshinees";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY).buildUpon()
                .appendPath(TABLE_NAME).build();
        public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/" + AUTHORITY + "/" + TABLE_NAME;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/" + AUTHORITY + "/" + TABLE_NAME;

        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
