package com.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] cells;

    public Board() {
        this.cells = new int[3][3];
    }

    public boolean placeMove(int position, int marker) {
        if (position < 1 || position > 9) {
            return false;
        }

        int row = (position - 1) / 3;
        int col = (position - 1) % 3;

        if (cells[row][col] != 0) {
            return false;
        }

        cells[row][col] = marker;
        return true;
    }

    public boolean isPositionFree(int position) {
        if (position < 1 || position > 9) {
            return false;
        }

        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        return cells[row][col] == 0;
    }

    public int getCellValue(int position) {
        if (position < 1 || position > 9) {
            throw new IllegalArgumentException("Position must be between 1 and 9.");
        }

        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        return cells[row][col];
    }

    public boolean hasWinner(int marker) {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0] == marker && cells[i][1] == marker && cells[i][2] == marker) {
                return true;
            }
            if (cells[0][i] == marker && cells[1][i] == marker && cells[2][i] == marker) {
                return true;
            }
        }

        return (cells[0][0] == marker && cells[1][1] == marker && cells[2][2] == marker)
                || (cells[0][2] == marker && cells[1][1] == marker && cells[2][0] == marker);
    }

    public boolean isFull() {
        for (int i = 1; i <= 9; i++) {
            if (isPositionFree(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getFreePositions() {
        List<Integer> freePositions = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (isPositionFree(i)) {
                freePositions.add(i);
            }
        }
        return freePositions;
    }
}
