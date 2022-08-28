package com.trilogyed.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRecommendationRepository albumRecommendationRepository;

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    MockMvc mockMvc;

    public void setUpAlbumRecommendationMock(){
        AlbumRecommendation newAlbum = new AlbumRecommendation(100L,100,100,true);
        AlbumRecommendation newAlbumWithoutId =new AlbumRecommendation(100,100,true);
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(newAlbum);
        doReturn(albumRecommendationList).when(albumRecommendationRepository).findAll();
        doReturn(newAlbum).when(albumRecommendationRepository).save(newAlbumWithoutId);

    }
    @Test
    public void getAllAlbumRecommendationsShouldReturnList()throws Exception{
        AlbumRecommendation newAlbum = new AlbumRecommendation(100L,100,100,true);
        List<AlbumRecommendation> albumRecommendationList= Arrays.asList(newAlbum);
        String expectedJsonValue = mapper.writeValueAsString(albumRecommendationList);
        doReturn(albumRecommendationList).when(albumRecommendationRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/albumRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }

    @Test
    public void createAlbumRecommendationShouldReturnNewLabel()throws Exception{
        AlbumRecommendation outputAlbumRecommendation=new AlbumRecommendation(100L,100,100,true);
        AlbumRecommendation inputAlbumRecommendation= new AlbumRecommendation(100,100,true);
        String outputAlbumRecommendationJson = mapper.writeValueAsString(outputAlbumRecommendation);
        String inputAlbumRecommendationJson = mapper.writeValueAsString(inputAlbumRecommendation);
        when(albumRecommendationRepository.save(inputAlbumRecommendation)).thenReturn(outputAlbumRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.post("/albumRecommendation")
                        .content(inputAlbumRecommendationJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumRecommendationJson));
    }
    @Test
    public void getArtistShouldReturn()throws Exception{
        AlbumRecommendation albumRecommendation=new AlbumRecommendation(100L,100,100,true);
        String expectedJsonValue = mapper.writeValueAsString(albumRecommendation);

        doReturn(Optional.of(albumRecommendation)).when(albumRecommendationRepository).findById(100L);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/albumRecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    };


    @Test
    public void shouldUpdateById() throws Exception {
        AlbumRecommendation albumRecommendation = new AlbumRecommendation(100L,100,100,true);

        String expectedJsonValue=mapper.writeValueAsString(albumRecommendation);
        mockMvc.perform(
                        put("/albumRecommendation/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteById() throws Exception {
        AlbumRecommendation albumRecommendation = new AlbumRecommendation( 100L,100,100,true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/albumRecommendation/1")).andExpect(status().isNoContent());
    }

}