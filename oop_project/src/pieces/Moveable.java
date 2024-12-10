package pieces;

import gui.ChessPane;

public interface Moveable {
    public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane);
}
