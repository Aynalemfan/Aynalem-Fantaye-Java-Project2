package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@WebMvcTest(Label.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelControllerTest {

    @MockBean
    private LabelRepository labelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
    }

    public void setUpLabelMock(){
        Label newLabel = new Label();
        Label newLabel1 = new Label();
        List<Label> labelList= Arrays.asList(newLabel);
        doReturn(labelList).when(labelRepository).findAll();
        doReturn(newLabel).when(labelRepository).save(newLabel1);

    }
    @Test
    public void getLabelById() throws Exception {
        Label newLabel = new Label();
        List<Label> labelList= Arrays.asList(newLabel);
        String expectedJsonValue = objectMapper.writeValueAsString(labelList);
        doReturn(labelList).when(labelRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/labels"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void addLabel() throws Exception {
        Label outputLabel = new Label();
        Label inputLabel = new Label();
        String outputLabelJson = objectMapper.writeValueAsString(outputLabel);
        String inputLabelJson = objectMapper.writeValueAsString(inputLabel);
        when(labelRepository.save(inputLabel)).thenReturn(outputLabel);

        mockMvc.perform(MockMvcRequestBuilders.post("/labels")
                        .content(inputLabelJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelJson));
    }
    @Test
    public void getLabelShouldReturnOneLabel() throws Exception {
        Label label = new Label();
        String expectedJsonValue = objectMapper.writeValueAsString(label);

        doReturn(Optional.of(label)).when(label).getLabelId();

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/labels/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    }


    @Test
    public void updateLabel() throws Exception {
        Label label = new Label();

        String expectedJsonValue = objectMapper.writeValueAsString(label);
        mockMvc.perform(
                        put("/labels/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void deleteLabel() throws Exception {
        Label label = new Label();
        mockMvc.perform(MockMvcRequestBuilders.delete("/label/1")).andExpect(status().isNoContent());
    }



}