package com.tictactoe;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        int firstTurn = parseFirstTurn(args);

        SwingUtilities.invokeLater(() -> {
            Game game = new Game(firstTurn);
            MainFrame frame = new MainFrame(game);
            frame.setVisible(true);

            game.start();
            frame.syncAfterStartupMove();
        });
    }

    private static int parseFirstTurn(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java -cp <jar> com.tictactoe.Main <1|2>");
        }

        int turn;
        try {
            turn = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Turn must be 1 (user first) or 2 (computer first).");
        }

        if (turn != 1 && turn != 2) {
            throw new IllegalArgumentException("Turn must be 1 (user first) or 2 (computer first).");
        }

        return turn;
    }
}
