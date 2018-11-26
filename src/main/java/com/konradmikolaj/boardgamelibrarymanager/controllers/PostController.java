package com.konradmikolaj.boardgamelibrarymanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @RequestMapping("/putTest")
    public void saveTest(@RequestParam(name = "test") String value) {
        System.out.println("Received value: " + value);
    }
}
