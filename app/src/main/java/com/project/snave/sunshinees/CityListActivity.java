package com.project.snave.sunshinees;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CityListActivity extends AppCompatActivity {

    ListView CityView ;
    public static ArrayList<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities.add(new City("Détroit","USA"));
        cities.add(new City("Venice","USA"));

        CityView = (ListView) findViewById(R.id.listView);

        CityAdapter adapter = new CityAdapter(CityListActivity.this, cities);
        CityView.setAdapter(adapter);

    }
}
