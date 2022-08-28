package com.trilogyed.musicstorecatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
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
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AlbumControllerTest {
    @MockBean
    private AlbumRepository albumRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    public void setUpAlbumMock(){
        Album newAlbum = new Album(2L, "album1", 2L, (LocalDate.of(2010, 1, 5)), 4L, (new BigDecimal(19.99)));
        Album newAlbum1 = new Album(2L, "album1", 2L, (LocalDate.of(2010, 1, 5)), 4L, (new BigDecimal(19.99)));
        List<Album> albumList= Arrays.asList(newAlbum);
        doReturn(albumList).when(albumRepository).findAll();
        doReturn(newAlbum).when(albumRepository).save(newAlbum1);

    }
    @Test
    public void getAllAlbumShouldReturnList()throws Exception{
        Album newAlbum = new Album(2L, "album1", 2L, (LocalDate.of(2010, 1, 5)), 4L, (new BigDecimal(19.99)));
        List<Album> albumList= Arrays.asList(newAlbum);
        String expectedJsonValue = mapper.writeValueAsString(albumList);
        doReturn(albumList).when(albumRepository).findAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonValue));

    }


    @Test
    public void createAlbumShouldReturnNewAlbum()throws Exception{
        Album outputAlbum = new Album(2L, "album1", 2L, (LocalDate.of(2010, 1, 5)), 4L, (new BigDecimal(19.99)));
        Album inputAlbum = new Album(2L, "album1", 2L, (LocalDate.of(2010, 1, 5)), 4L, (new BigDecimal(19.99)));
        String outputAlbumJson = mapper.writeValueAsString(outputAlbum);
        String inputAlbumJson = mapper.writeValueAsString(inputAlbum);
        when(albumRepository.save(inputAlbum)).thenReturn(outputAlbum);

        mockMvc.perform(MockMvcRequestBuilders.post("/albums")
                        .content(inputAlbumJson)
                .content(String.valueOf(MediaType.APPLICATION_JSON)))

                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json((outputAlbumJson)));

    }
    @Test
    public void getAlbumShouldReturnAlbum()throws Exception{
        Album album = new Album();
        String expectedJsonValue=mapper.writeValueAsString(album);

        doReturn(Optional.of(album)).when(albumRepository).findById(10L);

        ResultActions result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/album/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(expectedJsonValue))
                );
    }


    @Test
    public void shouldUpdateById() throws Exception {
        Album album = new Album();

        String expectedJsonValue = mapper.writeValueAsString(album);
        mockMvc.perform(
                        put("/albums/1")
                                .content(expectedJsonValue)
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk());

    }
    @Test
    public void shouldDeleteById() throws Exception {
        Album album = new Album();
        mockMvc.perform(MockMvcRequestBuilders.delete("/albums/1")).andExpect(status().isNoContent());
    }



}