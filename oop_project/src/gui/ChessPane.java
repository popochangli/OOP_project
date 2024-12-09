package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChessPane extends GridPane {

	private ArrayList<TicTacToeCell> allCells;
	private static final Map<String, Image> imageCache = new HashMap<>();

	public ChessPane(int width, int height, ArrayList<String> pieces, double panelSize) {
		this.setHgap(8.0);
		this.setVgap(8.0);
		this.setPadding(new Insets(8.0));
		this.setAlignment(Pos.CENTER);

		// Set the preferred size based on panelSize
		this.setPrefSize(panelSize, panelSize);
		int cellSize = Math.min((int) panelSize / width, (int) panelSize / height);
		this.setPrefSize(cellSize * width, cellSize * height);
		this.setMaxSize(cellSize * width, cellSize * height);
		this.setMinSize(cellSize * width, cellSize * height);

		this.allCells = new ArrayList<>();

		int l = 0;

		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				TicTacToeCell cell = new TicTacToeCell(i, j);
				cell.setPrefSize(cellSize, cellSize);

				if (l < pieces.size()) {
					String piece = pieces.get(l);
					String imagePath = "piece/" + piece + "B.png";
					Image pieceImage = loadImage(imagePath);
					if (pieceImage != null) {
						cell.draw(pieceImage, Color.GREEN, cellSize);
					}
				} else {
					cell.draw(null, Color.GREEN, cellSize);
				}

				this.allCells.add(cell);
				this.add(cell, i, j);
				l++;
			}
		}
	}

	private Image loadImage(String imagePath) {
		if (!imageCache.containsKey(imagePath)) {
			imageCache.put(imagePath, new Image(ClassLoader.getSystemResource(imagePath).toString()));
		}
		return imageCache.get(imagePath);
	}

	public ArrayList<TicTacToeCell> getAllCells() {
		return this.allCells;
	}
}
