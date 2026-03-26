package com.progex.tictactoe;

/**
 * Game engine — pure logic, zero UI knowledge.
 *
 * Depends ONLY on the GameView interface.
 * Works identically whether the view is CLI, Swing, Web, or a test mock.
 *
 * This class is CLOSED for modification but OPEN for extension via GameView.
 */
public class Game {
    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final GameView view;

    public Game(Board board, Player firstPlayer, Player secondPlayer, GameView view) {
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.view = view;
    }

    public void play() {
        view.onGameStart(board);

        Player current = firstPlayer;

        while (board.hasFreeCell()) {
            int move = current.chooseMove(board);   // AI players decide themselves
            board.placeMark(move, current.getMark());
            view.onMoveMade(board, current, move);

            if (board.hasWinner(current.getMark())) {
                view.onPlayerWins(current);
                return;
            }

            if (!board.hasFreeCell()) {
                view.onDraw();
                return;
            }

            current = (current == firstPlayer) ? secondPlayer : firstPlayer;
        }

        view.onDraw();
    }
}