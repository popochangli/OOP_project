//package gui;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.image.Image;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.Border;
//import javafx.scene.layout.BorderStroke;
//import javafx.scene.layout.BorderStrokeStyle;
//import javafx.scene.layout.BorderWidths;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.GridPane;
//import javafx.scene.paint.Color;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ChessPane extends GridPane {
//
//	private ArrayList<TicTacToeCell> allCells;
//	private static final Map<String, Image> imageCache = new HashMap<>();
//	private int chessPaneWidth;
//	private int chessPaneHeight;
//	
//	public ChessPane(int width, int height, ArrayList<String> pieces) {
//		this.chessPaneWidth = width;
//		this.chessPaneHeight = height;
//		
//		this.setHgap(8.0);
//		this.setVgap(8.0);
//		this.setPadding(new Insets(8.0));
//		this.setAlignment(Pos.CENTER);
//		this.setPrefWidth(500.0);
//		this.setPrefHeight(500.0);
//		int cellSize = Math.min(500 / width, 500 / height);
//		this.setPrefSize((double) (cellSize * width), (double) (cellSize * height));
//		this.setMaxSize((double) (cellSize * width), (double) (cellSize * height));
//		this.setMinSize((double) (cellSize * width), (double) (cellSize * height));
//		this.setBorder(new Border(new BorderStroke[] {
//				new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT) }));
//		this.setBackground(new Background(
//				new BackgroundFill[] { new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY) }));
//		this.allCells = new ArrayList<TicTacToeCell>();
//
//		int l = 0;
//		for (int i = 0; i < width; ++i) {
//			for (int j = 0; j < height; ++j) {
//				TicTacToeCell cell = new TicTacToeCell(i, j);
//				cell.setPrefSize((double) cellSize, (double) cellSize);
//
//				if (l < pieces.size()) {
//					String piece = pieces.get(l);
//					String imagePath = (i + j) % 2 == 0 ? "piece/" + piece + "B.png" : "piece/" + piece + "W.png";
//
//					Image pieceImage = loadImage(imagePath);
//
//					if (pieceImage != null) {
//						cell.draw(pieceImage, (i + j) % 2 == 0 ? piece + "B" : piece + "W");
//					} else {
//						cell.draw(null, null);
//					}
//				}
//
//				this.allCells.add(cell);
//				this.add(cell, i, j);
//				l++;
//			}
//		}
//
//	}
//
//	
//
//	public int getChessPaneWidth() {
//		return chessPaneWidth;
//	}
//
//
//
//	public void setChessPaneWidth(int chessPaneWidth) {
//		this.chessPaneWidth = chessPaneWidth;
//	}
//
//
//
//	public int getChessPaneHeight() {
//		return chessPaneHeight;
//	}
//
//
//
//	public void setChessPaneHeight(int chessPaneHeight) {
//		this.chessPaneHeight = chessPaneHeight;
//	}
//
//
//
//	private Image loadImage(String imagePath) {
//		if (!imageCache.containsKey(imagePath)) {
//			imageCache.put(imagePath, new Image(ClassLoader.getSystemResource(imagePath).toString()));
//		}
//		return imageCache.get(imagePath);
//	}
//
//	public ArrayList<TicTacToeCell> getAllCells() {
//		return this.allCells;
//	}
//	
//	public TicTacToeCell getCell(int x, int y) {
//	    if (x < 0 || x >= chessPaneWidth || y < 0 || y >= chessPaneHeight) {
//	        //System.out.println("Invalid cell coordinates: (" + x + ", " + y + ")");
//	        return null;
//	    }
//	    //int index = y * chessPaneWidth + x;
//	    int index = x * chessPaneHeight + y;
//	    //System.out.println("Retrieving cell at (" + x + ", " + y + ") -> Index: " + index);
//	    return allCells.get(index);
//	}
//
//	
//
//}
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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChessPane extends GridPane {

	private ArrayList<TicTacToeCell> allCells;
	private static final Map<String, Image> imageCache = new HashMap<>();
	private int chessPaneWidth;
	private int chessPaneHeight;
	
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
				cell.setPrefSize((double) cellSize, (double) cellSize);

				if (l < pieces.size()) {
					String piece = pieces.get(l);
					String imagePath = (i + j) % 2 == 0 ? "piece/" + piece + "B.png" : "piece/" + piece + "W.png";

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



	private Image loadImage(String imagePath) {
	    URL resource = ClassLoader.getSystemResource(imagePath);
	    if (resource == null) {
	        System.err.println("Resource not found: " + imagePath);
	        return null;
	    }
	    if (!imageCache.containsKey(imagePath)) {
	        imageCache.put(imagePath, new Image(resource.toString()));
	    }
	    return imageCache.get(imagePath);
	}


	public ArrayList<TicTacToeCell> getAllCells() {
		return this.allCells;
	}
	
	public TicTacToeCell getCell(int x, int y) {
	    if (x < 0 || x >= chessPaneWidth || y < 0 || y >= chessPaneHeight) {
	        //System.out.println("Invalid cell coordinates: (" + x + ", " + y + ")");
	        return null;
	    }
	    //int index = y * chessPaneWidth + x;
	    int index = x * chessPaneHeight + y;
	    //System.out.println("Retrieving cell at (" + x + ", " + y + ") -> Index: " + index);
	    return allCells.get(index);
	}

	public boolean checkWin() {
	    for (int x = 0; x < getChessPaneWidth(); x++) {
	        for (int y = 0; y < getChessPaneHeight(); y++) {
	            TicTacToeCell cell = getCell(x, y);
	            if (cell != null && cell.hasPiece() && cell.getPieceType().endsWith("B")) {
	                return false; // A black piece is still on the board
	            }
	        }
	    }
	    return true; // No black pieces left
	}

	public void announceWinner() {
	    System.out.println("Congratulations! You have won the game.");
	}


}
