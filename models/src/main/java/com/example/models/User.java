package com.example.models;

import java.util.ArrayList;

/**
 * Created by clairescout on 9/25/18.
 */

public class User {
    private static User single_instance;
    private ArrayList<Trip> trips;

    private User() {
        trips = new ArrayList<>();
    }

    public User getInstance() {
        if (single_instance == null) {
            single_instance = new User();
        }
        return single_instance;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }
}
