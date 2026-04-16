import java.io.PrintStream;
import java.util.Scanner;

/**
 * Human player that reads moves from standard input.
 */
public class HumanPlayer extends Player {

    private final Scanner scanner;
    private final PrintStream output;

    public HumanPlayer(int playerNumber, Scanner scanner) {
        this(playerNumber, scanner, System.out);
    }

    public HumanPlayer(int playerNumber, Scanner scanner, PrintStream output) {
        super(playerNumber);
        this.scanner = scanner;
        this.output = output;
    }

    @Override
    public boolean takeTurn(Board board) {
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("q")) {
                output.println("End of the game");
                return false;
            }

            int cellNumber;
            try {
                cellNumber = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                output.println("Please, input a valid number [1-9]");
                output.println("Player#" + playerNumber + "'s turn");
                continue;
            }

            if (cellNumber < 1 || cellNumber > 9) {
                output.println("Please, input a valid number [1-9]");
                output.println("Player#" + playerNumber + "'s turn");
                continue;
            }

            if (!board.isCellFree(cellNumber)) {
                output.println("The cell is occupied!");
                output.println("Player#" + playerNumber + "'s turn");
                continue;
            }

            board.place(cellNumber, playerNumber);
            return true;
        }
    }
}
