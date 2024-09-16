package com.example.musicplayer;

import java.io.Serializable;

public class Music implements Serializable {
    Long currentID;
    String title;
    String artist;
   String data;
    Long Date;

    Long Album;

    public Music(Long currentID, String title, String artist, String data, Long date, Long album) {
        this.currentID = currentID;
        this.title = title;
        this.artist = artist;
        this.data = data;
        Date = date;
        Album = album;
    }

    public Long getCurrentID() {
        return currentID;
    }

    public void setCurrentID(Long currentID) {
        this.currentID = currentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }

    public Long getAlbum() {
        return Album;
    }

    public void setAlbum(Long album) {
        Album = album;
    }
}
