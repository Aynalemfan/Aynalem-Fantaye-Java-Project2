package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrackController {
    @Autowired
    private TrackRepository trackRepository;

    @RequestMapping(value = "/tracks", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Track createTrack(@RequestBody Track track) {
        return trackRepository.save(track);
    }

    @RequestMapping(value = "/tracks", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Track> getAllTracks(){
        return trackRepository.findAll();
    }

    @RequestMapping(value = "/tracks/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Track findTracks(@PathVariable Long id){
        Optional<Track> track = trackRepository.findById(id);

        if (track.isPresent() == false) {

            throw new IllegalArgumentException("It is invalid id");

        } else {

            return track.get();
        }
    }
    @RequestMapping(value = "/track/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Track updateTrack(@RequestBody Track track, @PathVariable Long id){
        if (track.getTrackId() == null) {
            track.setTrackId(id);
        } else if (track.getTrackId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return trackRepository.save(track);

    }
    @RequestMapping(value = "/tracks/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable Long id) {
        trackRepository.deleteById(id);

    }
}
