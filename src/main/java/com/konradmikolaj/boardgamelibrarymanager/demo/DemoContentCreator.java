package com.konradmikolaj.boardgamelibrarymanager.demo;

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
        User user1 = new User(1L, "user_1","pass_1", Collections.emptyList());
        User user2 = new User(2L, "user_2","pass_2", Collections.emptyList());
        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void createBoardGames() {
        BoardGame boardGame1 = new BoardGame(1L, 1L, "Osadnicy z Catanu", "Ekonomiczna", "Dom");
        BoardGame boardGame2 = new BoardGame(2L, 1L, "Domek", "Lekka gra rodzinna", "user_1");
        BoardGame boardGame3 = new BoardGame(3L, 2L, "Splendor", "Mózgożerna ekonomia", "Dom");
        BoardGame boardGame4 = new BoardGame(4L, 2L, "Magia i Miecz", "Klasyczny losowy RPG", "user_2");
        BoardGame boardGame5 = new BoardGame(5L, 2L, "Robinson Crusoe", "Kooperacyjna, przetrwanie", "Dom");
        boardGameRepository.save(boardGame1);
        boardGameRepository.save(boardGame2);
        boardGameRepository.save(boardGame3);
        boardGameRepository.save(boardGame4);
        boardGameRepository.save(boardGame5);
    }
}

