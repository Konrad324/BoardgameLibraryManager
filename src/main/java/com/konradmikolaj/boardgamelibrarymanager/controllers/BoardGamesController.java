package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.google.gson.Gson;
import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardGamesController {

    private final BoardGameService boardGameService;

    @Autowired
    public BoardGamesController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @RequestMapping("/saveGame")
    public ResponseEntity saveGame(@RequestParam(value = "login") String login,
                                   @RequestParam(value = "pass") String pass,
                                   @RequestParam(value = "title") String title,
                                   @RequestParam(value = "description") String description,
                                   @RequestParam(value = "localization") String localization){
        return new ResponseEntity(boardGameService.createBoardGame(
                User.of(login, pass),
                BoardGame.of(login, title, description, localization)));
    }

    @RequestMapping("/updateGame")
    public ResponseEntity updateGame(@RequestParam(value = "login") String login,
                                     @RequestParam(value = "pass") String pass,
                                     @RequestParam(value = "id") Long id,
                                     @RequestParam(value = "title") String title,
                                     @RequestParam(value = "description") String description,
                                     @RequestParam(value = "localization") String localization){
        return new ResponseEntity(boardGameService.updateBoardGame(
                User.of(login, pass),
                BoardGame.of(id, login, title, description, localization)));
    }

    @RequestMapping("/removeGame")
    public ResponseEntity removeGame(@RequestParam(value = "login") String login,
                                     @RequestParam(value = "pass") String pass,
                                     @RequestParam(value = "id") Long id){
        return new ResponseEntity(boardGameService.removeBoardGame(
                User.of(login, pass),
                BoardGame.of(id, login)));
    }

    @RequestMapping("/userGames")
    public String getAllUserGames(@RequestParam(value = "login") String login,
                                  @RequestParam(value = "pass") String pass){
        return new Gson().toJson(boardGameService.getBoardGames(User.of(login, pass)));
    }
}
