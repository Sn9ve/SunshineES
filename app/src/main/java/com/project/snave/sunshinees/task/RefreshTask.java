package com.project.snave.sunshinees.task;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.project.snave.sunshinees.activity.CityListActivity;
import com.project.snave.sunshinees.data.City;
import com.project.snave.sunshinees.tools.JSONResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Snave on 13/10/2016.
 */
public class RefreshTask extends AsyncTask<ArrayList<City>, Void, Void> {

    private Context context;

    private HttpURLConnection connection;
    private JSONResponseHandler jsonRH = new JSONResponseHandler();
    //préparation de la requête get
    final String BASE_URL = "https://query.yahooapis.com/v1/public/yql?";
    final String QUERY_PARAM = "q";
    final String FORMAT_PARAM = "format";

    private City tmpCity;

    public RefreshTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(ArrayList<City>... params) {
        //on parcour toute la liste de ville pour mettre à jour les informations des villes
        Iterator i = params[0].iterator();
        while(i.hasNext()) {
            tmpCity = (City) i.next();

            String query = "select * from weather.forecast where woeid in " +
                    "(select woeid from geo.places(1) where text=\'" +
                    tmpCity.getName() + "," + tmpCity.getCountry() + "\')";

            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .build();

            try {
                URL url = new URL(uri.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                //récupération des données puis envoie en paramètre pour la mise à jour
                if(inputStream != null){
                    tmpCity.updateData((ArrayList) jsonRH.handleResponse(inputStream, null));
                    CityListActivity.db.updateCities(CityListActivity.cities);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    //après chaque connection qu'elle est marchée ou pas, on se déconnecte
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "loading...", Toast.LENGTH_LONG).show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(context, "background task, done", Toast.LENGTH_LONG).show();
        super.onPostExecute(aVoid);
    }
}
