package com.progex.tictactoe;

import java.util.Scanner;

/**
 * CLI implementation of GameView.
 *
 * Preserves 100% of the week1 user experience.
 * The Human player's input is read here via Scanner.
 *
 * Note: HumanPlayer.chooseMove() is bypassed for human turns when the view
 * owns input (the game calls view.requestMove for human players).
 * For a clean separation, SwingHumanPlayer and CliHumanPlayer both delegate
 * input to the view.
 */
public class CliGameView implements GameView {

    private final Scanner scanner;

    public CliGameView(Scanner scanner) {
        this.scanner = scanner;
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
        while (true) {
            System.out.print("Enter your move (1-9): ");
            String input = scanner.nextLine();
            int position;
            try {
                position = Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.");
                continue;
            }
            if (!board.isValidPosition(position)) {
                System.out.println("Position out of range. Please choose from 1 to 9.");
                continue;
            }
            if (!board.isFree(position)) {
                System.out.println("Position already used. Choose another one.");
                continue;
            }
            return position;
        }
    }
}