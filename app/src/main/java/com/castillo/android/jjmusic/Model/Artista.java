package com.castillo.android.jjmusic.Model;

import java.util.List;

/**
 * Created by juanjosecastillo on 13/9/17.
 */

public class Artista {
    private String name;
    private List<Image> image;
    private String listeners;
    private String mbid;

    public String getMbid() {
        return mbid;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    //
    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
