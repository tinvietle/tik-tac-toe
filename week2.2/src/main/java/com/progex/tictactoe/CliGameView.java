package com.progex.tictactoe;

/**
 * CLI implementation of GameView.
 *
 * Preserves 100% of the week1 user experience.
 * In the current CLI wiring, HumanPlayer handles input directly.
 */
public class CliGameView implements GameView {

    public CliGameView() {
    }

    @Override
    public void onGameStart(Board board) {
        board.printReferenceMap();
        board.printState();
    }

    @Override
    public void onMoveMade(Board board, Player player, int position) {
        System.out.println(player.getName() + " chose position " + position + ".");
        board.printState();
    }

    @Override
    public void onPlayerWins(Player winner) {
        System.out.println(winner.getName() + " wins.");
    }

    @Override
    public void onDraw() {
        System.out.println("Game finished (draw). No free cells left.");
    }

    @Override
    public int requestMove(Board board, Player player) {
        throw new UnsupportedOperationException("CLI input is handled by HumanPlayer in this configuration.");
    }
}