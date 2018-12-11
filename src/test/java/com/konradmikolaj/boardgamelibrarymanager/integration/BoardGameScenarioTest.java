package com.konradmikolaj.boardgamelibrarymanager.integration;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.DemoContentCreator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.konradmikolaj.boardgamelibrarymanager.services.DemoContentCreator.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardGameScenarioTest {

    private final static User USER = User.of(USER_3,PASS_3);
    private final static User OTHER_USER = User.of(USER_1,PASS_1);

    private final static String EMPTY_JSON_ARRAY = "[]";
    private final static String CREATE_GAME_1 = "{\"userLogin\":\"user_3\",\"title\":\"Splendor\",\"description\":\"Mózgożerna ekonomia update\",\"localization\":\"Dom\"}";
    private final static String CREATE_GAME_2 = "{\"userLogin\":\"user_3\",\"title\":\"Szczęść Boże\",\"description\":\"Euro ekonomiczna symulacja kopalni\",\"localization\":\"Dom\"}";
    private final static String UPDATE_GAME_1 = "{\"userLogin\":\"user_3\",\"title\":\"Splendor\",\"description\":\"Gra ekonomiczno-strategiczna\",\"localization\":\"Schowek na miotły\"}";
    private final static String REMOVE_GAME_1 = "{\"title\":\"Splendor\"}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoContentCreator demoContentCreator;

    @Before
    public void setUp() throws Exception {
        demoContentCreator.cleanDatabase();
        demoContentCreator.createUsers();
    }

    private static String wrapToJsonArray(String ... objects){
        return "[" + String.join(",", objects) + "]";
    }

    @Test
    public void saveGame_checkIfSaved_saveAnotherGame_checkIfBothExist() throws Exception {

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1)));

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_2))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1, CREATE_GAME_2)));

    }

    @Test
    public void saveGame_checkIfSaved_saveSameGame_cannotSaveTwiceSameGame() throws Exception {

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1)));

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1)));

    }

    @Test
    public void saveGame_updateGame_checkIfUpdated() throws Exception {

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/updateGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(UPDATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(UPDATE_GAME_1)));

    }

    @Test
    public void updateGame_cannotUpdateNonExistingGame() throws Exception {

        mockMvc.perform(post("/updateGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(UPDATE_GAME_1))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));

    }

    @Test
    public void cannotMakeOperationsOnOtherUserGameByPassingOtherUserLoginInBody() throws Exception {

        mockMvc.perform(post("/saveGame")
                .param("login", OTHER_USER.getLogin())
                .param("pass", OTHER_USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        //Game hasn't been saved for USER, but for USER_OTHER
        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));

        //Correctly create user_1 game to then try modify/delete it
        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());


        mockMvc.perform(post("/updateGame")
                .param("login", OTHER_USER.getLogin())
                .param("pass", OTHER_USER.getPassword())
                .content(UPDATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/removeGame")
                .param("login", OTHER_USER.getLogin())
                .param("pass", OTHER_USER.getPassword())
                .content(UPDATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        //Game still exist and its data didn't change
        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1)));
    }

    @Test
    public void saveGame_removeGame_gameNotExist() throws Exception {

        mockMvc.perform(post("/saveGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(CREATE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME_1)));

        mockMvc.perform(post("/removeGame")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword())
                .content(REMOVE_GAME_1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login", USER.getLogin())
                .param("pass", USER.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));

    }

}