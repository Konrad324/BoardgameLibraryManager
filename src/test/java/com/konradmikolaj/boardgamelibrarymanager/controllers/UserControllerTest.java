package com.konradmikolaj.boardgamelibrarymanager.controllers;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoContentCreator demoContentCreator;

    @Before
    public void setUp() throws Exception {
        demoContentCreator.prepare();
    }

    @Test
    public void checkPermissions_correctUser() throws Exception {
        final User user = User.of("user_1","pass_1");

        mockMvc.perform(get("/checkPermission")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void checkPermissions_incorrectUser() throws Exception {
        final User user = User.of("user_1","wrong_password");

        mockMvc.perform(get("/checkPermission")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void createUser_newUser() throws Exception {
        final User user = User.of("new_user","password");

        mockMvc.perform(post("/createUser")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_existingUserLogin() throws Exception {
        final User user = User.of("user_1","password");

        mockMvc.perform(post("/createUser")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void removeUser_correctCredentials() throws Exception {
        final User user = User.of("user_3","pass_3");

        mockMvc.perform(post("/removeUser")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void removeUser_userNotExist() throws Exception {
        final User user = User.of("user_non_existing","pass_1");

        mockMvc.perform(post("/removeUser")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void removeUser_incorrectCredentials() throws Exception {
        final User user = User.of("user_1","wrong_password");

        mockMvc.perform(post("/removeUser")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers() throws Exception {
        final String EXPECTED_USERS = "[\"user_1\",\"user_2\",\"user_3\"]";

        mockMvc.perform(get("/getAllUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_USERS)));
    }
}