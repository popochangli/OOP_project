package pieces;

import gui.ChessPane;
import javafx.scene.image.Image;

public class King extends Piece implements Moveable{

    public King(String team, Image image) {
        super(team, image);
    }

    @Override
    public boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
        int dx = Math.abs(targetX - startX);
        int dy = Math.abs(targetY - startY);

        // Kings move one square in any direction
        return dx <= 1 && dy <= 1;
    }
}
