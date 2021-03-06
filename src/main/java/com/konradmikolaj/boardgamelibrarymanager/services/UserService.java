package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
import com.konradmikolaj.boardgamelibrarymanager.repository.BoardGameRepository;
import com.konradmikolaj.boardgamelibrarymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BoardGameRepository boardGameRepository;

    @Autowired
    public UserService(UserRepository userRepository, BoardGameRepository boardGameRepository) {
        this.userRepository = userRepository;
        this.boardGameRepository = boardGameRepository;
    }

    public HttpStatus createUser(User user) {
        if (isExistingUser(user)) {
            return HttpStatus.FORBIDDEN;
        } else {
            user.setPassword(encodePassword(user.getPassword()));
            userRepository.save(user);
            return HttpStatus.OK;
        }
    }

    public HttpStatus removeUser(User user) {
        if (hasPermissions(user)) {
            boardGameRepository.deleteBoardGamesByUserLogin(user.getLogin());
            userRepository.deleteById(user.getLogin());
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    public List<String> getAllUsers(){
        return userRepository.findAll().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    public boolean hasPermissions(User user) {
        if (isExistingUser(user)) {
            String pass64 = encodePassword(user.getPassword());
            User userFromDb = userRepository.findByLogin(user.getLogin());
            return pass64.equals(userFromDb.getPassword());
        }
        return false;
    }

    private boolean isExistingUser(User user) {
        return userRepository.existsById(user.getLogin());
    }

    public static String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
