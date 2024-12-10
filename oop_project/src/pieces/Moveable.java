package pieces;

import gui.ChessPane;

public interface Moveable {
	// By default interface methods are implicitly abstract and public
	boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane);
}
