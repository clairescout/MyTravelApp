package com.example.models;

/**
 * Created by clairescout on 9/27/18.
 */

public class Song {
    private String artist;
    private String name;
    private String album;
    private String id;
    // TODO: CHECK THAT THESE NAMES AND TYPES ARE RIGHT SO THAT GSON WILL WORK


    public Song(String artist, String name, String album, String id) {
        this.artist = artist;
        this.name = name;
        this.album = album;
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
