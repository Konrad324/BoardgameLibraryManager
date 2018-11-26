package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id;

    private String login;

    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardgame_id")
    private List<BoardGame> boardGames;

}
