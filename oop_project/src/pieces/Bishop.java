package pieces;

import gui.ChessPane;
import javafx.scene.image.Image;

public class Bishop extends Piece implements Moveable{

    public Bishop(String team, Image image) {
        super(team, image);
    }

    @Override
    public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);

        // Bishops move diagonally
        //return dx == dy && isPathClear(startX, startY, targetX, targetY, chessPane);
        if(dx == dy && isPathClear(startX, startY, targetX, targetY, chessPane)){
        	return canLandOn(targetX, targetY, chessPane);
        }
        return false;
    }

    
}
