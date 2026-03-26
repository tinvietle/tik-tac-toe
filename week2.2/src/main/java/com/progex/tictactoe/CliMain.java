package com.progex.tictactoe;

import java.util.Scanner;

/**
 * Week 1 entry point — CLI version.
 *
 * Uses the same core Game engine and the same Board/Player types.
 * The only week1-specific wiring is:  CliGameView  +  HumanPlayer (scanner).
 */
public class CliMain {

    public static void main(String[] args) {
        if (args.length != 1 || !("1".equals(args[0]) || "2".equals(args[0]))) {
            System.out.println("Usage: java ... com.progex.tictactoe.CliMain <1|2>");
            System.out.println("1 = user first, 2 = computer first");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            GameView view = new CliGameView();

            Board board = new Board();
            Player user     = new HumanPlayer(1, scanner);
            Player computer = new ComputerPlayer(2);

            Player first;
            Player second;
            if ("1".equals(args[0])) {
                first = user;
                second = computer;
            } else {
                first = computer;
                second = user;
            }

            System.out.println("Starting game. " + first.getName() + " moves first.");
            new Game(board, first, second, view).play();
        }
    }
}