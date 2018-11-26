package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.google.gson.Gson;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @RequestMapping("/test")
    public String getTest(){
        return "test";
    }

    @RequestMapping("/allGames")
    public String getAllGames(){
        return new Gson().toJson(boardGameRepository.findAll());
    }

    @RequestMapping("/userGames/{userId}")
    public String getAllGames(@PathVariable Long userId){
        return new Gson().toJson(boardGameRepository.findBoardGamesByUserId(userId));
    }
}
