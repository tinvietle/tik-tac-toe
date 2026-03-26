package com.progex.tictactoe;

/**
 * Week 2 entry point — Swing UI version.
 *
 * Lines changed vs week1/Main.java:
 *   - Replace  CliGameView   →  SwingGameView      (1 line)
 *   - Replace  HumanPlayer   →  SwingHumanPlayer   (1 line)
 *   - Replace  Scanner usage →  nothing            (remove 2 lines)
 *   - Use core/Game instead of week1/Game          (1 line)
 *
 * Everything else (Board, ComputerPlayer, Player, Game logic) is untouched.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 1 || (args.length == 1 && !("1".equals(args[0]) || "2".equals(args[0])))) {
            System.out.println("Usage: java ... com.progex.tictactoe.Main <1|2>");
            System.out.println("1 = user first, 2 = computer first");
            return;
        }

        GameView view = new SwingGameView();

        Board board = new Board();
        Player human    = new SwingHumanPlayer(1, view);
        Player computer = new ComputerPlayer(2);

        Player first;
        Player second;
        if (args.length == 0 || "1".equals(args[0])) {
            first = human;
            second = computer;
        } else {
            first = computer;
            second = human;
        }

        Game game = new Game(board, first, second, view);

        // Run the game loop on a background thread so Swing's EDT stays free
        Thread gameThread = new Thread(() -> game.play(), "game-engine");
        gameThread.start();
    }
}