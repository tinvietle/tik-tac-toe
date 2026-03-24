package com.progex.tictactoe;

public class Game {
    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(Board board, Player firstPlayer, Player secondPlayer) {
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void play() {
        board.printReferenceMap();
        board.printState();

        Player current = firstPlayer;
        while (board.hasFreeCell()) {
            int move = current.chooseMove(board);
            board.placeMark(move, current.getMark());
            System.out.println(current.getName() + " chose position " + move + ".");
            board.printState();

            if (board.hasWinner(current.getMark())) {
                System.out.println(current.getName() + " wins.");
                return;
            }

            if (!board.hasFreeCell()) {
                System.out.println("Game finished (draw). No free cells left.");
                return;
            }

            current = (current == firstPlayer) ? secondPlayer : firstPlayer;
        }
    }
}
