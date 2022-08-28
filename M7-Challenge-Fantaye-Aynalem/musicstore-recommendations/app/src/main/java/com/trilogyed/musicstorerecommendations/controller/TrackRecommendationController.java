package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrackRecommendationController {
    @Autowired
    private TrackRecommendationRepository trackRecommendationRepository;


    @RequestMapping(value = "/trackRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody TrackRecommendation track) {
        return trackRecommendationRepository.save(track);
    }

    @RequestMapping(value = "/trackRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TrackRecommendation> getAllTrack(){
        return trackRecommendationRepository.findAll();
    }

    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public TrackRecommendation findTrackRecommendation(@PathVariable Long id){
        Optional<TrackRecommendation> track = trackRecommendationRepository.findById(id);

        if (track.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return track.get();
        }
    }
    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public TrackRecommendation updateTrackRecommendation(@RequestBody TrackRecommendation track, @PathVariable Long id){
        if (track.getTrackRecommendationId() == null) {
            track.setTrackRecommendationId(id);
        } else if (track.getTrackRecommendationId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return trackRecommendationRepository.save(track);

    }
    @RequestMapping(value = "/trackRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable Long id) {
        trackRecommendationRepository.deleteById(id);

    }
}
