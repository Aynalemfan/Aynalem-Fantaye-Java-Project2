package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository albumRecommendationRepository;


    @RequestMapping(value = "/albumRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody AlbumRecommendation album) {
        return albumRecommendationRepository.save(album);
    }

    @RequestMapping(value = "/albumRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbum(){
        return albumRecommendationRepository.findAll();
    }

    @RequestMapping(value = "/albumRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public AlbumRecommendation findAlbumRecommendation(@PathVariable Long id){
        Optional<AlbumRecommendation> album = albumRecommendationRepository.findById(id);

        if (album.isPresent() == false) {

            throw new IllegalArgumentException("Invalid album id!");

        } else {

            return album.get();
        }
    }
    @RequestMapping(value = "/albumRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public AlbumRecommendation updateAlbumRecommendation(@RequestBody AlbumRecommendation album, @PathVariable Long id){
        if (album.getAlbumRecommendationId() == null) {
            album.setAlbumRecommendationId(id);
        } else if (album.getAlbumRecommendationId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return albumRecommendationRepository.save(album);

    }
    @RequestMapping(value = "/albumRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable Long id) {
        albumRecommendationRepository.deleteById(id);

    }
}

