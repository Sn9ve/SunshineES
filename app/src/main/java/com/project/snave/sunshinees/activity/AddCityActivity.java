package com.project.snave.sunshinees.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.snave.sunshinees.data.City;
import com.project.snave.sunshinees.R;

/**
 * Created by Snave on 09/10/2016.
 * classe de l'activité d'ajout de ville
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
                tmpCity = "";
                tmpCountry = "";
                //on récupère les valeurs contenue dans nos différent inputText
                tmpCity = city.getText().toString();
                tmpCountry = country.getText().toString();
                //on vérifie que nos information ne sont pas vide
                if(!tmpCity.isEmpty() && !tmpCountry.isEmpty()){
                    //on ajoute directement dans l'adapter la nouvelle ville
                    //au lieu de l'ajouter dans l'arraylist
                    //puis ensuite notifier l'adapter de ce changement
                    CityListActivity.adapter.add(new City(tmpCity, tmpCountry));
                    CityListActivity.db.addCity(tmpCity, tmpCountry);
                    Toast.makeText(getBaseContext(), "you just added " + tmpCity + ", "
                            + tmpCountry, Toast.LENGTH_LONG).show();
                    //on quitte l'activité pour revenir à l'activité principale
                    finish();
                } else{
                    Toast.makeText(getBaseContext(), "missing information", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
