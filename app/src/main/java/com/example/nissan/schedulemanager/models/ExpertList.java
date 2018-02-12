package com.example.nissan.schedulemanager.models;

/**
 * Created by nissan on 12/31/17.
 */

public class ExpertList {

    private String name;
    private String arrival;
    private String departure;
    private String arrival_airport;
    private String arrival_time;
    private String check_in;
    private String check_out;
    private String curr_loc;
    private String estm_stay;
    private String hotel_name;
    private String origin;
    private String origin_flight_no;
    private String origin_flight_time;
    private String transit_airport;
    private String transit_flight_no;
    private String transit_flight_time;

    /*public ExpertList(){

    }

    public ExpertList(String name, String arrival, String departure, String arrival_airport, String arrival_time,
                      String check_in, String check_out, String curr_loc, String estm_stay, String hotel_name, String origin,
                      String origin_flight_no, String origin_flight_time, String transit_airport, String transit_flight_no,
                      String transit_flight_time) {
        this.name = name;
        this.arrival = arrival;
        this.departure = departure;
        this.arrival_airport = arrival_airport;
        this.arrival_time = arrival_time;
        this.check_in = check_in;
        this.check_out = check_out;
        this.curr_loc = curr_loc;
        this.estm_stay = estm_stay;
        this.hotel_name = hotel_name;
        this.origin = origin;
        this.origin_flight_no = origin_flight_no;
        this.origin_flight_time = origin_flight_time;
        this.transit_airport = transit_airport;
        this.transit_flight_no = transit_flight_no;
        this.transit_flight_time = transit_flight_time;
    } */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(String arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getCurr_loc() {
        return curr_loc;
    }

    public void setCurr_loc(String curr_loc) {
        this.curr_loc = curr_loc;
    }

    public String getEstm_stay() {
        return estm_stay;
    }

    public void setEstm_stay(String estm_stay) {
        this.estm_stay = estm_stay;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin_flight_no() {
        return origin_flight_no;
    }

    public void setOrigin_flight_no(String origin_flight_no) {
        this.origin_flight_no = origin_flight_no;
    }

    public String getOrigin_flight_time() {
        return origin_flight_time;
    }

    public void setOrigin_flight_time(String origin_flight_time) {
        this.origin_flight_time = origin_flight_time;
    }

    public String getTransit_airport() {
        return transit_airport;
    }

    public void setTransit_airport(String transit_airport) {
        this.transit_airport = transit_airport;
    }

    public String getTransit_flight_no() {
        return transit_flight_no;
    }

    public void setTransit_flight_no(String transit_flight_no) {
        this.transit_flight_no = transit_flight_no;
    }

    public String getTransit_flight_time() {
        return transit_flight_time;
    }

    public void setTransit_flight_time(String transit_flight_time) {
        this.transit_flight_time = transit_flight_time;
    }
}
