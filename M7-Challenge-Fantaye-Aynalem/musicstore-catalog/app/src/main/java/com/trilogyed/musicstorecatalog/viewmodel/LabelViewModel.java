package com.trilogyed.musicstorecatalog.viewmodel;

import com.trilogyed.musicstorecatalog.model.Album;

import java.util.*;

public class LabelViewModel {

    private Long labelId;
    private List<Album> albums = new ArrayList<>();
    private String name;
    private String website;

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelViewModel)) return false;
        LabelViewModel that = (LabelViewModel) o;
        return Objects.equals(getLabelId(),that.getLabelId()) && Objects.equals(getAlbums(),that.getAlbums()) && Objects.equals(getName(),that.getName()) && Objects.equals(getWebsite(),that.getWebsite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabelId(),getAlbums(),getName(),getWebsite());
    }
}
