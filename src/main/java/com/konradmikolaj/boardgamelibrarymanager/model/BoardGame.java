package com.konradmikolaj.boardgamelibrarymanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "boardgame")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardGame {

    @Id
    private Long id;

    private Long userId;

    private String title;

    private String description;

    private String localization;
}
