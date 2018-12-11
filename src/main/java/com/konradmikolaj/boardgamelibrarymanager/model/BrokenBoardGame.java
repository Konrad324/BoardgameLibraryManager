package com.konradmikolaj.boardgamelibrarymanager.model;

public final class BrokenBoardGame extends BoardGame {

    public static BoardGame get() {
        return new BrokenBoardGame();
    }
}
