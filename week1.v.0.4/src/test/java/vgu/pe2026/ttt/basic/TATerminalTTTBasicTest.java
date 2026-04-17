package vgu.pe2026.ttt.basic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TATerminalTTTBasicTest {

    private static final String INVALID_OPTION_MSG = "Please, input a valid option [1-2]";
    private static final String INVALID_NUMBER_MSG = "Please, input a valid number [1-9]";
    private static final String OCCUPIED_MSG = "The cell is occupied!";
    private static final String END_MSG = "End of the game";
    private static final String HUMAN_WIN_MSG = "Player#1 won!";
    private static final String COMPUTER_WIN_MSG = "Player#2 won!";
    private static final String DRAW_MSG = "It is a draw!";

    private PrintStream originalOut;
    private PrintStream originalErr;
    private java.io.InputStream originalIn;

    @Before
    public void setUp() {
        originalOut = System.out;
        originalErr = System.err;
        originalIn = System.in;
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void testStartGameWithHumanFirst() {
        String output = runGame(new String[] {"1"}, "q");

        assertTrue(output.contains("Hello!"));
        assertTrue(output.contains("Player#1's turn"));

        List<int[]> boards = extractBoards(output);
        assertFalse("Expected initial board snapshot.", boards.isEmpty());
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}, boards.get(0));
    }

    @Test
    public void testStartGameWithComputerFirst() {
        String output = runGame(new String[] {"2"}, "q");

        assertTrue(output.contains("Hello!"));
        assertTrue(output.contains("Player#2's turn"));

        List<int[]> boards = extractBoards(output);
        assertFalse("Expected initial board snapshot.", boards.isEmpty());
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}, boards.get(0));
    }

    @Test
    public void testRejectMissingStartupArgument() {
        String output = runGame(new String[] {});
        assertEquals(INVALID_OPTION_MSG, output.trim());
    }

    @Test
    public void testRejectInvalidStartupArgumentValues() {
        for (String invalidArg : Arrays.asList("3", "0", "-1", "abc")) {
            String output = runGame(new String[] {invalidArg});
            assertEquals("Unexpected output for arg=" + invalidArg, INVALID_OPTION_MSG, output.trim());
        }
    }

    @Ignore("Requirement wording is ambiguous for strict parsing of 01 and extra args.")
    @Test
    public void testStartupArgumentStrictnessAmbiguity() {
        String outputFor01 = runGame(new String[] {"01"}, "q");
        String outputForExtraArg = runGame(new String[] {"1", "2"}, "q");

        assertTrue(
                outputFor01.contains(INVALID_OPTION_MSG) || outputFor01.contains("Hello!"));
        assertTrue(
                outputForExtraArg.contains(INVALID_OPTION_MSG) || outputForExtraArg.contains("Hello!"));
    }

    @Test
    public void testBoardRendersAsStateValuesOnly() {
        String output = runGame(new String[] {"1"}, "5", "q");
        List<int[]> boards = extractBoards(output);

        assertFalse("Expected at least one board snapshot.", boards.isEmpty());
        assertFalse("Board must not show reference numbering table.", output.contains("| 1 | 2 | 3 |"));

        for (int[] board : boards) {
            for (int cell : board) {
                assertTrue("Board must contain only 0,1,2 values.", cell >= 0 && cell <= 2);
            }
        }
    }

    @Test
    public void testAcceptValidHumanMoveAndUpdateBoard() {
        String output = runGame(new String[] {"1"}, "5", "q");
        List<int[]> boards = extractBoards(output);

        assertTrue("Expected board state with human move in cell 5.", hasBoardWithCellValue(boards, 5, 1));
    }

    @Test
    public void testHandleNonIntegerInputAsInvalid() {
        String output = runGame(new String[] {"1"}, "abc", "@", "", "q");

        assertTrue(output.contains(INVALID_NUMBER_MSG));
        assertTrue("Expected invalid number message at least 3 times.",
                countOccurrences(output, INVALID_NUMBER_MSG) >= 3);
        assertTrue("Expected turn prompt after invalid inputs.",
                countOccurrences(output, "Player#1's turn") >= 2);
    }

    @Test
    public void testQuitGameWithLowercaseQ() {
        String output = runGame(new String[] {"1"}, "q");

        assertTrue(output.contains(END_MSG));
        assertFalse(output.contains(HUMAN_WIN_MSG));
        assertFalse(output.contains(COMPUTER_WIN_MSG));
        assertFalse(output.contains(DRAW_MSG));
    }

    @Test
    public void testQCaseSensitivityAndWhitespaceVariants() {
        String output = runGame(new String[] {"1"}, "Q", " q", "q ", "q");

        assertTrue("Non-exact q variants should be invalid.", countOccurrences(output, INVALID_NUMBER_MSG) >= 3);
        assertTrue("Expected game termination with exact q.", output.contains(END_MSG));
    }

    @Test
    public void testRejectIntegerOutsideRangeOneToNine() {
        String output = runGame(new String[] {"1"}, "0", "10", "-3", "q");

        assertTrue("Expected invalid number message at least 3 times.",
                countOccurrences(output, INVALID_NUMBER_MSG) >= 3);
    }

    @Test
    public void testRejectMoveToOccupiedCell() {
        String output = runGame(new String[] {"1"}, "1", "1", "q");

        assertTrue(output.contains(OCCUPIED_MSG));
        assertTrue("Expected turn prompt after occupied-cell attempt.",
                countOccurrences(output, "Player#1's turn") >= 2);
    }

    @Test
    public void testHumanWinDetectionRow() {
        String output = runGame(new String[] {"1"}, "4", "5", "6");
        assertTrue(output.contains(HUMAN_WIN_MSG));
    }

    @Test
    public void testHumanWinDetectionColumn() {
        String output = runGame(new String[] {"1"}, "3", "6", "9");
        assertTrue(output.contains(HUMAN_WIN_MSG));
    }

    @Test
    public void testHumanWinDetectionDiagonal() {
        String output = runGame(new String[] {"1"}, "3", "5", "7");
        assertTrue(output.contains(HUMAN_WIN_MSG));
    }

    @Test
    public void testComputerWinDetection() {
        String output = runGame(new String[] {"1"}, "9", "6", "8");
        assertTrue(output.contains(COMPUTER_WIN_MSG));
    }

    @Test
    public void testDrawDetectionWhenBoardFullAfterHumanMove() {
        String output = runGame(new String[] {"1"}, "2", "5", "6", "7", "9");
        assertTrue(output.contains(DRAW_MSG));
    }

    @Test
    public void testDrawDetectionWhenBoardFullAfterComputerMove() {
        String output = runGame(new String[] {"2"}, "2", "5", "7", "9");
        assertTrue(output.contains(DRAW_MSG));
    }

    @Test
    public void testComputerChoosesFirstAvailableCell() {
        String output = runGame(new String[] {"2"}, "2", "5", "q");
        List<int[]> boards = extractBoards(output);

        assertTrue("Expected board with first computer move at cell 1.", hasBoardWithCellValue(boards, 1, 2));
        assertTrue("Expected board with next computer move at cell 3.", hasBoardWithCellValue(boards, 3, 2));
        assertTrue("Expected board with next computer move at cell 4.", hasBoardWithCellValue(boards, 4, 2));
    }

    @Test
    public void testBoardIntegrityAfterEachMove() {
        String output = runGame(new String[] {"1"}, "2", "5", "q");
        List<int[]> boards = extractBoards(output);

        assertFalse("Expected multiple board snapshots.", boards.isEmpty());

        assertTrue("Initial empty board must exist.",
                containsBoard(boards, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}));
        assertTrue("Board after first human move (cell 2) must exist.",
                containsBoard(boards, new int[] {0, 1, 0, 0, 0, 0, 0, 0, 0}));
        assertTrue("Board after first computer move (cell 1) must exist.",
                containsBoard(boards, new int[] {2, 1, 0, 0, 0, 0, 0, 0, 0}));
        assertTrue("Board after second human move (cell 5) must exist.",
                containsBoard(boards, new int[] {2, 1, 0, 0, 1, 0, 0, 0, 0}));
        assertTrue("Board after second computer move (cell 3) must exist.",
                containsBoard(boards, new int[] {2, 1, 2, 0, 1, 0, 0, 0, 0}));
    }

    @Test
    public void testTurnPromptSequenceCorrectness() {
        String output = runGame(new String[] {"1"}, "abc", "2", "2", "5", "q");

        assertTrue("Expected turn prompt to reappear after invalid and occupied inputs.",
                countOccurrences(output, "Player#1's turn") >= 4);
        assertTrue(output.contains(INVALID_NUMBER_MSG));
        assertTrue(output.contains(OCCUPIED_MSG));
    }

    @Test
    public void testProgramTerminationBehaviorForFinalStates() {
        List<String> outputs = Arrays.asList(
                runGame(new String[] {"1"}, "3", "5", "7"),
                runGame(new String[] {"1"}, "9", "6", "8"),
                runGame(new String[] {"1"}, "2", "5", "6", "7", "9"),
                runGame(new String[] {"1"}, "q"));

        assertTrue(outputs.get(0).contains(HUMAN_WIN_MSG));
        assertTrue(outputs.get(1).contains(COMPUTER_WIN_MSG));
        assertTrue(outputs.get(2).contains(DRAW_MSG));
        assertTrue(outputs.get(3).contains(END_MSG));

        for (String output : outputs) {
            assertFalse("Program should not print stack traces on terminal end states.",
                    output.toLowerCase().contains("exception"));
        }
    }

    @Test
    public void testInputRobustnessUnderRapidInvalidRetries() {
        List<String> inputLines = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            inputLines.add("x");
            inputLines.add("!");
            inputLines.add("999");
        }
        inputLines.add("5");
        inputLines.add("q");

        String output = runGame(new String[] {"1"}, inputLines.toArray(new String[0]));

        assertTrue("Expected many invalid-number responses.",
                countOccurrences(output, INVALID_NUMBER_MSG) >= 30);

        List<int[]> boards = extractBoards(output);
        assertTrue("Game should still accept a valid move after invalid retries.",
                hasBoardWithCellValue(boards, 5, 1));
    }

    @Test
    public void testOutputConsistencyForRequiredMessages() {
        String invalidStart = runGame(new String[] {"3"});
        String invalidMove = runGame(new String[] {"1"}, "abc", "q");
        String occupied = runGame(new String[] {"1"}, "1", "1", "q");
        String quit = runGame(new String[] {"1"}, "q");
        String humanWin = runGame(new String[] {"1"}, "3", "5", "7");
        String computerWin = runGame(new String[] {"1"}, "9", "6", "8");
        String draw = runGame(new String[] {"1"}, "2", "5", "6", "7", "9");

        assertEquals(INVALID_OPTION_MSG, invalidStart.trim());
        assertTrue(invalidMove.contains(INVALID_NUMBER_MSG));
        assertTrue(occupied.contains(OCCUPIED_MSG));
        assertTrue(quit.contains(END_MSG));
        assertTrue(humanWin.contains(HUMAN_WIN_MSG));
        assertTrue(computerWin.contains(COMPUTER_WIN_MSG));
        assertTrue(draw.contains(DRAW_MSG));
    }

    @Ignore("OOP compliance is a white-box review item and not black-box terminal automation.")
    @Test
    public void testRequirementGapOopComplianceNotBlackBoxTestable() {
        fail("Intentionally ignored: use code review checklist for OOP evaluation.");
    }

    @Ignore("Board visual separator/spacing is ambiguous without a canonical format contract.")
    @Test
    public void testRequirementGapBoardVisualFormatAmbiguity() {
        fail("Intentionally ignored until canonical board format is agreed.");
    }

    @Ignore("Startup argument count strictness requires clarified acceptance criteria.")
    @Test
    public void testRequirementGapStartupArgumentCountAmbiguity() {
        fail("Intentionally ignored until strict argument contract is finalized.");
    }

    @Ignore("Input normalization rules for whitespace/trim behavior are currently ambiguous.")
    @Test
    public void testRequirementGapNonIntegerParsingNormalizationAmbiguity() {
        fail("Intentionally ignored until parsing normalization is clarified.");
    }

    private String runGame(String[] args, String... inputLines) {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();

        try (PipedOutputStream pipedOutput = new PipedOutputStream();
                PipedInputStream pipedInput = new PipedInputStream(pipedOutput);
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(pipedOutput, StandardCharsets.UTF_8), true);
                PrintStream capture = new PrintStream(outputBuffer, true, StandardCharsets.UTF_8.name())) {

            for (String line : inputLines) {
                writer.println(line);
            }

            writer.flush();

            System.setIn(pipedInput);
            System.setOut(capture);
            System.setErr(capture);

            TerminalTTTBasic.main(args);

        } catch (Exception ex) {
            throw new AssertionError("Error while running TerminalTTTBasic main method", ex);
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
            System.setIn(originalIn);
        }

        return normalizeOutput(new String(outputBuffer.toByteArray(), StandardCharsets.UTF_8));
    }

    private String normalizeOutput(String output) {
        String normalized = output.replace("\r\n", "\n").replace("\r", "\n");
        return normalized.replace('\u2019', '\'');
    }

    private List<int[]> extractBoards(String output) {
        List<int[]> boards = new ArrayList<>();
        List<int[]> pendingRows = new ArrayList<>();

        String[] lines = output.split("\\n");
        for (String line : lines) {
            int[] row = extractThreeCellRow(line);
            if (row == null) {
                continue;
            }

            pendingRows.add(row);
            if (pendingRows.size() == 3) {
                int[] board = new int[9];
                System.arraycopy(pendingRows.get(0), 0, board, 0, 3);
                System.arraycopy(pendingRows.get(1), 0, board, 3, 3);
                System.arraycopy(pendingRows.get(2), 0, board, 6, 3);
                boards.add(board);
                pendingRows.clear();
            }
        }

        return boards;
    }

    private int[] extractThreeCellRow(String line) {
        Matcher matcher = Pattern.compile("[012]").matcher(line);
        List<Integer> digits = new ArrayList<>();
        while (matcher.find()) {
            digits.add(Integer.parseInt(matcher.group()));
        }

        if (digits.size() != 3) {
            return null;
        }

        return new int[] {digits.get(0), digits.get(1), digits.get(2)};
    }

    private boolean hasBoardWithCellValue(List<int[]> boards, int oneBasedCellIndex, int expectedValue) {
        int cellIndex = oneBasedCellIndex - 1;
        for (int[] board : boards) {
            if (board[cellIndex] == expectedValue) {
                return true;
            }
        }
        return false;
    }

    private boolean containsBoard(List<int[]> boards, int[] expectedBoard) {
        for (int[] board : boards) {
            if (Arrays.equals(board, expectedBoard)) {
                return true;
            }
        }
        return false;
    }

    private int countOccurrences(String text, String token) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(token, index)) >= 0) {
            count++;
            index += token.length();
        }
        return count;
    }
}
