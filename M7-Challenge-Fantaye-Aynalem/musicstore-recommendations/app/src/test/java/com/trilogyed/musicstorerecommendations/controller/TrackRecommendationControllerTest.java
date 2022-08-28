package com.trilogyed.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRecommendationRepository trackRecommendationRepository;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;

    public void setUpTrackRecommendationMock(){
        TrackRecommendation newTrack =new TrackRecommendation(100L,100,100,true);
        TrackRecommendation newTrackWithoutId =new TrackRecommendation(100,100,true);
        List<TrackRecommendation> trackRecommendationList= Arrays.asList(newTrack);
        doReturn(trackRecommendationList).when(trackRecommendationRepository).findAll();
        doReturn(newTrack).when(trackRecommendationRepository).save(newTrackWithoutId);

    }
    @Test
    public void getAllTrackRecommendationsShouldReturnList()throws Exception{
        TrackRecommendation newTrack = new TrackRecommendation(100L,100,100,true);
        List<TrackRecommendation> trackRecommendationList= Arrays.asList(newTrack);
        String expectedJsonValue =mapper.writeValueAsString(trackRecommendationList);
        doReturn(trackRecommendationList).when(trackRecommendationRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createTrackRecommendationShouldReturnNewLabel()throws Exception{
        TrackRecommendation outputTrackRecommendation=new TrackRecommendation(100L,100,100,true);
        TrackRecommendation inputTrackRecommendation= new TrackRecommendation(100,100,true);
        String outputTrackRecommendationJson=mapper.writeValueAsString(outputTrackRecommendation);
        String inputTrackRecommendationJson = mapper.writeValueAsString(inputTrackRecommendation);
        when(trackRecommendationRepository.save(inputTrackRecommendation)).thenReturn(outputTrackRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/trackRecommendation")
                        .content(inputTrackRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackRecommendationJson));
    }
    @Test
    public void getTrackShouldReturn()throws Exception{
        TrackRecommendation trackRecommendation=new TrackRecommendation(100L,100,100,true);
        String expectedJsonValue=mapper.writeValueAsString(trackRecommendation);

        doReturn(Optional.of(trackRecommendation)).when(trackRecommendationRepository).findById(100L);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/trackRecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateById() throws Exception {
        TrackRecommendation trackRecommendation = new TrackRecommendation(100L,100,100,true);

        String expectedJsonValue=mapper.writeValueAsString(trackRecommendation);
        mockMvc.perform(
                        put("/trackRecommendation/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteById() throws Exception {
        TrackRecommendation trackRecommendation = new TrackRecommendation( 100L,100,100,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/trackRecommendation/1")).andExpect(status().isNoContent());
    }



}