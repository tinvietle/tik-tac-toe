package vgu.pe2026.ttt.basic;

/**
 * Base class for all players.
 */
public abstract class Player {

    protected final int playerNumber;

    protected Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Executes one player turn.
     *
     * @return false when the game should terminate, true otherwise.
     */
    public abstract boolean takeTurn(Board board);
}
