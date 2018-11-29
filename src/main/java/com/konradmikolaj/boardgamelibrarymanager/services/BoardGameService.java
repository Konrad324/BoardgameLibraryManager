package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.BoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.NoPermissionBoardGame;
import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import com.konradmikolaj.boardgamelibrarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGameService {

    private final UserService userService;

    private final BoardGameRepository boardGameRepository;

    @Autowired
    public BoardGameService(UserService userService, BoardGameRepository boardGameRepository) {
        this.userService = userService;
        this.boardGameRepository = boardGameRepository;
    }

    public HttpStatus createBoardGame(User user, BoardGame boardGame) {
        if (hasPermissions(user) && !isExistingBoardGame(boardGame)) {
            boardGameRepository.save(boardGame);
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    public HttpStatus removeBoardGame(User user, BoardGame boardGame) {
        if (hasPermissions(user)) {
            if (isExistingBoardGame(boardGame)) {
                boardGameRepository.delete(boardGame);
                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    public HttpStatus updateBoardGame(User user, BoardGame boardGame) {
        if (hasPermissions(user)) {
            if (isExistingBoardGame(boardGame)) {
                BoardGame boardGameToUpdate = boardGameRepository.getBoardGameById(boardGame.getId());
                boardGameToUpdate.setTitle(boardGame.getTitle());
                boardGameToUpdate.setDescription(boardGame.getDescription());
                boardGameToUpdate.setLocalization(boardGame.getLocalization());
                boardGameRepository.saveAndFlush(boardGameToUpdate);
                return HttpStatus.OK;
            } else {
                return HttpStatus.NOT_FOUND;
            }
        }
        return HttpStatus.FORBIDDEN;
    }

    public List<BoardGame> getBoardGames(User user) {
        if (hasPermissions(user)) {
            return boardGameRepository.findBoardGamesByUserLogin(user.getLogin());
        } else {
            return NoPermissionBoardGame.getAsList();
        }
    }

    private boolean isExistingBoardGame(BoardGame boardGame) {
        return boardGameRepository.existsBoardGameById(boardGame.getId());
    }

    private boolean hasPermissions(User user) {
        return userService.hasPermissions(user);
    }
}
