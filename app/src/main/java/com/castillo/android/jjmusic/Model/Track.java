package com.castillo.android.jjmusic.Model;

import java.util.ArrayList;

/**
 * Created by juanjosecastillo on 18/9/17.
 */


public class Track {
    private String name;
    private String duration;
    private ArrayList<Image> image;
    private Artist artist;
    private Wiki wiki;


    public Wiki getWiki() {
        return wiki;
    }

    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

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

}

