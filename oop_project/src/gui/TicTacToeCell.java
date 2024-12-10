package gui;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.GameLogic;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class TicTacToeCell extends Pane {

	private Image pieceImage;
	private Color cellColor;
	private boolean isOccupied;
	private String pieceType; // e.g., "rookW", "kingB"

	public TicTacToeCell(int x, int y) {
		super();
		this.cellColor = (x + y) % 2 == 0 ? Color.BEIGE : Color.BROWN;
		this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
		initializeDragAndDrop();
	}

	public void draw(Image pieceImage, String pieceType) {
		this.pieceImage = pieceImage;
		this.isOccupied = true;
		this.pieceType = pieceType;

		double cellWidth = this.getPrefWidth(); // Get the preferred width of the cell
		double cellHeight = this.getPrefHeight(); // Get the preferred height of the cell

		BackgroundImage bgImg = new BackgroundImage(pieceImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, new BackgroundSize(cellWidth, cellHeight, false, false, false, false) // Scale
																													// to
																													// fit
																													// cell

		);

		this.setBackground(
				new Background(new BackgroundFill[] { new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
						new BackgroundImage[] { bgImg }));

	}

	public void clearPiece() {
		this.pieceImage = null;
		this.isOccupied = false;
		this.pieceType = null;
		this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public boolean hasPiece() {
		return isOccupied;
	}

	public String getPieceType() {
		return pieceType;
	}

	private void initializeDragAndDrop() {
		// Drag detected on source cell
		this.setOnDragDetected(event -> {
			if (pieceImage == null || pieceType == null || !isWhitePiece(pieceType)) {
				System.out.println("Only white pieces can move.");
				event.consume();
				return;
			}

			Dragboard db = startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(pieceImage);
			content.putString(pieceType);
			db.setContent(content);

			highlightValidMoves();
			clearPiece();
			event.consume();
		});

		// Drag over target cell
		this.setOnDragOver(event -> {
			if (event.getDragboard().hasImage()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		this.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasImage() && db.hasString()) {
				ChessPane chessPane = (ChessPane) getParent();
				TicTacToeCell sourceCell = (TicTacToeCell) event.getGestureSource();
				int startX = GridPane.getColumnIndex(sourceCell);
				int startY = GridPane.getRowIndex(sourceCell);
				int targetX = GridPane.getColumnIndex(this);
				int targetY = GridPane.getRowIndex(this);

				if (validateMove(db.getString(), startX, startY, targetX, targetY, chessPane)) {
					processMove(db.getImage(), db.getString());
					success = true;

					GameLogic.getPlayerTimer(0).incrementMove(1); // Assuming player 0 for simplicity
					Platform.runLater(() -> {
						GameLogic.getInstance().getTimerPane()[0].setMove(GameLogic.getPlayerTimer(0).getMove());
					});
				} else {
					System.out.println("Invalid move for " + db.getString());
				}
			}

			event.setDropCompleted(success);
			event.consume();
		});

		// Drag done on source cell
		this.setOnDragDone(event -> {
			clearHighlights();
			if (event.getTransferMode() != TransferMode.MOVE) {
				revertPiece(event.getDragboard());
			}
			event.consume();
		});
	}

	private boolean isWhitePiece(String pieceType) {
		return pieceType.endsWith("W");
	}

	private void highlightValidMoves() {
		ChessPane chessPane = (ChessPane) getParent();
		int startX = GridPane.getColumnIndex(this);
		int startY = GridPane.getRowIndex(this);

//        if (startX == null || startY == null) {
//            System.out.println("Source cell coordinates could not be determined.");
//            return;
//        }

		for (int x = 0; x < chessPane.getChessPaneWidth(); x++) {
			for (int y = 0; y < chessPane.getChessPaneHeight(); y++) {
				TicTacToeCell targetCell = chessPane.getCell(x, y);
				if (targetCell != null && validateMove(pieceType, startX, startY, x, y, chessPane)) {
					if (!targetCell.hasPiece() || !isWhitePiece(targetCell.getPieceType())) {
						targetCell.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
								CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					}
				}
			}
		}
	}

	private void clearHighlights() {
		ChessPane chessPane = (ChessPane) getParent();
		for (int x = 0; x < chessPane.getChessPaneWidth(); x++) {
			for (int y = 0; y < chessPane.getChessPaneHeight(); y++) {
				TicTacToeCell targetCell = chessPane.getCell(x, y);
				if (targetCell != null) {
					targetCell.setBorder(null);
				}
			}
		}
	}

	private void revertPiece(Dragboard db) {
		if (db.hasImage() && db.hasString()) {
			draw(db.getImage(), db.getString());
		}
	}

	private void processMove(Image pieceImage, String pieceType) {
		if (hasPiece()) {
			char currentColor = getPieceType().charAt(getPieceType().length() - 1);
			char incomingColor = pieceType.charAt(pieceType.length() - 1);
			if (currentColor != incomingColor) {
				System.out.println("Drop succeeded: Opposite team piece replaced.");
				draw(pieceImage, pieceType);
			} else {
				System.out.println("Drop failed: Same team pieces.");
			}
		} else {
			System.out.println("Drop succeeded: Moved piece to empty cell.");
			draw(pieceImage, pieceType);
		}
	}

	private boolean validateMove(String pieceType, int startX, int startY, int targetX, int targetY,
			ChessPane chessPane) {
		int dx = targetX - startX;
		int dy = targetY - startY;

		switch (pieceType) {
		case "pawnW":
			return dx == 0 && dy == -1 && !chessPane.getCell(targetX, targetY).hasPiece();
		case "rookW":
			return (dx == 0 || dy == 0) && isPathClear(startX, startY, targetX, targetY, chessPane);
		case "knightW":
			return (Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
		case "bishopW":
			return Math.abs(dx) == Math.abs(dy) && isPathClear(startX, startY, targetX, targetY, chessPane);
		case "queenW":
			return (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy))
					&& isPathClear(startX, startY, targetX, targetY, chessPane);
		case "kingW":
			return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
		default:
			return false;
		}
	}

	private boolean isPathClear(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
		int dx = Integer.compare(targetX, startX);
		int dy = Integer.compare(targetY, startY);

		int x = startX + dx;
		int y = startY + dy;

		while (x != targetX || y != targetY) {
			if (chessPane.getCell(x, y).hasPiece()) {
				return false;
			}
			x += dx;
			y += dy;
		}

		return true;
	}

}