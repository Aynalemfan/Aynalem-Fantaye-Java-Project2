package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository artistRecommendationRepository;


    @RequestMapping(value = "/artistRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@RequestBody ArtistRecommendation artist) {
        return artistRecommendationRepository.save(artist);
    }

    @RequestMapping(value = "/artistRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtist(){
        return artistRecommendationRepository.findAll();
    }

    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public ArtistRecommendation findArtistRecommendation(@PathVariable Long id){
        Optional<ArtistRecommendation> artist = artistRecommendationRepository.findById(id);

        if (artist.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return artist.get();
        }
    }
    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public ArtistRecommendation updateArtistRecommendation(@RequestBody ArtistRecommendation artist, @PathVariable Long id){
        if (artist.getArtistRecommendationId() == null) {
            artist.setArtistRecommendationId(id);
        } else if (artist.getArtistRecommendationId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return artistRecommendationRepository.save(artist);

    }
    @RequestMapping(value = "/artistRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable Long id) {
        artistRecommendationRepository.deleteById(id);

    }
}
