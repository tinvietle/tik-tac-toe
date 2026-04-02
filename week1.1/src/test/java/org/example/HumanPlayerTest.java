package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class HumanPlayerTest {

    @Test
    void tc06_and_atc05_non_integer_inputs_are_rejected_until_valid() {
        Board board = new Board();
        Scanner scanner = new Scanner("x\n1.5\nabc\n5\n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(5, chosen);
        assertEquals(3, TestConsoleSupport.count(output, "Please, input a valid number [1-9]"));
        assertEquals(3, TestConsoleSupport.count(output, "Player#1's turn"));
        assertTrue(board.isCellFree(5));
    }

    @Test
    void tc07_and_atc04_out_of_range_values_are_rejected_until_valid() {
        Board board = new Board();
        Scanner scanner = new Scanner("0\n10\n-5\n999999999\n2147483648\n3\n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(3, chosen);
        assertEquals(5, TestConsoleSupport.count(output, "Please, input a valid number [1-9]"));
    }

    @Test
    void tc08_and_atc14_occupied_cell_is_rejected_then_free_cell_accepted() {
        Board board = new Board();
        board.placeMove(1, 2);
        Scanner scanner = new Scanner("1\n2\n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(2, chosen);
        assertTrue(output.contains("The cell is occupied!"));
        assertTrue(output.contains("Player#1's turn"));
        assertFalse(board.isCellFree(1));
        assertTrue(board.isCellFree(2));
    }

    @Test
    void atc06_multi_token_input_rejected_and_recovers() {
        Board board = new Board();
        Scanner scanner = new Scanner("5 6\n5\n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(5, chosen);
        assertTrue(output.contains("Please, input a valid number [1-9]"));
    }

    @Test
    void tc19_whitespace_around_number_is_accepted_blank_line_is_rejected() {
        Board board = new Board();
        Scanner scanner = new Scanner("\n 5 \n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(5, chosen);
        assertEquals(1, TestConsoleSupport.count(output, "Please, input a valid number [1-9]"));
    }

    @Test
    void atc13_recovery_after_non_integer_then_valid_move() {
        Board board = new Board();
        Scanner scanner = new Scanner("hello\n4\n");
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(4, chosen);
        assertTrue(output.contains("Please, input a valid number [1-9]"));
    }

    @Test
    void atc15_many_invalid_attempts_do_not_break_recovery() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            input.append("bad").append(i).append("\n");
            input.append("0\n");
        }
        input.append("7\n");

        Board board = new Board();
        Scanner scanner = new Scanner(input.toString());
        HumanPlayer player = new HumanPlayer(1, scanner);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        int chosen;
        try {
            chosen = player.chooseCell(board);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = TestConsoleSupport.normalize(out.toString(StandardCharsets.UTF_8));
        assertEquals(7, chosen);
        assertEquals(20, TestConsoleSupport.count(output, "Please, input a valid number [1-9]"));
    }

    @Test
    void tc20_and_atc16_eof_behavior_is_observable_for_gap_tracking() {
        Board board = new Board();
        Scanner scanner = new Scanner("");
        HumanPlayer player = new HumanPlayer(1, scanner);

        Throwable thrown = null;
        try {
            player.chooseCell(board);
        } catch (Throwable t) {
            thrown = t;
        } finally {
            scanner.close();
        }

        assertNotNull(thrown);
    }
}
