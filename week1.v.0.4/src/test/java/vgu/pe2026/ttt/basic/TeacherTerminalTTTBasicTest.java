/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vgu.pe2026.ttt.basic;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeacherTerminalTTTBasicTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    //private ByteArrayOutputStream outputByteArray;

    //private PipedOutputStream outputStream;
    //private BufferedReader scanner;
    // checking that nothing else has been printed-out
    //readLine() does not throw an error (exception) simply because the buffer is empty. 
    //Instead, it handles an empty state in one of two ways: 
    // If the stream is still open: It will block (wait) and attempt to fill the buffer from the underlying source 
    //until a full line is available.
    //If the end of the stream is reached: It returns null.
    @BeforeEach
    void setUp() {

        // A BufferedReader reading (lines) from  pipedInputstream connected to the pipedOutputStream where the System.out 
        //reads is very convenient for testing, since the messages are text and end with end-of-line
        // outputStream = new PipedOutputStream();
        // ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        //  try {
        //      PipedInputStream inputStream = new PipedInputStream(outputStream); // Connect in constructor
        //      scanner = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        //  } catch (IOException ex) {
        //      Logger.getLogger(AbstractPlayer.class.getName()).log(Level.SEVERE, null, ex);
        //  }

        /* this is unavoidable because main uses System.out */
        //System.setOut(new PrintStream(outputStream));
        //PrintStream printer = new PrintStream(outputByteArray, true);
        //System.setOut(printer);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void emptyOptionTest() throws TTTException, IOException {
        
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void invalidOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"a"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void quotedValidOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"'1'"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void extraWhiteSpacesBeforeOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"  1"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void extraWhiteSpacesAfterOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"1 "});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void extraArgumentAfterValidOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"1", "extra"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void extraArgumentAfterInvalidOptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        TerminalTTTBasic.main(new String[]{"a", "extra"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void player1OptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void player2OptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"2"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
            // skipping first computer move
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void nonIntegerPlayerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "a" + System.lineSeparator() + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void outOfRangePlayerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "10" + System.lineSeparator() + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void cellIsOccupiedPlayerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator() + "1" + System.lineSeparator()
                + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("The cell is occupied!", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void cellIsOccupiedPlayerChoiceNoBoardChangeTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator() + "1" + System.lineSeparator()
                + "3" + System.lineSeparator()
                + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("The cell is occupied!", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 1 | 2 | 1 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void getChoiceTrueTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator()
                + "q" + System.lineSeparator();

        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);

            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 1 | 2 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void getChoiceFalseTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "1" + System.lineSeparator()
                + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);

            assertEquals("Player#2's turn", reader.readLine());
            assertNotEquals("| 1 | 0 | 2 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void humanWinsAfterHumanChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator()
                + "6" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 1 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#1 won!", reader.readLine());
            assertNull(reader.readLine());

        }

    }

    @Test
    void computerWinsAfterComputerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 2 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("Player#2 won!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void allCellsAreOccupiedAfterHumanChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        
        String data = "3" + System.lineSeparator() + "4" + System.lineSeparator() + "5" + System.lineSeparator()
                + "8" + System.lineSeparator() + "9";
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after third move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after third move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            //skipping messages after fourth move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            //skipping messages after fourth move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 1 | 1 | 2 |", reader.readLine());
            assertEquals("| 2 | 1 | 1 |", reader.readLine());
            assertEquals("It is a draw!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void allCellsAreOccupiedAfterComputerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "2" + System.lineSeparator() + "5" + System.lineSeparator() + "7" + System.lineSeparator()
                + "9" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"2"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after third move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after third move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            //skipping messages after fourth move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            //skipping messages after fourth move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 1 | 2 |", reader.readLine());
            assertEquals("| 2 | 1 | 2 |", reader.readLine());
            assertEquals("| 1 | 2 | 1 |", reader.readLine());
            assertEquals("It is a draw!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void humanWinsAfterHumanChoiceLastCellTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "3" + System.lineSeparator() + "6" + System.lineSeparator() + "7" + System.lineSeparator()
                + "8" + System.lineSeparator() + "9";
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after third move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after third move by the computer
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            //skipping messages after fourth move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            //skipping messages after fourth move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 1 | 1 | 1 |", reader.readLine());
            assertEquals("Player#1 won!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void computerWinsAfterComputerChoiceLastCellTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "3" + System.lineSeparator() + "6" + System.lineSeparator() + "7" + System.lineSeparator()
                + "8";
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"2"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {
            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after first move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after first move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after second move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after second move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            // skipping messages after third move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            // skipping messages after third move by the human
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            //skipping messages after fourth move by the computer 
            skipLines(3, reader);
            assertEquals("Player#1's turn", reader.readLine());
            //skipping messages after fourth move by the human 
            skipLines(3, reader);
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 2 | 2 | 1 |", reader.readLine());
            assertEquals("| 1 | 1 | 2 |", reader.readLine());
            assertEquals("Player#2 won!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void humanWinsGameTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator() + "6" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 1 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1 won!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    // computer wins 
    @Test
    void computerWinsGameTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator() + "6" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"2"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 2 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2 won!", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void afterHumanWinsNoMoreInputConsumedGameTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));

        String data = "4" + System.lineSeparator() + "5" + System.lineSeparator() + "6" + System.lineSeparator()
                + "7" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            //

            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //

            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 0 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 0 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#2's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 0 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());
            //
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("| 2 | 2 | 0 |", reader.readLine());
            assertEquals("| 1 | 1 | 1 |", reader.readLine());
            assertEquals("| 0 | 0 | 0 |", reader.readLine());

            assertEquals("Player#1 won!", reader.readLine());
            //
            assertNull(reader.readLine());
        }
    }

    @Test
    void extraWhiteSpacesBeforePlayerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = " 5" + System.lineSeparator()
                + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());

        }
    }

    @Test
    void extraWhiteSpacesAfterPlayerChoiceTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        String data = "5 " + System.lineSeparator()
                + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes(); // Convert string to byte array
        InputStream inputStream = new ByteArrayInputStream(byteArray);

        System.setIn(inputStream);

        TerminalTTTBasic.main(new String[]{"1"});

        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    class BrokenInputStream extends InputStream {

        @Override
        public int read() throws IOException {
            throw new IOException("Read failed");
        }
    }

    @Test
    public void IOExceptionTest() throws TTTException, IOException {
        ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputByteArray, true));


        System.setIn(new BrokenInputStream());

        TerminalTTTBasic.main(new String[]{"1"});
        byte[] printout = outputByteArray.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printout), StandardCharsets.UTF_8))) {

            // skipping initial messages
            skipLines(4, reader);
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Unexpected exception when reading player's choice", reader.readLine());
            assertNull(reader.readLine());
            
        }
    }

    
    void skipLines(int number, BufferedReader reader) throws IOException {
        for (int i = 0; i < number; i = i + 1) {
            reader.readLine();
        }
    }

}