package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import com.konradmikolaj.boardgamelibrarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class DemoContentCreator {

    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;

    @Autowired
    public DemoContentCreator(UserRepository userRepository, BoardGameRepository boardGameRepository) {
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
    }

    public void prepare() {
        createUsers();
        createBoardGames();
    }

    private void createUsers() {
        User user1 = new User("user_1","pass_1", Collections.emptyList());
        User user2 = new User("user_2","pass_2", Collections.emptyList());
        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void createBoardGames() {
        BoardGame boardGame1 = new BoardGame("user_1", "Osadnicy z Catanu", "Ekonomiczna", "Dom");
        BoardGame boardGame2 = new BoardGame("user_1", "Domek", "Lekka gra rodzinna", "user_1");
        BoardGame boardGame3 = new BoardGame("user_2", "Splendor", "Mózgożerna ekonomia", "Dom");
        BoardGame boardGame4 = new BoardGame("user_2", "Magia i Miecz", "Klasyczny losowy RPG", "user_2");
        BoardGame boardGame5 = new BoardGame("user_2", "Robinson Crusoe", "Kooperacyjna, przetrwanie", "Dom");
        boardGameRepository.save(boardGame1);
        boardGameRepository.save(boardGame2);
        boardGameRepository.save(boardGame3);
        boardGameRepository.save(boardGame4);
        boardGameRepository.save(boardGame5);
    }
}

