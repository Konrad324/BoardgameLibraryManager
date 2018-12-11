package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import com.konradmikolaj.boardgamelibrarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DemoContentCreator {

    public static final String USER_1 = "user_1";
    public static final String USER_2 = "user_2";
    public static final String USER_3 = "user_3";

    public static final String PASS_1 = "pass_1";
    public static final String PASS_2 = "pass_2";
    public static final String PASS_3 = "pass_3";

    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;

    @Autowired
    public DemoContentCreator(UserRepository userRepository, BoardGameRepository boardGameRepository) {
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
    }

    public void prepare() {
        cleanDatabase();
        createUsers();
        createBoardGames();
    }

    public void cleanDatabase() {
        userRepository.deleteAll();
        boardGameRepository.deleteAll();
    }

    public void createUsers() {
        User user1 = User.of(USER_1, UserService.encodePassword(PASS_1));
        User user2 = User.of(USER_2, UserService.encodePassword(PASS_2));
        User user3 = User.of(USER_3, UserService.encodePassword(PASS_3));
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private void createBoardGames() {
        BoardGame boardGame1 = BoardGame.of(USER_1, "Osadnicy z Catanu", "Ekonomiczna", "Dom");
        BoardGame boardGame2 = BoardGame.of(USER_1, "Domek", "Lekka gra rodzinna", USER_1);
        BoardGame boardGame3 = BoardGame.of(USER_2, "Splendor", "Mózgożerna ekonomia", "Dom");
        BoardGame boardGame4 = BoardGame.of(USER_2, "Magia i Miecz", "Klasyczny losowy RPG", USER_2);
        BoardGame boardGame5 = BoardGame.of(USER_2, "Robinson Crusoe", "Kooperacyjna, przetrwanie", "Dom");
        boardGameRepository.save(boardGame1);
        boardGameRepository.save(boardGame2);
        boardGameRepository.save(boardGame3);
        boardGameRepository.save(boardGame4);
        boardGameRepository.save(boardGame5);
    }
}

