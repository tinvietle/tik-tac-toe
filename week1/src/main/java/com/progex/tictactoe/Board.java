package com.progex.tictactoe;

import java.util.Arrays;

public class Board {
    private final int[] cells;
    private static final int[][] WIN_LINES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    }; // Fixed winning lines for 3x3 tic-tac-toe

    public Board() {
        this.cells = new int[9];
        Arrays.fill(this.cells, 0);
    }

    public boolean isValidPosition(int position) {
        return position >= 1 && position <= 9;
    }

    public boolean isFree(int position) {
        return cells[position - 1] == 0;
    }

    public boolean placeMark(int position, int mark) {
        if (!isValidPosition(position) || !isFree(position)) {
            return false;
        }
        cells[position - 1] = mark;
        return true;
    }

    public boolean hasFreeCell() {
        for (int cell : cells) {
            if (cell == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasWinner(int mark) {
        for (int[] line : WIN_LINES) {
            if (cells[line[0]] == mark && cells[line[1]] == mark && cells[line[2]] == mark) {
                return true;
            }
        }
        return false;
    }

    public void printReferenceMap() {
        System.out.println("Map:");
        System.out.println("|1|2|3|");
        System.out.println("|4|5|6|");
        System.out.println("|7|8|9|");
    }

    public void printState() {
        System.out.println("Current board:");
        for (int i = 0; i < cells.length; i += 3) {
            System.out.println("|" + cells[i] + "|" + cells[i + 1] + "|" + cells[i + 2] + "|");
        }
    }
}
