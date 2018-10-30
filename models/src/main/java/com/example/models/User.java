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
        if (idToTrips.containsKey(trip.getId())) {
            idToTrips.put(trip.getId(), trip);
        } else {
            trips.add(trip);
            idToTrips.put(trip.getId(), trip);
        }
    }

    public Trip getTripById(String id) {
        return idToTrips.get(id);
    }

    private void makeFakeData() {
        Trip trip = new Trip("Sundance", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", null), -33.852, 151.211);
        Trip trip2 = new Trip("The Cabin", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", null), -28, 140.2);
        Trip trip3 = new Trip("Washington" +
                "", new ArrayList<Memory>(),
                Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),
                new Song("Mason Jennings", "idk", "idk", null), -45, 160);
//        Photo photo1 = new Photo();
//        photo1.setPhotoPath("R.drawable.jordan_river");
//        photo1.setText("Leo is cute");
//        trip.addMemory(photo1);
        addTrip(trip);
        addTrip(trip2);
        addTrip(trip3);
    }
}
