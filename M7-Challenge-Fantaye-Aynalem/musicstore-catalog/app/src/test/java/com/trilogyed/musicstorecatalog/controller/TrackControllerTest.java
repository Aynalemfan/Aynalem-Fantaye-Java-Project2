package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrackControllerTest {

    @MockBean
    private TrackRepository trackRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        Track newTrack =new Track(100L,100L,"New Title",200);
        Track newTrack1 =new Track(100L,100L,"New Title",200);
        List<Track> trackList= Arrays.asList(newTrack);
        doReturn(trackList).when(trackRepository).findAll();
        doReturn(newTrack).when(trackRepository).save(newTrack1);
    }

    @Test
    public void getTrack() throws Exception{
        Track newTrack = new Track();
        List<Track> trackList= Arrays.asList(newTrack);
        String expectedJsonValue = objectMapper.writeValueAsString(trackList);
        doReturn(trackList).when(trackRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/tracks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));


    }

    @Test
    public void getTrackById() throws Exception {

        Track track = new Track();

        String expectedJsonValue=objectMapper.writeValueAsString(track);
        mockMvc.perform(
                        put("/track/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());
    }

    @Test
    public void addTrack() throws Exception{
        Track outputTrack = new Track();
        Track inputTrack = new Track();
        String outputTrackJson = objectMapper.writeValueAsString(outputTrack);
        String inputTrackJson = objectMapper.writeValueAsString(inputTrack);
        when(trackRepository.save(inputTrack)).thenReturn(outputTrack);

        mockMvc.perform(MockMvcRequestBuilders.post("/tracks")
                        .content(inputTrackJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackJson));
    }

    @Test
    public void updateTrack() throws Exception {
        Track track = new Track();

        String expectedJsonValue = objectMapper.writeValueAsString(track);
        mockMvc.perform(
                        put("/tracks/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTrack() throws Exception{
        Track track = new Track();
        mockMvc.perform(MockMvcRequestBuilders.delete("/tracks/1")).andExpect(status().isNoContent());
    }
}