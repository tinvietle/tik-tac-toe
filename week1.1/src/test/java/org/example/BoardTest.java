package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void tc05_and_atc01_mapping_move1_updates_topLeft() {
        Board board = new Board();

        board.placeMove(1, 1);

        String[] lines = board.render().split("\\R");
        assertEquals("| 1 | 0 | 0 |", lines[0]);
    }

    @Test
    void tc05_mapping_move5_updates_center() {
        Board board = new Board();

        board.placeMove(5, 1);

        String[] lines = board.render().split("\\R");
        assertEquals("| 0 | 1 | 0 |", lines[1]);
    }

    @Test
    void tc05_and_atc02_mapping_move9_updates_bottomRight() {
        Board board = new Board();

        board.placeMove(9, 1);

        String[] lines = board.render().split("\\R");
        assertEquals("| 0 | 0 | 1 |", lines[2]);
    }

    @Test
    void tc07_and_atc04_range_validation_respects_boundaries() {
        Board board = new Board();

        assertFalse(board.isCellInRange(0));
        assertTrue(board.isCellInRange(1));
        assertTrue(board.isCellInRange(9));
        assertFalse(board.isCellInRange(10));
        assertFalse(board.isCellInRange(999_999_999));
    }

    @Test
    void tc17_cells_do_not_revert_once_set() {
        Board board = new Board();
        board.placeMove(1, 1);
        board.placeMove(2, 2);
        board.placeMove(9, 1);

        String render = board.render();

        assertTrue(render.contains("| 1 | 2 | 0 |"));
        assertTrue(render.contains("| 0 | 0 | 1 |"));
        assertFalse(board.isCellFree(1));
        assertFalse(board.isCellFree(2));
        assertFalse(board.isCellFree(9));
    }

    @Test
    void tc21_render_format_is_stable_and_three_lines() {
        Board board = new Board();

        String render = board.render();

        String[] lines = render.split("\\R");
        assertEquals(3, lines.length);
        assertEquals("| 0 | 0 | 0 |", lines[0]);
        assertEquals("| 0 | 0 | 0 |", lines[1]);
        assertEquals("| 0 | 0 | 0 |", lines[2]);
    }

    @Test
    void tc12_human_win_detection() {
        Board board = new Board();
        board.placeMove(1, 1);
        board.placeMove(4, 1);
        board.placeMove(7, 1);

        assertTrue(board.hasWinner(1));
        assertFalse(board.hasWinner(2));
    }

    @Test
    void tc13_computer_win_detection() {
        Board board = new Board();
        board.placeMove(1, 2);
        board.placeMove(2, 2);
        board.placeMove(3, 2);

        assertTrue(board.hasWinner(2));
        assertFalse(board.hasWinner(1));
    }

    @Test
    void tc14_tc15_draw_state_has_no_free_cells() {
        Board board = new Board();
        int[] values = {1, 2, 1, 1, 2, 2, 2, 1, 1};
        for (int i = 0; i < values.length; i++) {
            board.placeMove(i + 1, values[i]);
        }

        assertFalse(board.hasFreeCells());
        assertEquals(-1, board.firstFreeCell());
    }

    @Test
    void tc10_and_req06_first_free_cell_is_smallest_available() {
        Board board = new Board();
        board.placeMove(1, 1);
        board.placeMove(2, 2);
        board.placeMove(3, 1);
        board.placeMove(4, 2);

        assertEquals(5, board.firstFreeCell());
    }
}
