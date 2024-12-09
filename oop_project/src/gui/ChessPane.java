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

public class ChessPane extends GridPane{
	
	private ArrayList<TicTacToeCell> allCells;

	public ChessPane(int width, int height) {
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

		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				TicTacToeCell cell = new TicTacToeCell(i, j);
				cell.setPrefSize((double)cellSize, (double)cellSize);
				cell.draw(new Image(ClassLoader.getSystemResource("piece/kingB.png").toString()), Color.GREEN, cellSize);
				this.allCells.add(cell);
				this.add(cell, i, j);
			}
		}

	}

	public ArrayList<TicTacToeCell> getAllCells() {
		return this.allCells;
	}
}
