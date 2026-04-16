import java.io.PrintStream;

/**
 * Runs the Tic-Tac-Toe game loop.
 * The players are already resolved in main based on startup argument.
 */
public class Game {

    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final PrintStream output;

    public Game(Board board, Player firstPlayer, Player secondPlayer) {
        this(board, firstPlayer, secondPlayer, System.out);
    }

    public Game(Board board, Player firstPlayer, Player secondPlayer, PrintStream output) {
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.output = output;
    }

    public void run() {
        output.println("Hello!");
        board.display();

        Player[] turnOrder = {firstPlayer, secondPlayer};
        int current = 0;

        while (true) {
            Player activePlayer = turnOrder[current];
            output.println("Player#" + activePlayer.getPlayerNumber() + "'s turn");

            boolean shouldContinue = activePlayer.takeTurn(board);
            if (!shouldContinue) {
                return;
            }

            board.display();

            if (board.hasWon(activePlayer.getPlayerNumber())) {
                output.println("Player#" + activePlayer.getPlayerNumber() + " won!");
                return;
            }

            if (board.isFull()) {
                output.println("It is a draw!");
                return;
            }

            current = 1 - current;
        }
    }
}
