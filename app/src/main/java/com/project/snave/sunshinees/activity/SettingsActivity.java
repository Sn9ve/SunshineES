package com.project.snave.sunshinees.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.project.snave.sunshinees.R;

/**
 * Created by Snave on 19/11/2016.
 */
public class SettingsActivity extends Activity {
    public final String PREFS_NAME = "preference";
    RadioButton F, C, kmh, mph;
    RadioGroup temp, wind;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        save = (Button) findViewById(R.id.saveSettings);
        F = (RadioButton) findViewById(R.id.radioButtonF);
        C = (RadioButton) findViewById(R.id.radioButtonC);
        kmh = (RadioButton) findViewById(R.id.radioButtonKMH);
        mph = (RadioButton) findViewById(R.id.radioButtonMPH);

        temp = (RadioGroup) findViewById(R.id.radioGroup);
        wind = (RadioGroup) findViewById(R.id.radioGroup2);

        if(CityListActivity.prefTemperature.equals("c")){
            C.setChecked(true);
        }else if(CityListActivity.prefTemperature.equals("f")){
            F.setChecked(true);
        }
        if(CityListActivity.prefWindSpeed.equals("kmh")){
            kmh.setChecked(true);
        }else if(CityListActivity.prefWindSpeed.equals("mph")){
            mph.setChecked(true);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences.Editor editor = CityListActivity.settings.edit();
                if (C.isChecked()){
                    Log.v("C", " C checked");
                    editor.putString("temperature", "c");
                }else if(F.isChecked()) {
                    Log.v("V", " V checked");
                    editor.putString("temperature", "f");
                }
                if(kmh.isChecked()) {
                    Log.v("kmh", " kmh checked");
                    editor.putString("wind_speed", "kmh");
                }else if(mph.isChecked()) {
                        Log.v("mph", " mph checked");
                        editor.putString("wind_speed", "mph");
                }
                // Commit the edits!
                if(editor.commit()) {
                    Toast.makeText(getBaseContext(), "you just updated your preference ", Toast.LENGTH_LONG).show();
                    CityListActivity.prefTemperature = CityListActivity.settings.getString("temperature", "c");
                    CityListActivity.prefWindSpeed = CityListActivity.settings.getString("wind_speed", "kmh");
                }
                //on quitte l'activité pour revenir à l'activité principale
                finish();
            }
        });
    }
}
