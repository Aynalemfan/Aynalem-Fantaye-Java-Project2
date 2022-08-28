package com.trilogyed.musicstorecatalog.viewmodel;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Track;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumViewModel {
    private Long albumId;
    private String title;
    private Long artistId;
    private LocalDate releaseDate;
    private Long labelId;
    private BigDecimal listPrice;
    private List<Track> tracks = new ArrayList<>();

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

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlbumViewModel)) return false;
        AlbumViewModel that = (AlbumViewModel) o;
        return Objects.equals(getAlbumId(),that.getAlbumId()) && Objects.equals(getTitle(),that.getTitle()) && Objects.equals(getArtistId(),that.getArtistId()) && Objects.equals(getReleaseDate(),that.getReleaseDate()) && Objects.equals(getLabelId(),that.getLabelId()) && Objects.equals(getListPrice(),that.getListPrice()) && Objects.equals(getTracks(),that.getTracks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlbumId(),getTitle(),getArtistId(),getReleaseDate(),getLabelId(),getListPrice(),getTracks());
    }
}
