package com.trilogyed.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistRecommendationControllerTest {
    @MockBean
    private ArtistRecommendationRepository artistRecommendationRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    public void setUpArtistRecommendationMock(){
        ArtistRecommendation newArtist = new ArtistRecommendation(100L,100,100,true);
        ArtistRecommendation newArtistWithoutId = new ArtistRecommendation(100,100,true);
        List<ArtistRecommendation> artistRecommendationList= Arrays.asList(newArtist);
        doReturn(artistRecommendationList).when(artistRecommendationRepository).findAll();
        doReturn(newArtist).when(artistRecommendationRepository).save(newArtistWithoutId);

    }
    @Test
    public void getAllArtistRecommendationsShouldReturnList()throws Exception{
        ArtistRecommendation newArtist = new ArtistRecommendation(100L,100,100,true);
        List<ArtistRecommendation> artistRecommendationList= Arrays.asList(newArtist);
        String expectedJsonValue = mapper.writeValueAsString(artistRecommendationList);
        doReturn(artistRecommendationList).when(artistRecommendationRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }


    @Test
    public void createArtistRecommendationShouldReturnNewLabel()throws Exception{
        ArtistRecommendation outputArtistRecommendation = new ArtistRecommendation(100L,100,100,true);
        ArtistRecommendation inputArtistRecommendation = new ArtistRecommendation(100,100,true);
        String outputArtistRecommendationJson = mapper.writeValueAsString(outputArtistRecommendation);
        String inputArtistRecommendationJson = mapper.writeValueAsString(inputArtistRecommendation);
        when(artistRecommendationRepository.save(inputArtistRecommendation)).thenReturn(outputArtistRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/artistRecommendation")
                        .content(inputArtistRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistRecommendationJson));
    }
    @Test
    public void getArtistShouldReturnArtist()throws Exception{
        ArtistRecommendation artistRecommendation=new ArtistRecommendation(100L,100,100,true);
        String expectedJsonValue=mapper.writeValueAsString(artistRecommendation);

        doReturn(Optional.of(artistRecommendation)).when(artistRecommendationRepository).findById(100L);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/artistRecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateById() throws Exception {
        ArtistRecommendation artistRecommendation = new ArtistRecommendation(100L,100,100,true);

        String expectedJsonValue=mapper.writeValueAsString(artistRecommendation);
        mockMvc.perform(
                        put("/artistRecommendation/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteById() throws Exception {
        ArtistRecommendation artistRecommendation = new ArtistRecommendation( 100L,100,100,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/artistRecommendation/1")).andExpect(status().isNoContent());
    }



}