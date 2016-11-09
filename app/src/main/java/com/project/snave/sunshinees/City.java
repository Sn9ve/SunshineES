package com.project.snave.sunshinees;

import java.util.ArrayList;

/**
 * Created by Snave on 09/10/2016.
 */
public class City {
    private String name;
    private String country;
    private String lastDate;
    private String wind;                // km/h
    private String pressure;            // hPa
    private String temperature;         // c°

    public City(String name, String country) {
        this.country = country;
        this.name = name;
        this.lastDate = "missing";
        this.wind = "missing";
        this.pressure = "missing";
        this.temperature = "missing";
    }

    /**
     * fonction qui permet de mettre à jour tous les attributs d'une caractéristique d'une ville
     * prend en paramètre un arraylist contenant toutes les informations à jour
     */
    public boolean updateData(ArrayList<String> data){
        if(!data.isEmpty()){
            setWind(data.get(0));
            setTemperature(data.get(1));
            setPressure(data.get(2));
            setLastDate(data.get(3));
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}