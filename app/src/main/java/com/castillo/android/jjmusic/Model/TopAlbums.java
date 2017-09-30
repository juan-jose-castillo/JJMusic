package com.castillo.android.jjmusic.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 18/9/17.
 */

public class TopAlbums {
    private ArrayList<Album> album;
    ////



    private Attr attr;

    public ArrayList<Album> getAlbumList() {
        return album;
    }

    public void setAlbumList(ArrayList<Album> album) {
        this.album = album;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }
}
