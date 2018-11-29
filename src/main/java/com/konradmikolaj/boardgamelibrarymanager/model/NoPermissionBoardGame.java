package com.konradmikolaj.boardgamelibrarymanager.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class NoPermissionBoardGame extends BoardGame {
    //Return if user has no permission to read board game

    public static BoardGame get() {
        return new NoPermissionBoardGame();
    }

    public static List<BoardGame> getAsList() {
        return ImmutableList.of(get());
    }
}
