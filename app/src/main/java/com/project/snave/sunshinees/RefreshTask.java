package com.project.snave.sunshinees;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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

    final String BASE_URL = "https://query.yahooapis.com/v1/public/yql?";
    final String QUERY_PARAM = "q";
    final String FORMAT_PARAM = "format";

    private String country, city;
    private City tmpCity;

    public RefreshTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(ArrayList<City>... params) {
        Iterator i = params[0].iterator();
        while(i.hasNext()) {
            tmpCity = (City) i.next();

            String query = "select * from weather.forecast where woeid in " +
                    "(select woeid from geo.places(1) where text=\'" +
                    tmpCity.getName() + "," + tmpCity.getCountry() + "\'";

            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .build();

            URL url = null;
            try {
                url = new URL(uri.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                tmpCity.updateData((ArrayList) jsonRH.handleResponse(inputStream, null));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
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
    protected void onProgressUpdate(Void... values) {
        Toast.makeText(context, "background task, done", Toast.LENGTH_LONG).show();
        super.onProgressUpdate(values);
    }
}
