package com.example.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by clairescout on 9/25/18.
 */

public class Trip {
    private String name;
    private List<Memory> memories;
    private Date startDate;
    private Date endDate;
    private Song song;
    private double latitude;
    private double longitude;
    private String id;


    public Trip() {
    }

    public Trip(String name, List<Memory> memories, Date startDate, Date endDate, Song song, double latitude, double longitude) {
        this.name = name;
        this.memories = memories;
        this.startDate = startDate;
        this.endDate = endDate;
        this.song = song;
        this.id = UUID.randomUUID().toString();
    }

    public Trip(String name, Date startDate, Date endDate){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getStartDateString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(startDate);
    }

    public String getEndDateString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(endDate);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
