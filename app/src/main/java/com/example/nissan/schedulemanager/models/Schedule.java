package com.example.nissan.schedulemanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nissan on 12/26/2017.
 */

// [START post_class]
@IgnoreExtraProperties
public class Schedule {

    public String uid;
    public String name;
    public String arrival_date;
    public String departure_date;
    public Map<String, Boolean> stars = new HashMap<>();

    public Schedule() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Schedule(String uid, String author, String title, String body) {
        this.uid = uid;
        this.name = name;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("arrival_date", arrival_date);
        result.put("departure_date", departure_date);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]