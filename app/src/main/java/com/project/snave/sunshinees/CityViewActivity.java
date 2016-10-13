package com.project.snave.sunshinees;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Snave on 10/10/2016.
 */
public class CityViewActivity extends Activity {

    TextView city, country, wind, temperature, pressure, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        city = (TextView) findViewById(R.id.textAnsCity);
        country = (TextView) findViewById(R.id.textAnsCountry);
        wind = (TextView) findViewById(R.id.textAnsWind);
        temperature = (TextView) findViewById(R.id.textAnsTemp);
        pressure = (TextView) findViewById(R.id.textAnsPressure);
        date = (TextView) findViewById(R.id.textAnsDate);

        String intentCity;
        String intentCountry;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                intentCity = null;
                intentCountry = null;
            } else {
                intentCity = extras.getString("CITY");
                intentCountry = extras.getString("COUNTRY");
            }
        } else {
            intentCity = (String) savedInstanceState.getSerializable("CITY");
            intentCountry = (String) savedInstanceState.getSerializable("COUNTRY");
        }

        city.setText(intentCity);
        country.setText(intentCountry);
    }
}
