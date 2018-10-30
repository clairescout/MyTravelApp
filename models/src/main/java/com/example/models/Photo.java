package com.example.models;


/**
 * Created by clairescout on 9/27/18.
 */

public class Photo extends Memory{
    private String photoPath;
    private byte[] byteArray = null;
    private int photoDrawable;
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

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public int getPhotoDrawable() {
        return photoDrawable;
    }

    public void setPhotoDrawable(int photoDrawable) {
        this.photoDrawable = photoDrawable;
    }
}
