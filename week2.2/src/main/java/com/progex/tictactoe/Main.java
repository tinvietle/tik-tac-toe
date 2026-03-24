package com.progex.tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !("1".equals(args[0]) || "2".equals(args[0]))) {
            System.out.println("Usage: java -cp <classpath> com.progex.tictactoe.Main <1|2>");
            System.out.println("1 = user first, 2 = computer first");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Board board = new Board();
            Player user = new HumanPlayer(1, scanner);
            Player computer = new ComputerPlayer(2);

            Player firstPlayer = "1".equals(args[0]) ? user : computer;
            Player secondPlayer = "1".equals(args[0]) ? computer : user;

            System.out.println("Starting game. " + firstPlayer.getName() + " moves first.");
            Game game = new Game(board, firstPlayer, secondPlayer);
            game.play();
        }
    }
}
