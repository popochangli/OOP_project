package pieces;

import javafx.scene.image.Image;
import gui.ChessPane;
import gui.TicTacToeCell;

public abstract class Piece {
    protected String team; // "W" for White, "B" for Black
    protected Image image;

    public Piece(String team, Image image) {
        this.team = team;
        this.image = image;
    }

    public String getTeam() {
        return team;
    }

    public Image getImage() {
        return image;
    }

    public void setTeam(String team) {
		this.team = team;
	}

	public abstract boolean validateMove(int startX, int startY, int targetX, int targetY, ChessPane chessPane);
	
	protected boolean isPathClear(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
        int dx = Integer.compare(targetX, startX);
        int dy = Integer.compare(targetY, startY);

        int x = startX + dx;
        int y = startY + dy;

        while (x != targetX || y != targetY) {
        	
        	//add wall edit
        	
//            if (chessPane.getCell(x, y).hasPiece()) {
//                return false;
//            }
        	TicTacToeCell cell = chessPane.getCell(x, y);
            if (cell == null || cell.hasWall() || cell.hasPiece()) {
                return false; // Path is blocked by a wall or another piece
            }
            x += dx;
            y += dy;
        }
        return true;
    }
	
	//add wall edit
	protected boolean canLandOn(int targetX, int targetY, ChessPane chessPane) {
	    TicTacToeCell targetCell = chessPane.getCell(targetX, targetY);

	    // Check if the target cell has a wall
	    if (targetCell != null && targetCell.hasWall()) {
	        return false; // Cannot land on a wall
	    }

	    return true; // Valid landing
	}
	
}
