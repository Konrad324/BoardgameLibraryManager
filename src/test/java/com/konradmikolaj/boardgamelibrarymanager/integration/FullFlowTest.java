package com.konradmikolaj.boardgamelibrarymanager.integration;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.DemoContentCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FullFlowTest {

    private final static User user = User.of("new_user","password");
    private final static String EMPTY_JSON_ARRAY = "[]";
    private final static String CREATE_GAME = "{\"title\":\"Splendor\",\"description\":\"Mózgożerna ekonomia update\",\"localization\":\"Składzik\"}";
    private final static String UPDATE_GAME = "{\"title\":\"Splendor\",\"description\":\"Gra ekonomiczno-strategiczna\",\"localization\":\"Schowek na miotły\"}";
    private final static String REMOVE_GAME = "{\"title\":\"Splendor\"}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoContentCreator demoContentCreator;

    @Before
    public void setUp() throws Exception {
        demoContentCreator.cleanDatabase();
    }

    private static String wrapToJsonArray(String ... objects){
        return "[" + String.join(",", objects) + "]";
    }

    @Test
    public void createUser_checkZeroGames_addGame_checkIfAdded_UpdateGame_checkIfUpdated_removeGame_checkIfRemoved_removeUser_checkIfRemoved() throws Exception {

        mockMvc.perform(post("/createUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));

        mockMvc.perform(post("/saveGame")
                .param("login",user.getLogin())
                .param("pass", user.getPassword())
                .content(CREATE_GAME))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(CREATE_GAME)));

        mockMvc.perform(post("/updateGame")
                .param("login",user.getLogin())
                .param("pass", user.getPassword())
                .content(UPDATE_GAME))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(wrapToJsonArray(UPDATE_GAME)));

        mockMvc.perform(post("/removeGame")
                .param("login",user.getLogin())
                .param("pass", user.getPassword())
                .content(REMOVE_GAME))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/userGames")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));

        mockMvc.perform(post("/removeUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/getAllUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EMPTY_JSON_ARRAY));
    }

}