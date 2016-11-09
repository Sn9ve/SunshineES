package com.project.snave.sunshinees;

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
        public static final String COLUMN_NAME_WIND_SPEED = "wind_speed";
        public static final String COLUMN_NAME_WIND_DIRECTION = "wind_direction";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
    }
}
