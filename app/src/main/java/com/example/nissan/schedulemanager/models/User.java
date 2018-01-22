package com.example.nissan.schedulemanager.models;

/**
 * Created by nissan on 12/26/2017.
 */
public class User {

    public String name;
    public String arrival_date;
    public String departure_date;
    //public Map<String, Boolean> hData = new HashMap<>();

    public User() {
        // Default constructor
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
