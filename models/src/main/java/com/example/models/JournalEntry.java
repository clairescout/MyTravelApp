package com.example.models;

public class JournalEntry extends Memory {

    private String title;

    public JournalEntry(String title, String text) {
        super(text);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
