package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.google.gson.Gson;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestParam(value = "login") String login, @RequestParam(value = "pass") String pass) {
        User user = new User(login, pass, Collections.emptyList());
        return new ResponseEntity(userService.createUser(user));
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    public ResponseEntity removeUser(@RequestParam(value = "login") String login, @RequestParam(value = "pass") String pass) {
        User user = new User(login, pass, Collections.emptyList());
        return new ResponseEntity(userService.removeUser(user));
    }

    @RequestMapping(value = "/checkPermission", method = RequestMethod.GET)
    public ResponseEntity checkPermission(@RequestParam(value = "login") String login, @RequestParam(value = "pass") String pass) {
        User user = new User(login, pass, Collections.emptyList());
        HttpStatus status = userService.hasPermissions(user) ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return new ResponseEntity(status);
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers(){
        return new Gson().toJson(userService.getAllUsers());
    }
}
