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

	public ChessPane(int width, int height, ArrayList<String> pieces) {
		this.setHgap(8.0);
		this.setVgap(8.0);
		this.setPadding(new Insets(8.0));
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(500.0);
		this.setPrefHeight(500.0);
		int cellSize = Math.min(500 / width, 500 / height);
		this.setPrefSize((double)(cellSize * width), (double)(cellSize * height));
		this.setMaxSize((double)(cellSize * width), (double)(cellSize * height));
		this.setMinSize((double)(cellSize * width), (double)(cellSize * height));
		this.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)}));
		this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)}));
		this.allCells = new ArrayList<TicTacToeCell>();

		int l = 0;
		for (int i = 0; i < width; ++i) {
		    for (int j = 0; j < height; ++j) {
		        TicTacToeCell cell = new TicTacToeCell(i, j);
		        cell.setPrefSize((double) cellSize, (double) cellSize);

		        if (l < pieces.size()) {
		            String piece = pieces.get(l);
		            String imagePath = (i + j) % 2 == 0 
		                ? "piece/" + piece + "B.png" 
		                : "piece/" + piece + "W.png";
		            
		            Image pieceImage = loadImage(imagePath);

		            if (pieceImage != null) {
		                cell.draw(pieceImage, (i + j) % 2 == 0 ? piece + "B" : piece + "W");
		            } else {
		                cell.draw(null, null);
		            }
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
