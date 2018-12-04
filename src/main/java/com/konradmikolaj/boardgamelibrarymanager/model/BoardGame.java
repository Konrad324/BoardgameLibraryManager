package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String userLogin;

    private String title;

    private String description;

    private String localization;

    public static BoardGame of(Long id, String userLogin) {
        return BoardGame.builder().id(id).userLogin(userLogin).build();
    }

    public static BoardGame of(String userLogin, String title, String description, String localization) {
        return BoardGame.builder().userLogin(userLogin).title(title).description(description).localization(localization).build();
    }

    public static BoardGame of(Long id, String userLogin, String title, String description, String localization) {
        return BoardGame.builder().id(id).userLogin(userLogin).title(title).description(description).localization(localization).build();
    }

}
