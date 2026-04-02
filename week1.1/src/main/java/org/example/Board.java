package org.example;

public class Board {
    private static final int SIZE = 9;
    private final int[] cells;

    public Board() {
        this.cells = new int[SIZE];
    }

    public boolean isCellInRange(int cell) {
        return cell >= 1 && cell <= SIZE;
    }

    public boolean isCellFree(int cell) {
        return cells[cell - 1] == 0;
    }

    public void placeMove(int cell, int playerNumber) {
        cells[cell - 1] = playerNumber;
    }

    public boolean hasWinner(int playerNumber) {
        int[][] lines = {
            {1, 2, 3}, {4, 5, 6}, {7, 8, 9},
            {1, 4, 7}, {2, 5, 8}, {3, 6, 9},
            {1, 5, 9}, {3, 5, 7}
        };

        for (int[] line : lines) {
            if (cellValue(line[0]) == playerNumber
                && cellValue(line[1]) == playerNumber
                && cellValue(line[2]) == playerNumber) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFreeCells() {
        for (int cell : cells) {
            if (cell == 0) {
                return true;
            }
        }
        return false;
    }

    public int firstFreeCell() {
        for (int i = 0; i < SIZE; i++) {
            if (cells[i] == 0) {
                return i + 1;
            }
        }
        return -1;
    }

    public String render() {
        String lineSeparator = System.lineSeparator();
        return "| " + cells[0] + " | " + cells[1] + " | " + cells[2] + " |" + lineSeparator
            + "| " + cells[3] + " | " + cells[4] + " | " + cells[5] + " |" + lineSeparator
            + "| " + cells[6] + " | " + cells[7] + " | " + cells[8] + " |";
    }

    private int cellValue(int cell) {
        return cells[cell - 1];
    }
}
