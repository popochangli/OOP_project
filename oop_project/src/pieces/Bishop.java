package pieces;

public class Bishop extends Piece {

    public Bishop(boolean team, int[] location) {
        super(team, location);
    }

    @Override
    public boolean moveValid(int[] target, Piece[][] board) {
        int dx = Math.abs(target[0] - location[0]);
        int dy = Math.abs(target[1] - location[1]);
        return dx == dy; // Move must be diagonal
    }
}
