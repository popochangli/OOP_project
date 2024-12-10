package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import pieces.Piece;
import pieces.PieceFactory;
import java.util.ArrayList;

public class ChessPane extends GridPane {

	private final ArrayList<ChessCell> allCells;
	private int chessPaneWidth;
	private int chessPaneHeight;

	public ChessPane(int width, int height, ArrayList<String> pieces, double panelSize) {
		this.setHgap(8.0);
		this.setVgap(8.0);
		this.setPadding(new Insets(8.0));
		this.setAlignment(Pos.CENTER);

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

					if ("wall".equals(pieceType)) {
						// Configure the cell as a wall
						cell.setWall(true);
					} else if ("blank".equals(pieceType)) {
						cell.clearPiece();
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

}
