package org.example;

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
        Player currentPlayer = firstPlayer;

        System.out.println("Hello!");
        System.out.println(board.render());

        while (true) {
            System.out.println("Player#" + currentPlayer.getPlayerNumber() + "'s turn");
            int chosenCell = currentPlayer.chooseCell(board);
            board.placeMove(chosenCell, currentPlayer.getPlayerNumber());

            System.out.println(board.render());

            if (board.hasWinner(currentPlayer.getPlayerNumber())) {
                System.out.println("Player#" + currentPlayer.getPlayerNumber() + " won!");
                return;
            }

            if (!board.hasFreeCells()) {
                System.out.println("It is a draw!");
                return;
            }

            currentPlayer = (currentPlayer == firstPlayer) ? secondPlayer : firstPlayer;
        }
    }
}
