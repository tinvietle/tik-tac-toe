package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

final class GameTestHarness {
    private GameTestHarness() {
    }

    static RunResult run(String[] args, String input) {
        synchronized (GameTestHarness.class) {
            InputStream originalIn = System.in;
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;

            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
            ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outBuffer, true, StandardCharsets.UTF_8);
            PrintStream err = new PrintStream(errBuffer, true, StandardCharsets.UTF_8);

            Throwable throwable = null;
            try {
                System.setIn(in);
                System.setOut(out);
                System.setErr(err);
                Main.main(args);
            } catch (Throwable t) {
                throwable = t;
            } finally {
                System.setIn(originalIn);
                System.setOut(originalOut);
                System.setErr(originalErr);
            }

            return new RunResult(normalize(outBuffer.toString(StandardCharsets.UTF_8)),
                normalize(errBuffer.toString(StandardCharsets.UTF_8)), throwable);
        }
    }

    static String normalize(String text) {
        return text.replace("\r\n", "\n");
    }

    static void assertContainsInOrder(String text, String... tokens) {
        int searchFrom = 0;
        for (String token : tokens) {
            int foundAt = text.indexOf(token, searchFrom);
            if (foundAt < 0) {
                throw new AssertionError("Token not found in order: " + token + "\nOutput:\n" + text);
            }
            searchFrom = foundAt + token.length();
        }
    }

    static int countOccurrences(String text, String token) {
        int count = 0;
        int index = 0;
        while (true) {
            index = text.indexOf(token, index);
            if (index < 0) {
                return count;
            }
            count++;
            index += token.length();
        }
    }

    record RunResult(String stdout, String stderr, Throwable throwable) {
    }
}
