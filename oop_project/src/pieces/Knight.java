package pieces;

import gui.ChessPane;
import javafx.scene.image.Image;

public class Knight extends Piece implements Moveable{

    public Knight(String team, Image image) {
        super(team, image);
    }

    @Override
    public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);

        // Knights move in an "L" shape
        if( (dx == 2 && dy == 1) || (dx == 1 && dy == 2) || ((dx == 0) && (dy == 0))) {
            return canLandOn(targetX, targetY, chessPane);
        }
        return false;
    }
}
