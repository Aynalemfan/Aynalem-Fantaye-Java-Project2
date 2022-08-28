package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;


    @RequestMapping(value = "/albums", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album) {
        return albumRepository.save(album);
    }

    @RequestMapping(value = "/albums", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    @RequestMapping(value = "/albums/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Album findAlbum(@PathVariable Long id){
        Optional<Album> album = albumRepository.findById(id);

        if (album.isPresent() == false) {

            throw new IllegalArgumentException("It is invalid id");

        } else {

            return album.get();
        }
    }

    @RequestMapping(value = "/albums/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Album updateAlbum(@RequestBody Album album, @PathVariable Long id){
        if (album.getAlbumId() == null) {
            album.setAlbumId(id);
        } else if (album.getAlbumId() != id) {
            throw new IllegalArgumentException("Id doesn't match.");
        }
        return albumRepository.save(album);

    }
    @RequestMapping(value = "/albums/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Long id) {
        albumRepository.deleteById(id);

    }

}
