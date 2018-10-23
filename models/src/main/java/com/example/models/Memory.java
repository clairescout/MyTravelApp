package com.example.models;

import java.util.UUID;

/**
 * Created by clairescout on 9/27/18.
 */

public class Memory {
    private String text; // will string be long enough?
    private String id;

    public Memory(String text) {
        this.text = text;
        id = UUID.randomUUID().toString();
    }

    public Memory(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public Memory() {
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
}

