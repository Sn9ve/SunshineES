package com.project.snave.sunshinees.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.snave.sunshinees.R;
import com.project.snave.sunshinees.data.City;

import java.util.List;

/**
 * Created by Snave on 10/10/2016.
 * Simple classe qui hérite d'adapter pour créer une vue spécifique à l'affichage de la liste
 * ainsi que la gestion du roulement de données de la liste
 */
public class CityAdapter extends ArrayAdapter<City> {

    public CityAdapter(Context context, List<City> cities) {
        super(context, 0, cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_city, parent, false);
        }

        CityViewHolder viewHolder = (CityViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CityViewHolder();
            viewHolder.city = (TextView) convertView.findViewById(R.id.textCity);
            viewHolder.country = (TextView) convertView.findViewById(R.id.textCountry);
            convertView.setTag(viewHolder);
        }

        City city = getItem(position);

        viewHolder.city.setText(city.getName());
        viewHolder.country.setText(city.getCountry());

        return convertView;
    }
}