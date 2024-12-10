package pieces;

import gui.ChessPane;
import javafx.scene.image.Image;

public class Queen extends Piece implements Moveable {

	public Queen(String team, Image image) {
		super(team, image);
	}

	@Override
	public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
		int dx = Math.abs(targetX - startX);
		int dy = Math.abs(targetY - startY);

		// Queens move in straight lines or diagonally
		if ((dx == 0 || dy == 0 || dx == dy) && isPathClear(startX, startY, targetX, targetY, chessPane)) {
			return canLandOn(targetX, targetY, chessPane);
		}
		return false;
	}

}
