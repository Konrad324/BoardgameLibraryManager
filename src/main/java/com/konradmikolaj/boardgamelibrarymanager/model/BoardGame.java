package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "boardgame")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardGame {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String userLogin;

    @NonNull
    private String title;

    private String description;

    private String localization;


    public static BoardGame of(String userLogin, String title, String description, String localization) {
        return BoardGame.builder().userLogin(userLogin).title(title).description(description).localization(localization).build();
    }

    public boolean isBroken() {
        return this instanceof BrokenBoardGame;
    }

}
