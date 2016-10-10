package com.project.snave.sunshinees;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Snave on 09/10/2016.
 */
public class AddCityActivity extends Activity {
    Button save;
    EditText city;
    EditText country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityListActivity.cities.add(new City(city.getText().toString(), country.getText().toString()));

                /*Intent intent = new Intent(MainActivity.this, DiceActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
