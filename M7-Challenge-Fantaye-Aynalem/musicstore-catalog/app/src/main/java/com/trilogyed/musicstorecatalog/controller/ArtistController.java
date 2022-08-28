package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(value = "/artists", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Artist> getAllArtist(){
        return artistRepository.findAll();
    }

    @RequestMapping(value = "/artists/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Artist findArtist(@PathVariable Long id){
        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent() == false) {

            throw new IllegalArgumentException("It is invalid id");

        } else {

            return artist.get();
        }
    }
    @RequestMapping(value = "/artists/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Artist updateArtist(@RequestBody Artist artist, @PathVariable Long id){
        if (artist.getArtistId() == null) {
            artist.setArtistId(id);
        } else if (artist.getArtistId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return artistRepository.save(artist);

    }
    @RequestMapping(value = "/artists{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        artistRepository.deleteById(id);

    }

}
