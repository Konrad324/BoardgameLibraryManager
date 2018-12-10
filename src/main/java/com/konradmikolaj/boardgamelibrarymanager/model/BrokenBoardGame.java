package com.konradmikolaj.boardgamelibrarymanager.model;

public final class BrokenBoardGame extends BoardGame {

    private BrokenBoardGame() {
    }

    public static BoardGame get() {
        return new BrokenBoardGame();
    }
}
