package com.tictactoe;

public class Game {
    private final Board board;
    private final Player userPlayer;
    private final Player computerPlayer;
    private final int firstTurn;

    private int currentMarker;
    private GameState state;

    public Game(int firstTurn) {
        if (firstTurn != 1 && firstTurn != 2) {
            throw new IllegalArgumentException("First turn must be 1 (user) or 2 (computer).");
        }

        this.board = new Board();
        this.userPlayer = new HumanPlayer(1);
        this.computerPlayer = new ComputerPlayer(2);
        this.firstTurn = firstTurn;
        this.currentMarker = firstTurn;
        this.state = GameState.IN_PROGRESS;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState() {
        return state;
    }

    public int getCurrentMarker() {
        return currentMarker;
    }

    public boolean isUserTurn() {
        return currentMarker == userPlayer.getMarker();
    }

    public void start() {
        if (firstTurn == computerPlayer.getMarker()) {
            performComputerMove();
        }
    }

    public boolean performUserMove(int position) {
        if (state != GameState.IN_PROGRESS || !isUserTurn()) {
            return false;
        }

        boolean placed = board.placeMove(position, userPlayer.getMarker());
        if (!placed) {
            return false;
        }

        updateStateAfterMove(userPlayer.getMarker());
        if (state == GameState.IN_PROGRESS) {
            currentMarker = computerPlayer.getMarker();
            performComputerMove();
        }

        return true;
    }

    private void performComputerMove() {
        if (state != GameState.IN_PROGRESS || isUserTurn()) {
            return;
        }

        int position = computerPlayer.chooseMove(board);
        if (position == -1) {
            state = GameState.DRAW;
            return;
        }

        board.placeMove(position, computerPlayer.getMarker());
        updateStateAfterMove(computerPlayer.getMarker());

        if (state == GameState.IN_PROGRESS) {
            currentMarker = userPlayer.getMarker();
        }
    }

    private void updateStateAfterMove(int marker) {
        if (board.hasWinner(marker)) {
            state = marker == userPlayer.getMarker() ? GameState.USER_WON : GameState.COMPUTER_WON;
            return;
        }

        if (board.isFull()) {
            state = GameState.DRAW;
        }
    }
}
