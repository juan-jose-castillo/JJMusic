package com.castillo.android.jjmusic.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by juanjosecastillo on 13/9/17.
 */
/*
    @SerializedName("backdrop_path")

 */
public class Image {
    @SerializedName("#text")
    private String text;
    private String size;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
