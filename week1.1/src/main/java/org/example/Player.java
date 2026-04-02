package org.example;

public abstract class Player {
    private final int playerNumber;

    protected Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public abstract int chooseCell(Board board);
}
