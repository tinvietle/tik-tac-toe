package com.progex.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Simple Swing implementation of GameView.
 *
 * Thread model:
 *   - Game.play() runs on a background thread so it never blocks the EDT.
 *   - requestMove() blocks on a SynchronousQueue until the user clicks a cell.
 */
public class SwingGameView implements GameView {

    private JFrame frame;
    private JButton[] cells;
    private JLabel statusLabel;

    private final SynchronousQueue<Integer> moveQueue = new SynchronousQueue<>();
    private volatile boolean awaitingHumanMove = false;

    // ── GameView ───────────────────────────────────────────────────────────────

    @Override
    public void onGameStart(Board board) {
        SwingUtilities.invokeLater(this::buildUI);
        try { Thread.sleep(300); } catch (InterruptedException ignored) {}
    }

    @Override
    public void onMoveMade(Board board, Player player, int position) {
        SwingUtilities.invokeLater(() -> {
            refreshBoard(board);
            statusLabel.setText(player.getName() + " placed at " + position);
        });
        try { Thread.sleep(250); } catch (InterruptedException ignored) {}
    }

    @Override
    public void onPlayerWins(Player winner) {
        SwingUtilities.invokeLater(() -> {
            disableAllCells();
            statusLabel.setText(winner.getName() + " wins!");
            JOptionPane.showMessageDialog(frame, winner.getName() + " wins!");
            System.exit(0);
        });
    }

    @Override
    public void onDraw() {
        SwingUtilities.invokeLater(() -> {
            disableAllCells();
            statusLabel.setText("It's a draw!");
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            System.exit(0);
        });
    }

    @Override
    public int requestMove(Board board, Player player) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(player.getName() + "'s turn — click a cell");
            enableFreeCells(board);
        });
        awaitingHumanMove = true;
        try {
            return moveQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Move interrupted", e);
        } finally {
            awaitingHumanMove = false;
        }
    }

    // ── UI construction ────────────────────────────────────────────────────────

    private void buildUI() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        statusLabel = new JLabel("Starting…", SwingConstants.CENTER);
        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(buildBoardPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel buildBoardPanel() {
        JPanel grid = new JPanel(new GridLayout(3, 3, 4, 4));
        cells = new JButton[9];

        for (int i = 0; i < 9; i++) {
            final int position = i + 1;
            JButton btn = new JButton(String.valueOf(position));
            btn.setPreferredSize(new Dimension(90, 90));
            btn.setFont(new Font("SansSerif", Font.BOLD, 28));
            btn.setEnabled(false);
            btn.addActionListener(e -> {
                if (awaitingHumanMove) {
                    disableAllCells();
                    try { moveQueue.put(position); }
                    catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
                }
            });
            cells[i] = btn;
            grid.add(btn);
        }
        return grid;
    }

    // ── helpers ────────────────────────────────────────────────────────────────

    private void refreshBoard(Board board) {
        int[] state = board.getCells();
        for (int i = 0; i < 9; i++) {
            if (state[i] == 1)      { cells[i].setText("X"); cells[i].setEnabled(false); }
            else if (state[i] == 2) { cells[i].setText("O"); cells[i].setEnabled(false); }
            else                    { cells[i].setText(String.valueOf(i + 1)); }
        }
    }

    private void enableFreeCells(Board board) {
        for (int i = 0; i < 9; i++) {
            if (board.isFree(i + 1)) cells[i].setEnabled(true);
        }
    }

    private void disableAllCells() {
        for (JButton cell : cells) cell.setEnabled(false);
    }
}