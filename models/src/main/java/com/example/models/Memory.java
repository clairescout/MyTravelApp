package com.example.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by clairescout on 9/27/18.
 */

public class Memory {
    private String text; // will string be long enough?
    private String id;
    private Date date;

    public Memory(String text) {
        this.text = text;
        this.date = new Date();
        id = UUID.randomUUID().toString();

    }

    public Memory(String text, String id) {
        this.text = text;
        this.date = new Date();
        this.id = id;

    }

    public Memory() {
        this.date = new Date();
        id = UUID.randomUUID().toString();

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

