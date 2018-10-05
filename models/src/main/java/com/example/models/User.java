package com.example.models;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by clairescout on 9/25/18.
 */

public class User {
    private static User single_instance;
    private ArrayList<Trip> trips;

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
    }

    private void makeFakeData() {
        Trip trip = new Trip("Jerusalem", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf") );
        Trip trip2 = new Trip("Jordan", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf") );
        Trip trip3 = new Trip("Hawaii", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", "asdf") );
        trips.add(trip);
        trips.add(trip2);
        trips.add(trip3);
    }
}
