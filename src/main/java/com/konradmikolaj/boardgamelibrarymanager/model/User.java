package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String login;

    //By default - user fetched from database has encoded password, user created from request has decoded password
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardgame_id")
    private List<BoardGame> boardGames;

    public static User of(String login, String password) {
        return new User(login, password, new ArrayList<>());
    }

}
