package com.example.models;

import sun.jvm.hotspot.utilities.BitMap;

/**
 * Created by clairescout on 9/27/18.
 */

public class Photo extends Memory{
    private BitMap photo;

    public Photo(String text, BitMap photo) {
        super(text);
        this.photo = photo;
    }

    public Photo(BitMap photo) {
        this.photo = photo;
    }

    public Photo() {
    }

    public BitMap getPhoto() {
        return photo;
    }

    public void setPhoto(BitMap photo) {
        this.photo = photo;
    }
}
