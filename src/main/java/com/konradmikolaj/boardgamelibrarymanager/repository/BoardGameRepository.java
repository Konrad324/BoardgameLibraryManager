package com.konradmikolaj.boardgamelibrarymanager.repository;

import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    List<BoardGame> findBoardGamesByUserLogin(String userId);

    Boolean existsBoardGameByUserLoginAndTitle(String login, String title);

    BoardGame getBoardGameByUserLoginAndTitle(String login, String title);
}
