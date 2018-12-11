package com.konradmikolaj.boardgamelibrarymanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.BrokenBoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.services.BoardGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@Transactional
public class BoardGamesController {

    private final BoardGameService boardGameService;

    private final ObjectMapper objectMapper;

    @Autowired
    public BoardGamesController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
        this.objectMapper = new ObjectMapper();
    }

    @RequestMapping(value = "/saveGame", method = RequestMethod.POST)
    public ResponseEntity saveGame(@RequestParam(value = "login") String login,
                                   @RequestParam(value = "pass") String pass,
                                   @RequestBody String body){
        return new ResponseEntity(boardGameService.createBoardGame(
                User.of(login, pass),
                getBoardGameFromBody(body, login)));
    }

    @RequestMapping(value = "/updateGame", method = RequestMethod.POST)
    public ResponseEntity updateGame(@RequestParam(value = "login") String login,
                                     @RequestParam(value = "pass") String pass,
                                     @RequestBody String body) {
        return new ResponseEntity(boardGameService.updateBoardGame(
                User.of(login, pass),
                getBoardGameFromBody(body, login)));
    }

    @RequestMapping(value = "/removeGame", method = RequestMethod.POST)
    public ResponseEntity removeGame(@RequestParam(value = "login") String login,
                                     @RequestParam(value = "pass") String pass,
                                     @RequestBody String body) {
        return new ResponseEntity(boardGameService.removeBoardGame(
                User.of(login, pass),
                getBoardGameFromBody(body, login)));
    }

    @RequestMapping(value = "/userGames", method = RequestMethod.GET)
    public String getAllUserGames(@RequestParam(value = "login") String login,
                                  @RequestParam(value = "pass") String pass){
        return new Gson().toJson(boardGameService.getBoardGames(User.of(login, pass)));
    }

    private BoardGame getBoardGameFromBody(String body, String login) {
        BoardGame boardGame = null;
        try {
            boardGame = objectMapper.readValue(body, BoardGame.class);
            boardGame.setUserLogin(login);
        } catch (IOException e) {
            log.error("Cannot parse body to BoardGame object: {}", e.toString());
        }
        return Optional.ofNullable(boardGame)
                .orElse(BrokenBoardGame.get());
    }
}
