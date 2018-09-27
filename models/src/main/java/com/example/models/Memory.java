package com.example.models;

/**
 * Created by clairescout on 9/27/18.
 */

public class Memory {
    private String text; // will string be long enough?

    public Memory(String text) {
        this.text = text;
    }

    public Memory() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

