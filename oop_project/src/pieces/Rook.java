package pieces;

import gui.ChessPane;
import javafx.scene.image.Image;

public class Rook extends Piece implements Moveable{

    public Rook(String team, Image image) {
        super(team, image);
    }

    @Override
    public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
        int dx = targetX - startX;
        int dy = targetY - startY;

        // Rooks move in straight lines (horizontal or vertical)
        return (dx == 0 || dy == 0) && isPathClear(startX, startY, targetX, targetY, chessPane);
    }

    
}
