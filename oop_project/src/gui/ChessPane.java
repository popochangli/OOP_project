package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import pieces.Piece;
import pieces.PieceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChessPane extends GridPane {

	private ArrayList<ChessCell> allCells;
	private static final Map<String, Image> imageCache = new HashMap<>();
	private int chessPaneWidth;
	private int chessPaneHeight;
	
	public ChessPane(int width, int height, ArrayList<String> pieces, double panelSize) {
		this.setHgap(8.0);
        this.setVgap(8.0);
        this.setPadding(new Insets(8.0));
        this.setAlignment(Pos.CENTER);

        //manual merge edit
//        int cellSize = (int) panelSize / Math.max(width, height);
//        this.setPrefSize(cellSize * width, cellSize * height);
     		this.setPrefSize(panelSize, panelSize);
     		int cellSize = Math.min((int) panelSize / width, (int) panelSize / height);
     		this.setPrefSize(cellSize * width, cellSize * height);
     		this.setMaxSize(cellSize * width, cellSize * height);
     		this.setMinSize(cellSize * width, cellSize * height);

        this.allCells = new ArrayList<>();
        this.chessPaneWidth = width;
        this.chessPaneHeight = height;

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                ChessCell cell = new ChessCell(i, j);
                cell.setPrefSize(cellSize, cellSize);

                // Assign pieces based on input
                int index = i * height + j;
                if (index < pieces.size()) {
                	
                	
                    String pieceType = pieces.get(index);
                    //add wall edit
                    
//                    Piece piece = PieceFactory.createPiece(pieceType);
//                    if (piece != null) {
//                        cell.draw(piece);
//                    }
                    if ("wall".equals(pieceType)) {
                        // Configure the cell as a wall
                        cell.setWall(true);
                    } else {
                        // Assign regular pieces
                        Piece piece = PieceFactory.createPiece(pieceType);
                        if (piece != null) {
                            cell.draw(piece);
                        }
                    }
                }

                this.allCells.add(cell);
                this.add(cell, i, j);
            }
        }
	}


	public int getChessPaneWidth() {
		return chessPaneWidth;
	}



	public void setChessPaneWidth(int chessPaneWidth) {
		this.chessPaneWidth = chessPaneWidth;
	}



	public int getChessPaneHeight() {
		return chessPaneHeight;
	}



	public void setChessPaneHeight(int chessPaneHeight) {
		this.chessPaneHeight = chessPaneHeight;
	}



	public ArrayList<ChessCell> getAllCells() {
		return this.allCells;
	}
	
	public ChessCell getCell(int x, int y) {
        if (x < 0 || x >= chessPaneWidth || y < 0 || y >= chessPaneHeight) {
            return null;
        }
        return allCells.get(x * chessPaneHeight + y);
    }
	
	/*
	public boolean checkWin() {
	    for (int x = 0; x < getChessPaneWidth(); x++) {
	        for (int y = 0; y < getChessPaneHeight(); y++) {
	            ChessCell cell = getCell(x, y);
	            if (cell != null && cell.hasPiece()) {
	                Piece piece = cell.getPiece(); // Get the Piece object
	                if (piece != null && "B".equals(piece.getTeam())) {
	                    return false; // A black piece is still on the board
	                }
	            }
	        }
	    }
	    return true; // No black pieces left
	}

	public void announceWinner() {
	    System.out.println("Congratulations! You have won the game.");
	}
	*/

}
