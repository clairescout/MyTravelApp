package com.example.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by clairescout on 9/25/18.
 */

public class User {
    private static User single_instance;
    private ArrayList<Trip> trips;
    private Map<String, Trip> idToTrips = new HashMap<>();

    private User() {
        trips = new ArrayList<>();
    }

    public static User getInstance() {
        if (single_instance == null) {
            single_instance = new User();
            single_instance.makeFakeData();
        }
        return single_instance;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {

        this.trips = trips;
        for (Trip trip: trips) {
            idToTrips.put(trip.getId(), trip);
        }
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
        idToTrips.put(trip.getId(), trip);
    }

    public Trip getTripById(String id) {
        return idToTrips.get(id);
    }

    private void makeFakeData() {
        Trip trip = new Trip("Jerusalem", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf"), -33.852, 151.211);
        Trip trip2 = new Trip("Jordan", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf"), -20.2, -35.5);
        Trip trip3 = new Trip("Hawaii", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf"), 76.2, 151.211);
        trips.add(trip);
        trips.add(trip2);
        trips.add(trip3);
    }
}
