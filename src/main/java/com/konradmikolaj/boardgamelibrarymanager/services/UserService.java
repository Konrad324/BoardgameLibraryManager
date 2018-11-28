package com.konradmikolaj.boardgamelibrarymanager.services;

import com.konradmikolaj.boardgamelibrarymanager.model.User;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        if (hasCorrectPassword(user)) {
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

    private boolean hasCorrectPassword(User user) {
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

    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
