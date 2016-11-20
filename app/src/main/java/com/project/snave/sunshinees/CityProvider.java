package com.project.snave.sunshinees;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.project.snave.sunshinees.activity.CityListActivity;
import com.project.snave.sunshinees.data.db.MeiContract;

/**
 * Created by Snave on 20/11/2016.
 */
public class CityProvider extends ContentProvider {

    // Creates a UriMatcher object.
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        sUriMatcher.addURI(
                MeiContract.FeedEntry.AUTHORITY,
                MeiContract.FeedEntry.TABLE_NAME,
                1
        );

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
        sUriMatcher.addURI(
                MeiContract.FeedEntry.AUTHORITY,
                MeiContract.FeedEntry.TABLE_NAME + "/*/*",
                2
        );
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        /*
         * Choose the table to query and a sort order based on the code returned for the incoming
         * URI. Here, too, only the statements for table 3 are shown.
         */
        Cursor c = null;
            switch (sUriMatcher.match(uri)) {
                // If the incoming URI was for all of table3
                case 1:
                    c = CityListActivity.db.citiesProvider(
                            projection, selection, selectionArgs, sortOrder);
                    break;

                // If the incoming URI was for a single row
                case 2:
                    c = CityListActivity.db.cityProvider(uri, projection, sortOrder);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
            // call the code to actually do the query
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case 1:
                return MeiContract.FeedEntry.DIR_TYPE;
            case 2:
                return MeiContract.FeedEntry.ITEM_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        switch (sUriMatcher.match(uri)) {
            case 1: {
                id = CityListActivity.db.addProvider(uri, values);
                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return MeiContract.FeedEntry.buildWeatherUri(id);
                }
            }
            default:
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int id;
        switch (sUriMatcher.match(uri)) {
            case 1:
                id = CityListActivity.db.deleteProvider(uri, selection, selectionArgs);
                break;
            case 2:
                String segment = uri.getLastPathSegment();
                id = CityListActivity.db.deleteProvider(uri, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (id > 0) {
            // Notify the Context's ContentResolver of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int id;
        switch (sUriMatcher.match(uri)) {
            case 2:
                id = CityListActivity.db.updateProvider(
                        uri,
                        values,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        if (id > 0) {
            // Notify the Context's ContentResolver of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }
}
