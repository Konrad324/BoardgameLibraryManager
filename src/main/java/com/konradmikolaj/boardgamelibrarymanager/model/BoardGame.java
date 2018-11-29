package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "boardgame")
@Data
@NoArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue
    private Long id;

    private String userLogin;

    private String title;

    private String description;

    private String localization;

    public BoardGame(String userLogin, String title, String description, String localization) {
        this.userLogin = userLogin;
        this.title = title;
        this.description = description;
        this.localization = localization;
    }

    public static BoardGame of(String userLogin, String title, String description, String localization) {
        return new BoardGame(userLogin, title, description, localization);
    }

    public Boolean isNoPermission() {
        return this instanceof NoPermissionBoardGame;
    }
}
