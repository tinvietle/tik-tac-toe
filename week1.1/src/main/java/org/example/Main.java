package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || (!"1".equals(args[0]) && !"2".equals(args[0]))) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }

        int startingPlayer = Integer.parseInt(args[0]);
        Scanner scanner = new Scanner(System.in);

        Player humanPlayer = new HumanPlayer(1, scanner);
        Player computerPlayer = new ComputerPlayer(2);

        Player firstPlayer = (startingPlayer == 1) ? humanPlayer : computerPlayer;
        Player secondPlayer = (startingPlayer == 1) ? computerPlayer : humanPlayer;

        Board board = new Board();
        Game game = new Game(board, firstPlayer, secondPlayer);
        game.play();
    }
}
