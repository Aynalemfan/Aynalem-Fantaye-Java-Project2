package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "artist")
public class Artist implements Serializable {
    @Id
    @Column(name = "artist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;
    private String name;
    private String instagram;
    private String twitter;

    public Artist() {
    }

    public Artist(Long artistId,String name,String instagram,String twitter) {
        this.artistId = artistId;
        this.name = name;
        this.instagram = instagram;
        this.twitter = twitter;
    }

    public Artist(String name,String instagram,String twitter) {
        this.name = name;
        this.instagram = instagram;
        this.twitter = twitter;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(getArtistId(),artist.getArtistId()) && Objects.equals(getName(),artist.getName()) && Objects.equals(getInstagram(),artist.getInstagram()) && Objects.equals(getTwitter(),artist.getTwitter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArtistId(),getName(),getInstagram(),getTwitter());
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", name='" + name + '\'' +
                ", instagram='" + instagram + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
