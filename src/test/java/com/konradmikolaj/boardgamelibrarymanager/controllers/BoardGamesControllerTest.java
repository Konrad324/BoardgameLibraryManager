package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.DemoContentCreator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

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
            "{\"userLogin\":\"user_1\",\"title\":\"Osadnicy z Catanu\",\"description\":\"Ekonomiczna\",\"localization\":\"Dom\"}," +
            "{\"userLogin\":\"user_1\",\"title\":\"Domek\",\"description\":\"Lekka gra rodzinna\",\"localization\":\"user_1\"}" +
            "]";

    private final static String NEW_GAME = "{\"title\":\"Neuroshima Hex\",\"description\":\"Strategia\",\"localization\":\"Dom\"}";
    private final static String NEW_GAME_WRONG_JSON = "{\"title\"\"Neuroshima Hex\",\"description\":\"Strategia\",\"localization\":\"Dom\"";

    private final static String UPDATE_GAME = "{\"userLogin\":\"user_2\",\"title\":\"Splendor\",\"description\":\"Mózgożerna ekonomia update\",\"localization\":\"Schowek na miotły\"}";
    private final static String UPDATE_GAME_WRONG_JSON = "{\"userLogin\":\"user_2\",\"title\"lendor\",\"description\":\"Mózgożerna ekonomia\",\"localization\":\"Dom\"}";

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

    @Test
    public void saveNewGame_correctJsonAndCredentials() throws Exception {
        final User user = User.of("user_2","pass_2");

        mockMvc.perform(post("/saveGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(NEW_GAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    public void saveNewGame_incorrectJson() throws Exception {
        final User user = User.of("user_2","pass_2");

        mockMvc.perform(post("/saveGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(NEW_GAME_WRONG_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    public void saveNewGame_incorrectCredentials() throws Exception {
        final User user = User.of("user_2","incorrect_pass");

        mockMvc.perform(post("/saveGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(NEW_GAME))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    public void updateGame_correctJsonAndCredentials() throws Exception {
        final User user = User.of("user_2","pass_2");

        mockMvc.perform(post("/updateGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(UPDATE_GAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    public void updateGame_incorrectJson() throws Exception {
        final User user = User.of("user_2","pass_2");

        mockMvc.perform(post("/updateGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(UPDATE_GAME_WRONG_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(StringUtils.EMPTY));
    }

    @Test
    public void updateGame_incorrectCredentials() throws Exception {
        final User user = User.of("user_2","incorrect_pass");

        mockMvc.perform(post("/updateGame")
                .param("login", user.getLogin())
                .param("pass", user.getPassword())
                .content(UPDATE_GAME))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string(StringUtils.EMPTY));
    }
}