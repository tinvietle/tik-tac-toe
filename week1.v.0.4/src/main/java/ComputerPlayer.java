/**
 * Computer player that chooses the first free cell (1-9).
 */
public class ComputerPlayer extends Player {

    public ComputerPlayer(int playerNumber) {
        super(playerNumber);
    }

    @Override
    public boolean takeTurn(Board board) {
        int cellNumber = board.firstFreeCell();
        board.place(cellNumber, playerNumber);
        return true;
    }
}
