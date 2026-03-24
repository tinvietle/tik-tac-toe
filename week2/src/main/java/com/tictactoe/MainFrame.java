package com.tictactoe;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {
    private final Game game;
    private final JButton[] cells;
    private final JLabel statusLabel;

    public MainFrame(Game game) {
        this.game = game;
        this.cells = new JButton[9];
        this.statusLabel = new JLabel("", SwingConstants.CENTER);

        initUi();
        refreshBoard();
        refreshStatus();
    }

    private void initUi() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        for (int i = 0; i < 9; i++) {
            final int position = i + 1;
            JButton button = new JButton(String.valueOf(position));
            button.setFont(new Font("SansSerif", Font.BOLD, 36));
            button.addActionListener(e -> handleUserMove(position));
            cells[i] = button;
            boardPanel.add(button);
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private void handleUserMove(int position) {
        boolean success = game.performUserMove(position);
        if (!success) {
            if (game.getState() == GameState.IN_PROGRESS) {
                JOptionPane.showMessageDialog(this, "Invalid move. Please choose an empty cell.");
            }
            return;
        }

        refreshBoard();
        refreshStatus();

        if (game.getState() != GameState.IN_PROGRESS) {
            showResultDialog();
        }
    }

    private void refreshBoard() {
        for (int i = 0; i < 9; i++) {
            int position = i + 1;
            int value = game.getBoard().getCellValue(position);
            JButton button = cells[i];

            if (value == 1) {
                button.setText("X");
                button.setEnabled(false);
            } else if (value == 2) {
                button.setText("O");
                button.setEnabled(false);
            } else {
                button.setText(String.valueOf(position));
                button.setEnabled(game.getState() == GameState.IN_PROGRESS && game.isUserTurn());
            }
        }
    }

    private void refreshStatus() {
        GameState state = game.getState();
        if (state == GameState.USER_WON) {
            statusLabel.setText("You win!");
        } else if (state == GameState.COMPUTER_WON) {
            statusLabel.setText("Computer wins!");
        } else if (state == GameState.DRAW) {
            statusLabel.setText("Draw game.");
        } else if (game.isUserTurn()) {
            statusLabel.setText("Your turn (X)");
        } else {
            statusLabel.setText("Computer turn (O)");
        }
    }

    public void syncAfterStartupMove() {
        refreshBoard();
        refreshStatus();

        if (game.getState() != GameState.IN_PROGRESS) {
            showResultDialog();
        }
    }

    private void showResultDialog() {
        String message;
        if (game.getState() == GameState.USER_WON) {
            message = "You win!";
        } else if (game.getState() == GameState.COMPUTER_WON) {
            message = "Computer wins!";
        } else {
            message = "Draw!";
        }

        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
