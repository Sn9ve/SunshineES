package com.project.snave.sunshinees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CityListActivity extends AppCompatActivity {

    ListView CityView ;
    public static ArrayList<City> cities = new ArrayList<>();
    public static CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities.add(new City("Détroit","USA"));
        cities.add(new City("Venice","USA"));

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        CityView = (ListView) findViewById(R.id.listView);
        adapter = new CityAdapter(CityListActivity.this, cities);
        CityView.setAdapter(adapter);

        CityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                Intent intent = new Intent(CityListActivity.this, CityViewActivity.class);
                intent.putExtra("CITY", city.getName());
                intent.putExtra("COUNTRY", city.getCountry());
                startActivity(intent);
            }
        });

        CityView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                if(cities.contains(city)) {
                    Toast.makeText(getBaseContext(), "you just removed " + city.getName() + ", "
                            + city.getCountry(), Toast.LENGTH_LONG).show();
                    adapter.remove(city);
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(CityListActivity.this, AddCityActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Toast.makeText(getBaseContext(), "no option available", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_refresh:
                RefreshTask rt = new RefreshTask(getBaseContext());
                rt.execute(cities.get(1).getName(), cities.get(1).getCountry());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
