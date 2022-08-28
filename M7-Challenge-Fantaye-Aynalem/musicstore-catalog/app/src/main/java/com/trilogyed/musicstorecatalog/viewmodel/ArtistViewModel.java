package com.trilogyed.musicstorecatalog.viewmodel;

import com.trilogyed.musicstorecatalog.model.Album;

import javax.persistence.*;
import java.util.*;

public class ArtistViewModel {

    private Long artistId;
    private String name;
    private String instagram;
    private String twitter;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
