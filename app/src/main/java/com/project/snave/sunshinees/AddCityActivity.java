package com.project.snave.sunshinees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Snave on 09/10/2016.
 */
public class AddCityActivity extends Activity {
    Button save;
    EditText city;
    EditText country;
    String tmpCity, tmpCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        save = (Button) findViewById(R.id.save);
        city = (EditText) findViewById(R.id.editCity);
        country = (EditText) findViewById(R.id.editCountry);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmpCity = city.getText().toString();
                tmpCountry = country.getText().toString();
                CityListActivity.adapter.add(new City(tmpCity, tmpCountry));
                Toast.makeText(getBaseContext(), "you just have add " + tmpCity + ", "
                        + tmpCountry, Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
