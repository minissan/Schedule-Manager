package com.example.nissan.schedulemanager.models;

/**
 * Created by nissan on 12/31/17.
 */

public class ExpertList {

    private String name;
    private String arrival_date;
    private String departure_date;

    public ExpertList(){

    }

    public ExpertList(String name, String arrival_date, String departure_date) {
        this.name = name;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
