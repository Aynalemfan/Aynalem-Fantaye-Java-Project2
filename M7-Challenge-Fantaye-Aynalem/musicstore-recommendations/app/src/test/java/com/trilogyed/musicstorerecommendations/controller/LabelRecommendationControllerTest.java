package com.trilogyed.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
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


@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LabelRecommendationControllerTest {
    @MockBean
    private LabelRecommendationRepository labelRecommendationRepository;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;

    public void setUpLabelRecommendationMock(){
        LabelRecommendation newLabel = new LabelRecommendation(100L,100,100,true);
        LabelRecommendation newLabelWithoutId = new LabelRecommendation(100,100,true);
        List<LabelRecommendation> labelRecommendationList= Arrays.asList(newLabel);
        doReturn(labelRecommendationList).when(labelRecommendationRepository).findAll();
        doReturn(newLabel).when(labelRecommendationRepository).save(newLabelWithoutId);

    }
    @Test
    public void getAllLabelRecommendationsShouldReturnList()throws Exception{
        LabelRecommendation newLabel = new LabelRecommendation(100L,100,100,true);
        List<LabelRecommendation> labelRecommendationList= Arrays.asList(newLabel);
        String expectedJsonValue = mapper.writeValueAsString(labelRecommendationList);
        doReturn(labelRecommendationList).when(labelRecommendationRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createLabelRecommendationShouldReturnNewLabel()throws Exception{
        LabelRecommendation outputLabelRecommendation = new LabelRecommendation(100L,100,100,true);
        LabelRecommendation inputLabelRecommendation = new LabelRecommendation(100,100,true);
        String outputLabelRecommendationJson = mapper.writeValueAsString(outputLabelRecommendation);
        String inputLabelRecommendationJson = mapper.writeValueAsString(inputLabelRecommendation);
        when(labelRecommendationRepository.save(inputLabelRecommendation)).thenReturn(outputLabelRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/labelRecommendation")
                        .content(inputLabelRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelRecommendationJson));
    }
    @Test
    public void getLabelShouldReturnOneLabel()throws Exception{
        LabelRecommendation labelRecommendation=new LabelRecommendation(100L,100,100,true);
        String expectedJsonValue = mapper.writeValueAsString(labelRecommendation);

        doReturn(Optional.of(labelRecommendation)).when(labelRecommendationRepository).findById(100L);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/labelRecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateById() throws Exception {
        LabelRecommendation labelRecommendation = new LabelRecommendation(100L,100,100,true);

        String expectedJsonValue=mapper.writeValueAsString(labelRecommendation);
        mockMvc.perform(
                        put("/labelRecommendation/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteById() throws Exception {
        LabelRecommendation labelRecommendation = new LabelRecommendation( 100L,100,100,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/labelRecommendation/1")).andExpect(status().isNoContent());
    }



}