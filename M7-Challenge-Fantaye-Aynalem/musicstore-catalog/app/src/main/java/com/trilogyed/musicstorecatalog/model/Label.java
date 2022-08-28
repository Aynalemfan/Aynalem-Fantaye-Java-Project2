package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "label")
public class Label implements Serializable {
    @Id
    @Column(name = "label_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labelId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "labelId")
    private Set<Album> albums = new HashSet<>();
    private String name;
    private String website;

    public Label() {
    }

    public Label(Long labelId,Set<Album> albums,String name,String website) {
        this.labelId = labelId;
        this.albums = albums;
        this.name = name;
        this.website = website;
    }

    public Label(Set<Album> albums,String name,String website) {
        this.albums = albums;
        this.name = name;
        this.website = website;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
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
        if (!(o instanceof Label)) return false;
        Label label = (Label) o;
        return Objects.equals(getLabelId(),label.getLabelId()) && Objects.equals(getAlbums(),label.getAlbums()) && Objects.equals(getName(),label.getName()) && Objects.equals(getWebsite(),label.getWebsite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabelId(),getAlbums(),getName(),getWebsite());
    }

    @Override
    public String toString() {
        return "Label{" +
                "labelId=" + labelId +
                ", albums=" + albums +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
