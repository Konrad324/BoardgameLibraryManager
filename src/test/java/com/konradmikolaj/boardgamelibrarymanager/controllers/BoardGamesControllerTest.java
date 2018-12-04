package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.DemoContentCreator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardGamesControllerTest {

    private final static String EMPTY_JSON_ARRAY = "[]";

    private final static String USER_1_GAMES = "[" +
            "{\"id\":1,\"userLogin\":\"user_1\",\"title\":\"Osadnicy z Catanu\",\"description\":\"Ekonomiczna\",\"localization\":\"Dom\"}," +
            "{\"id\":2,\"userLogin\":\"user_1\",\"title\":\"Domek\",\"description\":\"Lekka gra rodzinna\",\"localization\":\"user_1\"}" +
            "]";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoContentCreator demoContentCreator;

    @Before
    public void setUp() throws Exception {
        demoContentCreator.prepare();
    }

    @Test
    public void getAllGames_correctUser() throws Exception {
        final User user = User.of("user_1","pass_1");

        mockMvc.perform(get("/userGames")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(USER_1_GAMES));
    }

    @Test
    public void getAllGames_incorrectUser() throws Exception {
        final User user = User.of("user_not_exist","pass_1");

        mockMvc.perform(get("/userGames")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));
    }

    @Test
    public void getAllGames_wrongPassword() throws Exception {
        final User user = User.of("user_1","incorrect_pass");

        mockMvc.perform(get("/userGames")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));
    }
}