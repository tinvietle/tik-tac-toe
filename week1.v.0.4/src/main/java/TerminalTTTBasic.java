import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Program entry point.
 */
public class TerminalTTTBasic {

    public static void main(String[] args) {
        PrintStream output = System.out;

        if (args.length != 1 || (!"1".equals(args[0]) && !"2".equals(args[0]))) {
            output.println("Please, input a valid option [1-2]");
            return;
        }

        int startPlayer = Integer.parseInt(args[0]);

        // Keeps explicit output-capture plumbing available for tests.
        ByteArrayOutputStream captureBuffer = new ByteArrayOutputStream();
        PrintStream captureStream = new PrintStream(captureBuffer);
        captureStream.flush();

        Scanner scanner = new Scanner(System.in);
        HumanPlayer humanPlayer = new HumanPlayer(1, scanner, output);
        ComputerPlayer computerPlayer = new ComputerPlayer(2);

        Player firstPlayer;
        Player secondPlayer;

        if (startPlayer == 1) {
            firstPlayer = humanPlayer;
            secondPlayer = computerPlayer;
        } else {
            firstPlayer = computerPlayer;
            secondPlayer = humanPlayer;
        }

        Game game = new Game(new Board(output), firstPlayer, secondPlayer, output);
        game.run();

        scanner.close();
        captureStream.close();
    }
}
