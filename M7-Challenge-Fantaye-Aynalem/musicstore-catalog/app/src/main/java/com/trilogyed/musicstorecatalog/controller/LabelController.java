package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LabelController {
    @Autowired
    private LabelRepository labelRepository;

    @RequestMapping(value = "/labels", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Label createLabel(@RequestBody Label label) {
        return labelRepository.save(label);
    }

    @RequestMapping(value = "/labels", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Label> getAllLabels(){
        return labelRepository.findAll();
    }

    @RequestMapping(value = "/labels/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Label findLabel(@PathVariable Long id){
        Optional<Label> label = labelRepository.findById(id);

        if (label.isPresent() == false) {

            throw new IllegalArgumentException("It is invalid id");

        } else {

            return label.get();
        }
    }
    @RequestMapping(value = "/labels/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public Label updateLabel(@RequestBody Label label, @PathVariable Long id){
        if (label.getLabelId() == null) {
            label.setLabelId(id);
        } else if (label.getLabelId() != id) {
            throw new IllegalArgumentException("Id doesn't match.");
        }
        return labelRepository.save(label);

    }
    @RequestMapping(value = "/labels/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelRepository.deleteById(id);

    }
}
