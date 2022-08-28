package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
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

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArtistControllerTest {

    @MockBean
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        Artist inputArtist = new Artist();
        Artist newtArtist = new Artist();
        List<Artist> artistList = Arrays.asList(inputArtist);
        doReturn(artistList).when(artistRepository).findAll();
        doReturn(inputArtist).when(artistRepository).save(newtArtist);
    }

    @Test
    public void getArtist() throws Exception {
        Artist inputArtist = new Artist();
        Artist newtArtist = new Artist();
        List<Artist> artistList = Arrays.asList(inputArtist);
        doReturn(artistList).when(artistRepository).findAll();
        doReturn(inputArtist).when(artistRepository).save(newtArtist);

    }
    @Test
    public void addArtist() throws Exception {
        Artist outputArtist = new Artist();
        Artist inputArtist = new Artist();
        String outputArtistJson = objectMapper.writeValueAsString(outputArtist);
        String inputArtistJson = objectMapper.writeValueAsString(inputArtist);
        when(artistRepository.save(inputArtist)).thenReturn(outputArtist);

        mockMvc.perform(MockMvcRequestBuilders.post("/artists")
                        .content(inputArtistJson)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistJson));

    }

    @Test
    public void updateArtist() throws Exception {
        Artist artist = new Artist();
        String expectedJsonValue = objectMapper.writeValueAsString(artist);
        mockMvc.perform(
                        put("/artists/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }

    @Test
    public void deleteArtist() throws Exception {
        Artist artist = new Artist();
        mockMvc.perform(MockMvcRequestBuilders.delete("/artists/{id}")).andExpect(status().isNoContent());
    }

}