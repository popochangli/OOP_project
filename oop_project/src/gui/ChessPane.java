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
		super();
		this.allCells = new ArrayList<TicTacToeCell>();
		this.setHgap(8);
		this.setVgap(8);
		this.setPadding(new Insets(8));
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(500);
		this.setPrefHeight(500);
		int cellSize = Math.min(500/width, 500/height);
		this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		for(int i = 0;i<width;i++) {
			for(int j =0;j<height;j++) {
				TicTacToeCell cell = new TicTacToeCell(i,j);
				cell.setPrefSize(cellSize, cellSize);
				cell.draw(new Image(ClassLoader.getSystemResource("piece/kingB.png").toString()), Color.GREEN, cellSize);
				this.allCells.add(cell);
				this.add(cell, i, j);
			}
		}
	}

	public ArrayList<TicTacToeCell> getAllCells() {
		return allCells;
	}
	
	
}
