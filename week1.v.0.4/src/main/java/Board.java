import java.io.PrintStream;

/**
 * Represents a 3x3 Tic-Tac-Toe board.
 * Cell values: 0 = empty, 1 = Player#1, 2 = Player#2.
 */
public class Board {

    private final int[] cells;
    private final PrintStream output;

    private static final int[][] WIN_LINES = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    public Board() {
        this(System.out);
    }

    public Board(PrintStream output) {
        this.cells = new int[9];
        this.output = output;
    }

    public boolean isCellFree(int cellNumber) {
        return cells[cellNumber - 1] == 0;
    }

    public void place(int cellNumber, int playerMark) {
        cells[cellNumber - 1] = playerMark;
    }

    public boolean hasWon(int playerMark) {
        for (int[] line : WIN_LINES) {
            if (cells[line[0]] == playerMark
                    && cells[line[1]] == playerMark
                    && cells[line[2]] == playerMark) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (int cell : cells) {
            if (cell == 0) {
                return false;
            }
        }
        return true;
    }

    public int firstFreeCell() {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == 0) {
                return i + 1;
            }
        }
        return -1;
    }

    public void display() {
        output.println("| " + cells[0] + " | " + cells[1] + " | " + cells[2] + " |");
        output.println("| " + cells[3] + " | " + cells[4] + " | " + cells[5] + " |");
        output.println("| " + cells[6] + " | " + cells[7] + " | " + cells[8] + " |");
    }
}
