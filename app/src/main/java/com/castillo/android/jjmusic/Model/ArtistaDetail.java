package com.castillo.android.jjmusic.Model;

import java.util.List;

/**
 * Created by juanjosecastillo on 14/9/17.
 */

public class ArtistaDetail {
    private String name;
    private String mbid;
    private List<Image> image;
    private Bio bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Bio getBio() {
        return bio;
    }

    public void setBio(Bio bio) {
        this.bio = bio;
    }
}
