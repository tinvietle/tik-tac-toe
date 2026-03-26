package com.progex.tictactoe;

/**
 * GameView is the single abstraction boundary between the game engine and any UI.
 *
 * Open-Closed Principle:
 *   - The Game engine is CLOSED for modification.
 *   - Every new UI (CLI, Swing, Web, Android…) is an OPEN extension via this interface.
 *
 * To add a new UI next week:
 *   1. Implement this interface.
 *   2. Wire it in a new Main.
 *   3. Zero changes to Game, Board, or any Player.
 */
public interface GameView {

    /** Called once at the start — show reference map, welcome message, etc. */
    void onGameStart(Board board);

    /**
     * Called after each move is placed.
     *
     * @param board    current board state
     * @param player   the player who just moved
     * @param position the cell they chose (1-9)
     */
    void onMoveMade(Board board, Player player, int position);

    /**
     * Called when a player wins.
     *
     * @param winner the winning player
     */
    void onPlayerWins(Player winner);

    /** Called when the board is full and nobody won. */
    void onDraw();

    /**
     * Ask the current human player for their next move.
     * For Swing: this may block on a future/semaphore until the user clicks.
     * For CLI: this reads from stdin.
     *
     * @param board  current board state (for validation hints in the view)
     * @param player the player whose turn it is
     * @return chosen position (1-9), guaranteed valid by the view implementation
     */
    int requestMove(Board board, Player player);
}