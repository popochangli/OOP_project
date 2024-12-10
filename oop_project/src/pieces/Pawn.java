package pieces;

import gui.ChessPane;
import gui.ChessCell;
import javafx.scene.image.Image;

public class Pawn extends Piece implements Moveable {

	public Pawn(String team, Image image) {
		super(team, image);
	}

	@Override
	public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
		int dx = targetX - startX;
		int dy = targetY - startY;

//        // Pawns can move one square forward
//        if ("W".equals(team)) {
//            return dx == 0 && dy == -1 && !chessPane.getCell(targetX, targetY).hasPiece();
//        } else {
//            return dx == 0 && dy == 1 && !chessPane.getCell(targetX, targetY).hasPiece();
//        }
		// Check if target cell is within bounds
		ChessCell targetCell = chessPane.getCell(targetX, targetY);
		if (targetCell == null) {
			System.out.println("Target cell is out of bounds or null.");
			return false;
		}

		// White pawn moves forward (dy = -1) to an empty cell
		if (dx == 0 && dy == -1 && !targetCell.hasPiece()) {
			return canLandOn(targetX, targetY, chessPane);
		}

		// White pawn captures diagonally (dy = -1, abs(dx) = 1) if an opponent piece
		// exists
		if (dy == -1 && Math.abs(dx) == 1) {
			Piece targetPiece = targetCell.getPiece();
			return targetPiece != null && canLandOn(targetX, targetY, chessPane);// Opponent piece
		}

		return false;
	}
}
