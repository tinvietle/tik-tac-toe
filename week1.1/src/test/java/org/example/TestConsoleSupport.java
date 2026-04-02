package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

final class TestConsoleSupport {
    private TestConsoleSupport() {
    }

    static RunResult runMain(String[] args, String stdin) {
        PrintStream originalOut = System.out;
        java.io.InputStream originalIn = System.in;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Throwable thrown = null;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            System.setIn(new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8)));
            Main.main(args);
        } catch (Throwable t) {
            thrown = t;
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }

        return new RunResult(normalize(output.toString(StandardCharsets.UTF_8)), thrown);
    }

    static RunResult runGame(Game game) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Throwable thrown = null;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            game.play();
        } catch (Throwable t) {
            thrown = t;
        } finally {
            System.setOut(originalOut);
        }

        return new RunResult(normalize(output.toString(StandardCharsets.UTF_8)), thrown);
    }

    static String normalize(String text) {
        return text.replace("\r\n", "\n").replace('\r', '\n');
    }

    static int count(String text, String token) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(token, index)) >= 0) {
            count++;
            index += token.length();
        }
        return count;
    }

    static final class RunResult {
        private final String output;
        private final Throwable throwable;

        RunResult(String output, Throwable throwable) {
            this.output = output;
            this.throwable = throwable;
        }

        String output() {
            return output;
        }

        Throwable throwable() {
            return throwable;
        }
    }
}
