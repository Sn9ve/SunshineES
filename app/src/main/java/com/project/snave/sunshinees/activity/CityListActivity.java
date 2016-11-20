package com.project.snave.sunshinees.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.snave.sunshinees.data.City;
import com.project.snave.sunshinees.data.db.MeiContract;
import com.project.snave.sunshinees.view.CityAdapter;
import com.project.snave.sunshinees.R;
import com.project.snave.sunshinees.task.RefreshTask;
import com.project.snave.sunshinees.tools.Tools;
import com.project.snave.sunshinees.data.db.MeiManage;

import java.util.ArrayList;

public class CityListActivity extends AppCompatActivity{ //implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView CityView ;
    public static ArrayList<City> cities = new ArrayList<>();
    public static CityAdapter adapter;
    public static SimpleCursorAdapter sCursorAdapter;
    public static MeiManage db;
    RefreshTask rt;
    public static SharedPreferences settings;
    // Restore preferences
    public static String prefTemperature, prefWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rt = new RefreshTask(getBaseContext());

        db = new MeiManage(this);
        //cities.add(new City("Detroit","United States")); //example
        Tools.feedList(cities, db.getAllCities());
        /** PROVIDER **/
        /*ContentResolver resolver = getContentResolver();
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
        Tools.feedList(cities,
                resolver.query(
                        MeiContract.FeedEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder
                )
        );*/

        // Restore preferences
        settings = this.getPreferences(0);
        prefTemperature = settings.getString("temperature", "c");
        prefWindSpeed = settings.getString("wind_speed", "kmh");
        Log.v("prefTemperature", prefTemperature);
        Log.v("prefWindSpeed", prefWindSpeed);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        CityView = (ListView) findViewById(R.id.listView);
        /*
        getLoaderManager().initLoader(0, null, this);
        sCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                new String[] {
                        MeiContract.FeedEntry.COLUMN_NAME_CITY,
                        MeiContract.FeedEntry.COLUMN_NAME_COUNTRY
                },
                new int[] { android.R.id.text1, android.R.id.text2},
                0
        );
        CityView.setAdapter(sCursorAdapter);*/
        adapter = new CityAdapter(CityListActivity.this, cities);
        CityView.setAdapter(adapter);

        //action d'affichage des détails d'une ville lorsque l'on appuie sur un itemm de la listeview,
        CityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                Intent intent = new Intent(CityListActivity.this, CityViewActivity.class);
                intent.putExtra("INDEX", cities.indexOf(city));
                startActivity(intent);
            }
        });
        //action de supppression d'une ville lorsque que l'on appuie longuement sur un item
        CityView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                if(cities.contains(city)) {
                    Toast.makeText(getBaseContext(), "you just removed " + city.getName() + ", "
                            + city.getCountry(), Toast.LENGTH_LONG).show();
                    adapter.remove(city);
                    //db.deleteCity(city.getName(), city.getCountry());
                    /** Provide **/
                    ContentResolver resolver = getContentResolver();
                    resolver.delete(
                            MeiContract.FeedEntry.CONTENT_URI.buildUpon().appendPath(city.getCountry()).appendPath(city.getName()).build(),
                            MeiContract.FeedEntry.COLUMN_NAME_COUNTRY + " LIKE ? AND "
                                    + MeiContract.FeedEntry.COLUMN_NAME_CITY + " LIKE ?",
                            null
                    );
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    //gestion du click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(CityListActivity.this, AddCityActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Intent intent2 = new Intent(CityListActivity.this, SettingsActivity.class);
                startActivity(intent2);
                Toast.makeText(getBaseContext(), "no option available", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_refresh:
                rt.execute(cities);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        sCursorAdapter.changeCursor(null);
    }*/
}
