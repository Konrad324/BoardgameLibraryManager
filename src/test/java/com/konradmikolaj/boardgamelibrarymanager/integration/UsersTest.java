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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoContentCreator demoContentCreator;

    @Before
    public void setUp() throws Exception {
        demoContentCreator.cleanDatabase();
    }

    @Test
    public void createUser_checkIfExist_removeUser() throws Exception {
        final User user = User.of("new_user","password");
        final String EXPECTED_USERS = "[\"new_user\"]";
        final String EMPTY_EXPECTED_USERS = "[]";

        mockMvc.perform(post("/createUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/checkPermission")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/getAllUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EXPECTED_USERS)));

        mockMvc.perform(post("/removeUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/getAllUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(EMPTY_EXPECTED_USERS)));
    }

    @Test
    public void createUser_removeUser_cannotRemoveUserTwice() throws Exception {
        final User user = User.of("new_user","password");

        mockMvc.perform(post("/createUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/removeUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/checkPermission")
                .param("login", user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/removeUser")
                .param("login",user.getLogin())
                .param("pass", user.getPassword()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}