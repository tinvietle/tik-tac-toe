package com.progex.tictactoe;

import java.util.Scanner;

public class HumanPlayer implements Player {
    private final int mark;
    private final Scanner scanner;

    public HumanPlayer(int mark, Scanner scanner) {
        this.mark = mark;
        this.scanner = scanner;
    }

    @Override
    public int getMark() {
        return mark;
    }

    @Override
    public String getName() {
        return "User";
    }

    @Override
    public int chooseMove(Board board) {
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
