package com.example.models;


/**
 * Created by clairescout on 9/27/18.
 */

public class Photo extends Memory{
    private String photoPath;

    public Photo(String text, String photoPath) {
        super(text);
        this.photoPath = photoPath;
    }

    public Photo() {
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
