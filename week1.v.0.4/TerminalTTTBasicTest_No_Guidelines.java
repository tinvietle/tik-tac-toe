import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class TerminalTTTBasicTest {

    private static final String MAIN_CLASS = "TerminalTTTBasic";
    private static final Duration PROCESS_TIMEOUT = Duration.ofSeconds(5);

    @Test
    @DisplayName("TS-001: Start game with human first")
    void shouldStartWithHumanFirst() throws Exception {
        ProcessResult result = runGame(List.of("1"), "q\n");

        assertLineExists(result.output(), "Hello!");
        assertLineExists(result.output(), "Player#1['\\u2019]s turn");
        assertInitialBoardIsEmpty(result.output());
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-002: Start game with computer first")
    void shouldStartWithComputerFirst() throws Exception {
        ProcessResult result = runGame(List.of("2"), "q\n");

        assertLineExists(result.output(), "Hello!");
        assertLineExists(result.output(), "Player#2['\\u2019]s turn");
        assertInitialBoardIsEmpty(result.output());
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-003: Reject missing startup argument")
    void shouldRejectMissingStartupArgument() throws Exception {
        ProcessResult result = runGame(List.of(), null);

        assertLineExists(result.output(), "Please, input a valid option \\[1-2\\]");
        assertFalse(result.output().contains("Hello!"), "Game should not start when startup argument is missing");
    }

    @ParameterizedTest(name = "TS-004: Reject invalid startup argument: {0}")
    @CsvSource({"3", "0", "-1", "abc"})
    void shouldRejectInvalidStartupArgumentValues(String value) throws Exception {
        ProcessResult result = runGame(List.of(value), null);

        assertLineExists(result.output(), "Please, input a valid option \\[1-2\\]");
        assertFalse(result.output().contains("Hello!"), "Game should not start for invalid argument: " + value);
    }

    @Test
    @DisplayName("TS-005: Validate startup argument strictness for extra argument")
    void shouldRejectExtraStartupArguments() throws Exception {
        ProcessResult result = runGame(List.of("1", "2"), null);

        assertLineExists(result.output(), "Please, input a valid option \\[1-2\\]");
    }

    @ParameterizedTest(name = "TS-005: Validate startup argument strictness for value: {0}")
    @CsvSource({"01", " 1"})
    void shouldRejectFormattedStartupArguments(String value) throws Exception {
        ProcessResult result = runGame(List.of(value), null);

        assertLineExists(result.output(), "Please, input a valid option \\[1-2\\]");
    }

    @Test
    @DisplayName("TS-006/TS-007: Board renders as 3x3 state values and valid human move updates board")
    void shouldRenderBoardAndUpdateOnValidHumanMove() throws Exception {
        ProcessResult result = runGame(List.of("1"), "5\nq\n");

        List<int[]> boards = extractBoardSnapshots(result.output());
        assertFalse(boards.isEmpty(), "Expected at least one board snapshot in output");
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}, boards.get(0));

        int[] boardAfterHumanMove = boards.stream()
            .filter(board -> board[4] == 1)
            .findFirst()
            .orElseThrow(() -> new AssertionError("No board snapshot found with cell 5 occupied by Player#1"));
        assertEquals(1, boardAfterHumanMove[4]);

        assertFalse(result.output().contains("| 1 | 2 | 3 |"), "Reference numbering should never be shown on displayed board");
        assertFalse(result.output().contains("1 2 3"), "Reference numbering should never be shown on displayed board");
    }

    @ParameterizedTest(name = "TS-008: Reject non-integer input: [{0}]")
    @MethodSource("invalidNonIntegerInputs")
    void shouldHandleNonIntegerInputAsInvalid(String input) throws Exception {
        ProcessResult result = runGame(List.of("1"), input + "\nq\n");

        assertLineExists(result.output(), "Please, input a valid number \\[1-9\\]");
        assertLineExists(result.output(), "Player#1['\\u2019]s turn");
    }

    @Test
    @DisplayName("TS-009: Quit game with q")
    void shouldQuitGameWithLowerCaseQ() throws Exception {
        ProcessResult result = runGame(List.of("1"), "q\n");

        assertLineExists(result.output(), "End of the game");
        assertProcessEndedCleanly(result);
    }

    @ParameterizedTest(name = "TS-010: q strictness for input [{0}]")
    @CsvSource({"Q", " q", "q "})
    void shouldTreatNonExactQAsInvalid(String input) throws Exception {
        ProcessResult result = runGame(List.of("1"), input + "\nq\n");

        assertLineExists(result.output(), "Please, input a valid number \\[1-9\\]");
        assertLineExists(result.output(), "Player#1['\\u2019]s turn");
    }

    @ParameterizedTest(name = "TS-011: Reject integer outside range [1-9]: {0}")
    @CsvSource({"0", "10", "-3"})
    void shouldRejectOutOfRangeIntegerInput(String number) throws Exception {
        ProcessResult result = runGame(List.of("1"), number + "\nq\n");

        assertLineExists(result.output(), "Please, input a valid number \\[1-9\\]");
        assertLineExists(result.output(), "Player#1['\\u2019]s turn");
    }

    @Test
    @DisplayName("TS-012: Reject move to occupied cell")
    void shouldRejectMoveToOccupiedCell() throws Exception {
        ProcessResult result = runGame(List.of("1"), "1\n1\nq\n");

        assertLineExists(result.output(), "The cell is occupied!");
        assertLineExists(result.output(), "Player#1['\\u2019]s turn");
    }

    @ParameterizedTest(name = "TS-013: Human win detection ({0})")
    @MethodSource("humanWinScenarios")
    void shouldDetectHumanWin(String scenario, String input, int[] expectedWinningCells) throws Exception {
        ProcessResult result = runGame(List.of("1"), input);

        assertLineExists(result.output(), "Player#1 won!");

        int[] finalBoard = extractLastBoard(result.output());
        for (int cell : expectedWinningCells) {
            assertEquals(1, finalBoard[cell - 1], "Expected winning cell " + cell + " to be occupied by Player#1");
        }
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-014: Computer win detection")
    void shouldDetectComputerWin() throws Exception {
        ProcessResult result = runGame(List.of("2"), "5\n8\n");

        assertLineExists(result.output(), "Player#2 won!");
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-015: Draw detection when board full after human move")
    void shouldDetectDrawAfterHumanMove() throws Exception {
        ProcessResult result = runGame(List.of("1"), "2\n4\n5\n7\n9\n");

        assertLineExists(result.output(), "It is a draw!");
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-016: Draw detection when board full after computer move")
    void shouldDetectDrawAfterComputerMove() throws Exception {
        ProcessResult result = runGame(List.of("2"), "2\n4\n7\n9\n");

        assertLineExists(result.output(), "It is a draw!");
        assertProcessEndedCleanly(result);
    }

    @Test
    @DisplayName("TS-017: Computer chooses first available cell")
    void shouldChooseFirstAvailableCellForComputerMoves() throws Exception {
        ProcessResult result = runGame(List.of("2"), "5\n8\n");

        assertComputerAlwaysChoosesLowestFreeCell(extractBoardSnapshots(result.output()));
    }

    @Test
    @DisplayName("TS-018: Board integrity after every move")
    void shouldPreserveBoardIntegrityAcrossMoves() throws Exception {
        ProcessResult result = runGame(List.of("1"), "5\n2\n9\nq\n");

        List<int[]> boards = extractBoardSnapshots(result.output());
        assertFalse(boards.size() < 3, "Expected multiple board snapshots to validate integrity");

        for (int i = 1; i < boards.size(); i++) {
            int[] previous = boards.get(i - 1);
            int[] current = boards.get(i);

            int changedCells = 0;
            for (int c = 0; c < 9; c++) {
                assertTrue(current[c] == 0 || current[c] == 1 || current[c] == 2, "Board value must be 0/1/2");
                if (current[c] != previous[c]) {
                    changedCells++;
                    assertEquals(0, previous[c], "Cells must never be overwritten once occupied");
                }
            }
            assertTrue(changedCells <= 1, "At most one cell may change between two consecutive board snapshots");
        }
    }

    @Test
    @DisplayName("TS-019: Turn prompt sequence correctness for mixed valid/invalid flow")
    void shouldShowCorrectTurnPromptSequence() throws Exception {
        ProcessResult result = runGame(List.of("1"), "abc\n5\n5\n7\nq\n");

        int invalidMsgCount = countMatches(result.output(), "Please, input a valid number \\[1-9\\]");
        int occupiedMsgCount = countMatches(result.output(), "The cell is occupied!");
        int player1PromptCount = countMatches(result.output(), "Player#1['\\u2019]s turn");

        assertTrue(invalidMsgCount >= 1, "Expected at least one invalid input message");
        assertTrue(occupiedMsgCount >= 1, "Expected occupied cell message");
        assertTrue(player1PromptCount >= 3, "Player#1 prompt should reappear after invalid branches");
    }

    @ParameterizedTest(name = "TS-020: Program termination for scenario {0}")
    @MethodSource("terminationScenarios")
    void shouldTerminateCleanlyOnAllFinalStates(String scenario, List<String> args, String input) throws Exception {
        ProcessResult result = runGame(args, input);

        assertProcessEndedCleanly(result);
        assertFalse(result.output().toLowerCase().contains("exception"), "No stack trace or exception expected");
    }

    @Test
    @DisplayName("TS-021: Input robustness under rapid invalid retries")
    void shouldRemainResponsiveAfterManyInvalidInputs() throws Exception {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            input.append("x\n");
            input.append("!\n");
            input.append("999\n");
            input.append("\n");
        }
        input.append("5\nq\n");

        ProcessResult result = runGame(List.of("1"), input.toString());

        assertFalse(result.output().toLowerCase().contains("exception"), "Program should not crash under repeated invalid input");

        int[] boardAfterValidMove = extractBoardSnapshots(result.output()).stream()
            .filter(board -> board[4] == 1)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Valid move after invalid retries was not applied"));
        assertEquals(1, boardAfterValidMove[4]);
    }

    @Test
    @DisplayName("TS-022: Output consistency for required literal messages")
    void shouldUseRequiredContractMessages() throws Exception {
        ProcessResult invalidStart = runGame(List.of("3"), null);
        assertLineExists(invalidStart.output(), "Please, input a valid option \\[1-2\\]");

        ProcessResult invalidMove = runGame(List.of("1"), "abc\nq\n");
        assertLineExists(invalidMove.output(), "Please, input a valid number \\[1-9\\]");

        ProcessResult occupiedMove = runGame(List.of("1"), "1\n1\nq\n");
        assertLineExists(occupiedMove.output(), "The cell is occupied!");

        ProcessResult quit = runGame(List.of("1"), "q\n");
        assertLineExists(quit.output(), "End of the game");

        ProcessResult humanWin = runGame(List.of("1"), "3\n5\n7\n");
        assertLineExists(humanWin.output(), "Player#1 won!");

        ProcessResult computerWin = runGame(List.of("2"), "5\n8\n");
        assertLineExists(computerWin.output(), "Player#2 won!");

        ProcessResult draw = runGame(List.of("1"), "2\n4\n5\n7\n9\n");
        assertLineExists(draw.output(), "It is a draw!");
    }

    @Test
    @Disabled("TS-023 is a code-quality/OOP architecture review item and is not black-box automatable from terminal I/O")
    @DisplayName("TS-023: Requirement gap - OOP compliance is not black-box testable")
    void oopComplianceRequiresWhiteBoxReview() {
        // Intentionally disabled: requires source-level design review rubric.
    }

    @Test
    @Disabled("TS-024 requires stakeholder alignment on canonical board formatting")
    @DisplayName("TS-024: Requirement gap - board visual format ambiguity")
    void boardVisualFormatNeedsCanonicalSpecification() {
        // Intentionally disabled until visual formatting contract is finalized.
    }

    @Test
    @Disabled("TS-025 requires requirement clarification for startup argument count/strictness")
    @DisplayName("TS-025: Requirement gap - startup argument count ambiguity")
    void startupArgumentCountRuleNeedsClarification() {
        // Intentionally disabled until product decision is documented.
    }

    @Test
    @Disabled("TS-026 requires requirement clarification for whitespace trimming rules")
    @DisplayName("TS-026: Requirement gap - non-integer parsing normalization")
    void nonIntegerNormalizationRuleNeedsClarification() {
        // Intentionally disabled until parsing policy is finalized.
    }

    private static Stream<String> invalidNonIntegerInputs() {
        return Stream.of("abc", "@", "");
    }

    private static Stream<Arguments> humanWinScenarios() {
        return Stream.of(
            Arguments.of("row", "4\n5\n6\n", new int[] {4, 5, 6}),
            Arguments.of("column", "3\n6\n9\n", new int[] {3, 6, 9}),
            Arguments.of("diagonal", "3\n5\n7\n", new int[] {3, 5, 7})
        );
    }

    private static Stream<Arguments> terminationScenarios() {
        return Stream.of(
            Arguments.of("human win", List.of("1"), "3\n5\n7\n"),
            Arguments.of("computer win", List.of("2"), "5\n8\n"),
            Arguments.of("draw", List.of("1"), "2\n4\n5\n7\n9\n"),
            Arguments.of("quit", List.of("1"), "q\n")
        );
    }

    private static ProcessResult runGame(List<String> args, String stdin) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add(resolveJavaCommand());
        command.add("-cp");
        command.add(System.getProperty("java.class.path"));
        command.add(MAIN_CLASS);
        command.addAll(args);

        Process process = new ProcessBuilder(command)
            .redirectErrorStream(true)
            .start();

        if (stdin != null) {
            try (OutputStream os = process.getOutputStream()) {
                os.write(stdin.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
        } else {
            process.getOutputStream().close();
        }

        boolean finished = process.waitFor(PROCESS_TIMEOUT.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new AssertionError("Game process did not terminate within timeout");
        }

        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
            .replace("\r\n", "\n")
            .replace("\r", "\n");

        return new ProcessResult(process.exitValue(), output);
    }

    private static String resolveJavaCommand() {
        String javaHome = System.getProperty("java.home");
        Path candidate = Path.of(javaHome, "bin", isWindows() ? "java.exe" : "java");
        return candidate.toFile().exists() ? candidate.toString() : "java";
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private static void assertProcessEndedCleanly(ProcessResult result) {
        assertEquals(0, result.exitCode(), "Program should exit with status code 0");
    }

    private static void assertInitialBoardIsEmpty(String output) {
        List<int[]> boards = extractBoardSnapshots(output);
        assertFalse(boards.isEmpty(), "No board snapshots found in output");
        assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}, boards.get(0), "Initial board must be all zeros");
    }

    private static void assertLineExists(String output, String expectedLineRegex) {
        Pattern linePattern = Pattern.compile("(?m)^" + expectedLineRegex + "$");
        assertTrue(linePattern.matcher(output).find(),
            () -> "Expected line not found: " + expectedLineRegex + "\nActual output:\n" + output);
    }

    private static int[] extractLastBoard(String output) {
        List<int[]> boards = extractBoardSnapshots(output);
        if (boards.isEmpty()) {
            throw new AssertionError("No board snapshot found in output");
        }
        return boards.get(boards.size() - 1);
    }

    private static List<int[]> extractBoardSnapshots(String output) {
        List<int[]> rowBuffer = new ArrayList<>();
        List<int[]> boards = new ArrayList<>();

        for (String line : output.split("\\n")) {
            int[] row = parseBoardRow(line);
            if (row == null) {
                continue;
            }
            rowBuffer.add(row);
            if (rowBuffer.size() == 3) {
                int[] board = new int[9];
                for (int i = 0; i < 3; i++) {
                    System.arraycopy(rowBuffer.get(i), 0, board, i * 3, 3);
                }
                boards.add(board);
                rowBuffer.clear();
            }
        }

        return boards;
    }

    private static int[] parseBoardRow(String line) {
        String[] tokens = line.split("[^0-9]+", -1);
        List<Integer> values = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            if (token.equals("0") || token.equals("1") || token.equals("2")) {
                values.add(Integer.parseInt(token));
            }
        }

        if (values.size() != 3) {
            return null;
        }

        return new int[] {values.get(0), values.get(1), values.get(2)};
    }

    private static int countMatches(String text, String lineRegex) {
        Pattern pattern = Pattern.compile("(?m)^" + lineRegex + "$");
        int count = 0;
        var matcher = pattern.matcher(text);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static void assertComputerAlwaysChoosesLowestFreeCell(List<int[]> boards) {
        assertFalse(boards.size() < 2, "Need at least two board snapshots to evaluate computer strategy");

        for (int i = 1; i < boards.size(); i++) {
            int[] previous = boards.get(i - 1);
            int[] current = boards.get(i);

            int previousComputerMarks = countMarks(previous, 2);
            int currentComputerMarks = countMarks(current, 2);

            if (currentComputerMarks != previousComputerMarks + 1) {
                continue;
            }

            int changedIndex = findSingleChangedIndex(previous, current);
            assertTrue(changedIndex >= 0, "Computer move should update exactly one cell");

            int expectedIndex = firstFreeCellIndex(previous);
            assertEquals(expectedIndex, changedIndex,
                "Computer should choose lowest free cell. Previous board: " + Arrays.toString(previous));
        }
    }

    private static int countMarks(int[] board, int mark) {
        int count = 0;
        for (int cell : board) {
            if (cell == mark) {
                count++;
            }
        }
        return count;
    }

    private static int findSingleChangedIndex(int[] previous, int[] current) {
        int idx = -1;
        for (int i = 0; i < 9; i++) {
            if (previous[i] != current[i]) {
                if (idx != -1) {
                    return -1;
                }
                idx = i;
            }
        }
        return idx;
    }

    private static int firstFreeCellIndex(int[] board) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private record ProcessResult(int exitCode, String output) {}
}
