package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.google.gson.Gson;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/createUser")
    public HttpStatus createUser(@RequestParam(value = "login") String login, @RequestParam(value = "pass") String pass) {
        User user = new User(login, pass, Collections.emptyList());
        return userService.createUser(user);
    }

    @RequestMapping("/removeUser")
    public HttpStatus removeUser(@RequestParam(value = "login") String login, @RequestParam(value = "pass") String pass) {
        User user = new User(login, pass, Collections.emptyList());
        return userService.removeUser(user);
    }

    @RequestMapping("/getAllUsers")
    public String getAllUsers(){
        return new Gson().toJson(userService.getAllUsers());
    }
}
