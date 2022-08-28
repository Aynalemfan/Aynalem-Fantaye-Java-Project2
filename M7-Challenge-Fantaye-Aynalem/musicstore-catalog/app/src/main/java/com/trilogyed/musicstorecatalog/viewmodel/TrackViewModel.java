package com.trilogyed.musicstorecatalog.viewmodel;

import com.trilogyed.musicstorecatalog.model.Album;

import java.util.List;
import java.util.Objects;

public class TrackViewModel {

    private Long trackId;
    private Long albumId;
    private String title;
    private int runTime;

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackViewModel)) return false;
        TrackViewModel that = (TrackViewModel) o;
        return getRunTime() == that.getRunTime() && Objects.equals(getTrackId(),that.getTrackId()) && Objects.equals(getAlbumId(),that.getAlbumId()) && Objects.equals(getTitle(),that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrackId(),getAlbumId(),getTitle(),getRunTime());
    }
}
