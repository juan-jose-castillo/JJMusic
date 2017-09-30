package com.castillo.android.jjmusic.Model;

import java.util.ArrayList;

/**
 * Created by juanjosecastillo on 29/9/17.
 */

public class ResultTrack {

    private String name;
    private String duration;
    private ArrayList<Image> image;
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
