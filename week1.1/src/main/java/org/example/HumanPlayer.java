package org.example;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner scanner;

    public HumanPlayer(int playerNumber, Scanner scanner) {
        super(playerNumber);
        this.scanner = scanner;
    }

    @Override
    public int chooseCell(Board board) {
        while (true) {
            String input = scanner.nextLine();
            int chosenCell;

            try {
                chosenCell = Integer.parseInt(input.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please, input a valid number [1-9]");
                System.out.println("Player#" + getPlayerNumber() + "'s turn");
                continue;
            }

            if (!board.isCellInRange(chosenCell)) {
                System.out.println("Please, input a valid number [1-9]");
                System.out.println("Player#" + getPlayerNumber() + "'s turn");
                continue;
            }

            if (!board.isCellFree(chosenCell)) {
                System.out.println("The cell is occupied!");
                System.out.println("Player#" + getPlayerNumber() + "'s turn");
                continue;
            }

            return chosenCell;
        }
    }
}
