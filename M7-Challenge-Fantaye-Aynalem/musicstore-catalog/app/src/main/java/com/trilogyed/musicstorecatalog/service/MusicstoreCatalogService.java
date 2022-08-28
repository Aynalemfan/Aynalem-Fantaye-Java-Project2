package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import com.trilogyed.musicstorecatalog.viewmodel.AlbumViewModel;
import com.trilogyed.musicstorecatalog.viewmodel.ArtistViewModel;
import com.trilogyed.musicstorecatalog.viewmodel.LabelViewModel;
import com.trilogyed.musicstorecatalog.viewmodel.TrackViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class MusicstoreCatalogService {
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;
    private LabelRepository labelRepository;
    private TrackRepository trackRepository;

    @Autowired
    public MusicstoreCatalogService(AlbumRepository albumRepository,ArtistRepository artistRepository,LabelRepository labelRepository,TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.labelRepository = labelRepository;
        this.trackRepository = trackRepository;
    }
    //Album Service Layer
    public List<AlbumViewModel> findAllAlbums() {
        List<Album> albumList = albumRepository.findAll();
        List<AlbumViewModel> albumViewModelList = new ArrayList<>();

        for (Album album : albumList) {
            AlbumViewModel albumVModel = buildAlbumViewModel(album);
            albumViewModelList.add(albumVModel);
        }
        return albumViewModelList;
    }

    private AlbumViewModel buildAlbumViewModel(Album album) {

        AlbumViewModel albumViewModel = new AlbumViewModel();
        albumViewModel.setAlbumId(album.getAlbumId());
        albumViewModel.setTitle(album.getTitle());
        albumViewModel.setArtistId(album.getArtistId());
        albumViewModel.setReleaseDate(album.getReleaseDate());
        albumViewModel.setLabelId(album.getLabelId());
        albumViewModel.setListPrice(album.getListPrice());
        return albumViewModel;
    }
    public AlbumViewModel findAlbum(Long id) {
        Optional<Album> album = albumRepository.findById(id);
            return album.isPresent() ? buildAlbumViewModel(album.get()) : null;
    }


    public AlbumViewModel saveAlbum(AlbumViewModel albumViewModel) {
        Album albumOne = new Album();
        albumOne.setTitle(albumViewModel.getTitle());
        albumOne.setReleaseDate(albumViewModel.getReleaseDate());
        albumOne.setListPrice(albumViewModel.getListPrice());
        albumOne.setLabelId(albumViewModel.getLabelId());
        albumOne.setArtistId(albumViewModel.getArtistId());
        albumOne = albumRepository.save(albumOne);
        albumViewModel.setAlbumId(albumOne.getAlbumId());

        List<Track> tracks = albumViewModel.getTracks();
        tracks.stream().forEach(track -> {
            track.setAlbumId(albumViewModel.getAlbumId());
            trackRepository.save(track);
        });
        tracks = trackRepository.findAllTracksByAlbumId(albumViewModel.getAlbumId());
        albumViewModel.setTracks(tracks);
        return albumViewModel;

    }

    public void updateAlbum(AlbumViewModel albumViewModel) {
        Album album = new Album();
//        album.setAlbumId(albumViewModel.getAlbumId());
        album.setArtistId(albumViewModel.getArtistId());
        album.setLabelId(albumViewModel.getLabelId());
        album.setListPrice(albumViewModel.getListPrice());
        album.setReleaseDate(albumViewModel.getReleaseDate());

        albumRepository.save(album);

        List<Track> trackList = trackRepository.findAllTracksByAlbumId(album.getAlbumId());
        trackList.stream().forEach(track -> trackRepository.deleteById(track.getTrackId()));

        List<Track> tracks = albumViewModel.getTracks();
        tracks.stream()
                .forEach(track ->
                {
                    track.setAlbumId(albumViewModel.getAlbumId());
                    track = trackRepository.save(track);
                });
    }

    public void removeAlbum(Long id) {

        List<Track> trackList = trackRepository.findAllTracksByAlbumId(id);

        trackList.stream()
                .forEach(track -> trackRepository.deleteById(track.getTrackId()));

        albumRepository.deleteById(id);

    }

    //Artist service layer

    public List<ArtistViewModel> findAllArtist() {
        List<Artist> artistList = artistRepository.findAll();
        List<ArtistViewModel> artistViewModelList = new ArrayList<>();

        for (Artist artist : artistList) {
            ArtistViewModel artistVModel = buildArtistViewModel(artist);
            artistViewModelList.add(artistVModel);
        }
        return artistViewModelList;
    }

    private ArtistViewModel buildArtistViewModel(Artist artist) {
        ArtistViewModel artistViewModel = new ArtistViewModel();
        artistViewModel.setArtistId(artist.getArtistId());
        artistViewModel.setName(artist.getName());
        artistViewModel.setInstagram(artist.getInstagram());
        artistViewModel.setTwitter(artist.getTwitter());

        return artistViewModel;

    }

    public ArtistViewModel findArtist(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        return artist.isPresent() ? buildArtistViewModel(artist.get()) : null;
    }

    public ArtistViewModel saveArtist(ArtistViewModel artistViewModel) {
        Artist artistOne = new Artist();
        artistOne.setArtistId(artistViewModel.getArtistId());
        artistOne.setName(artistViewModel.getName());
        artistOne.setInstagram(artistViewModel.getInstagram());
        artistOne.setTwitter(artistViewModel.getTwitter());
        artistOne = artistRepository.save(artistOne);
        artistViewModel.setArtistId(artistOne.getArtistId());


        return artistViewModel;

    }

    public void updateArtist(ArtistViewModel artistViewModel) {
        Artist artist = new Artist();
        artist.setArtistId(artistViewModel.getArtistId());
        artist.setName(artistViewModel.getName());
        artist.setInstagram(artistViewModel.getInstagram());
        artist.setTwitter(artistViewModel.getTwitter());

        artistRepository.save(artist);

        List<Album> albumList = albumRepository.findAllAlbumsByArtistId(artist.getArtistId());
        albumList.stream().forEach(album -> albumRepository.deleteById(album.getAlbumId()));

    }

    public void removeArtist(Long id) {

        List<Album> albumList = albumRepository.findAllAlbumsByArtistId(id);

        albumList.stream()
                .forEach(album -> albumRepository.deleteById(album.getAlbumId()));

        artistRepository.deleteById(id);

    }

    //Label service layer

    public List<LabelViewModel> findAllLabel() {
        List<Label> labelList = labelRepository.findAll();
        List<LabelViewModel> labelViewModelList = new ArrayList<>();

        for (Label label : labelList) {
            LabelViewModel labelVModel = buildLabelViewModel(label);
            labelViewModelList.add(labelVModel);
        }
        return labelViewModelList;
    }

    private LabelViewModel buildLabelViewModel(Label label) {

        LabelViewModel labelViewModel = new LabelViewModel();
        labelViewModel.setLabelId(label.getLabelId());
        labelViewModel.setName(label.getName());
        labelViewModel.setWebsite(label.getWebsite());
        labelViewModel.setAlbums((List<Album>) label.getAlbums());

        return labelViewModel;

    }

    public LabelViewModel findLabel(Long id) {
        Optional<Label> label = labelRepository.findById(id);
        return label.isPresent() ? buildLabelViewModel(label.get()) : null;
    }

    public LabelViewModel saveLabel(LabelViewModel labelViewModel) {
        Label labelOne = new Label();
        labelOne.setLabelId(labelViewModel.getLabelId());
        labelOne.setName(labelViewModel.getName());
        labelOne.setWebsite(labelViewModel.getWebsite());
        labelOne.setAlbums((Set<Album>) labelViewModel.getAlbums());
        labelOne = labelRepository.save(labelOne);
        labelViewModel.setLabelId(labelOne.getLabelId());

        List<Album> albums = labelViewModel.getAlbums();
        albums.stream().forEach(album -> {
            album.setLabelId(labelViewModel.getLabelId());
            albumRepository.save(album);
        });

        albums = albumRepository.findAllAlbumsByLabelId(labelViewModel.getLabelId());
        labelViewModel.setAlbums(albums);
        return labelViewModel;

    }

    public void updateLabel(LabelViewModel labelViewModel) {
        Label label = new Label();
        label.setLabelId(labelViewModel.getLabelId());
        label.setName(labelViewModel.getName());
        label.setWebsite(labelViewModel.getWebsite());
        label.setAlbums((Set<Album>) labelViewModel.getAlbums());

        labelRepository.save(label);

        List<Album> albumList = albumRepository.findAllAlbumsByLabelId(label.getLabelId());
        albumList.stream().forEach(album -> albumRepository.deleteById(album.getAlbumId()));

        List<Album> albums = (List<Album>) labelViewModel.getAlbums();
        albums.stream()
                .forEach(album ->
                {
                    album.setAlbumId(labelViewModel.getLabelId());
                    album = albumRepository.save(album);
                });
    }

    public void removeLabel(Long id) {

        List<Album> albumList = albumRepository.findAllAlbumsByLabelId(id);

        albumList.stream()
                .forEach(album -> albumRepository.deleteById(album.getAlbumId()));

        artistRepository.deleteById(id);

    }

   // Track service layer

    public List<TrackViewModel> findAllTrack() {
        List<Track> trackList = trackRepository.findAll();
        List<TrackViewModel> trackViewModelList = new ArrayList<>();

        for (Track track : trackList) {
            TrackViewModel trackVModel = buildTrackViewModel(track);
            trackViewModelList.add(trackVModel);
        }
        return trackViewModelList;
    }

    private TrackViewModel buildTrackViewModel(Track track) {
        TrackViewModel trackViewModel = new TrackViewModel();
        trackViewModel.setTrackId(track.getTrackId());
        trackViewModel.setTitle(track.getTitle());
        trackViewModel.setRunTime(track.getRunTime());
        trackViewModel.setAlbumId(track.getAlbumId());
        return trackViewModel;
    }

    public TrackViewModel findTrack(Long id) {
        Optional<Track> track = trackRepository.findById(id);
        return track.isPresent() ? buildTrackViewModel(track.get()) : null;
    }

    public TrackViewModel saveTrack(TrackViewModel trackViewModel) {
        Track trackOne = new Track();
        trackOne.setTrackId(trackViewModel.getTrackId());
        trackOne.setTitle(trackViewModel.getTitle());
        trackOne.setRunTime(trackViewModel.getRunTime());
//        trackOne.setAlbumId(trackViewModel.getAlbumId());
        trackOne = trackRepository.save(trackOne);
        trackViewModel.setTrackId(trackOne.getTrackId());

        return trackViewModel;
    }

    public void updateTrack(TrackViewModel trackViewModel) {
        Track track = new Track();
        track.setTrackId(trackViewModel.getTrackId());
        track.setTitle(trackViewModel.getTitle());
        track.setRunTime(trackViewModel.getRunTime());
//        track.setAlbumId(track.getAlbumId());

        trackRepository.save(track);

    }

    public void removeTrack(Long id) {

        artistRepository.deleteById(id);

    }
}
