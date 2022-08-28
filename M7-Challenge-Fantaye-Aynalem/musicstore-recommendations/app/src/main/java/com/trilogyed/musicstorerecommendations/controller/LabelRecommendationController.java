package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LabelRecommendationController {
    @Autowired
    private LabelRecommendationRepository labelRecommendationRepository;


    @RequestMapping(value = "/labelRecommendation", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody LabelRecommendation label) {
        return labelRecommendationRepository.save(label);
    }

    @RequestMapping(value = "/labelRecommendation", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<LabelRecommendation> getAllLabel(){
        return labelRecommendationRepository.findAll();
    }

    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public LabelRecommendation findLabelRecommendation(@PathVariable Long id){
        Optional<LabelRecommendation> label = labelRecommendationRepository.findById(id);

        if (label.isPresent() == false) {

            throw new IllegalArgumentException("invalid id");

        } else {

            return label.get();
        }
    }
    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public LabelRecommendation updateLabelRecommendation(@RequestBody LabelRecommendation label, @PathVariable Long id){
        if (label.getLabelRecommendationId() == null) {
            label.setLabelRecommendationId(id);
        } else if (label.getLabelRecommendationId() != id) {
            throw new IllegalArgumentException("Ids don't match.");
        }
        return labelRecommendationRepository.save(label);

    }
    @RequestMapping(value = "/labelRecommendation/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable Long id) {
        labelRecommendationRepository.deleteById(id);

    }
}
