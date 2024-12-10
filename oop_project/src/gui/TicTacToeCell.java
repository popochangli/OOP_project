package gui;

import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.GameLogic;
import pieces.Piece;
import pieces.PieceFactory;
import javafx.application.Platform;
import javafx.geometry.Insets;

public class TicTacToeCell extends Pane {

	private Color cellColor;
	private boolean isOccupied;
	private Piece piece;

	public TicTacToeCell(int x, int y) {
		super();
		this.cellColor = (x + y) % 2 == 0 ? Color.BEIGE : Color.BROWN;
		this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
		initializeDragAndDrop();
	}

	
	public void draw(Piece piece) {
        this.piece = piece;
        this.isOccupied = true;

        if (piece != null && piece.getImage() != null) {
            double cellWidth = this.getPrefWidth();
            double cellHeight = this.getPrefHeight();

            BackgroundImage bgImg = new BackgroundImage(piece.getImage(), BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(cellWidth, cellHeight, false, false, false, false));
            this.setBackground(new Background(
                    new BackgroundFill[] { new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
                    new BackgroundImage[] { bgImg }));
            System.out.println("Updated cell image to: " + piece.getClass().getSimpleName() + " (" + piece.getTeam() + ")");
        } else {
            this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
            System.out.println("Could not update cell image, no valid image found.");
        }
    }

	public void clearPiece() {
		this.piece = null;
		this.isOccupied = false;
		this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public boolean hasPiece() {
		return isOccupied;
	}

	public Piece getPiece() {
		return piece;
	}

	private void initializeDragAndDrop() {
		// Drag detected on source cell

		this.setOnDragDetected(event -> {
			if (piece == null || !"W".equals(piece.getTeam())) {
				System.out.println("Only white pieces can move.");
				event.consume();
				return;
			}

			System.out.println("Drag detected: Piece " + piece.getClass().getSimpleName() + " (" + piece.getTeam()
					+ ") is being moved from position (" + GridPane.getColumnIndex(this) + ", "
					+ GridPane.getRowIndex(this) + ").");

			Dragboard db = startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(piece.getImage());

			content.putString(piece.getClass().getSimpleName() + piece.getTeam()); // Add the piece type and team

			db.setContent(content);
			System.out.println("Dragboard content set: {image=" + piece.getImage() + ", type="
					+ piece.getClass().getSimpleName() + piece.getTeam() + "}");

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

				// Piece movingPiece = chessPane.createPieceFromType(db.getString(),
				// db.getImage());
				Piece movingPiece = PieceFactory.createPiece(db.getString());

				if (movingPiece != null) {
					System.out.println("Drop detected: Piece " + movingPiece.getClass().getSimpleName() + " ("
							+ movingPiece.getTeam() + ") is being dropped on position (" + targetX + ", " + targetY
							+ ").");

					// Validate and process the move
					if (validateMove(movingPiece, startX, startY, targetX, targetY, chessPane)) {
						success = processMove(movingPiece); // Process the move only if valid
						
				        //manual merge edit
						GameLogic.getPlayerTimer(0).incrementMove(1); // Assuming player 0 for simplicity
						Platform.runLater(() -> {
							GameLogic.getInstance().getTimerPane()[0].setMove(GameLogic.getPlayerTimer(0).getMove());
						});
						GameLogic.getInstance().checkGameEnd();
						
					} else {
						System.out.println("Invalid move for " + movingPiece.getClass().getSimpleName());
					}
				}
			}

			// Signal success or failure
			event.setDropCompleted(success);
			event.consume();
		});

		// Drag done on source cell
		this.setOnDragDone(event -> {
			clearHighlights();
			// if (event.getTransferMode() != TransferMode.MOVE) {
			if (event.getTransferMode() == null || event.getTransferMode() != TransferMode.MOVE) {
				revertPiece(event.getDragboard());
			}
			event.consume();
		});
	}

	private void highlightValidMoves() {
		ChessPane chessPane = (ChessPane) getParent();
		int startX = GridPane.getColumnIndex(this);
		int startY = GridPane.getRowIndex(this);

		for (int x = 0; x < chessPane.getChessPaneWidth(); x++) {
			for (int y = 0; y < chessPane.getChessPaneHeight(); y++) {
				TicTacToeCell targetCell = chessPane.getCell(x, y);

				if (targetCell != null && piece != null && piece.validateMove(startX, startY, x, y, chessPane)) {
					Piece targetPiece = targetCell.getPiece();

					if (targetPiece == null || !"W".equals(targetPiece.getTeam())) {
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
			String pieceType = db.getString();
			Piece piece = PieceFactory.createPiece(pieceType);

			if (piece != null) {
				draw(piece);
			}
		}
	}

	private boolean processMove(Piece movingPiece) {

		if (hasPiece()) {
			// Compare teams of the current piece and the moving piece
			if (!piece.getTeam().equals(movingPiece.getTeam())) {
				System.out.println("Drop succeeded: Opposite team piece replaced.");
				System.out.println("Transforming piece: " + piece.getClass().getSimpleName() + " (" + piece.getTeam()
						+ ") to team " + movingPiece.getTeam());

				// Transform the current piece
				piece.setTeam(movingPiece.getTeam()); // Change the team of the existing piece
				piece = PieceFactory.createPiece(piece.getClass().getSimpleName() + piece.getTeam());

				draw(piece); // Redraw the transformed piece (retain its type)
				return true;
			} else {
				System.out.println("Drop failed: Same team pieces.");
				return false; // Prevent further processing
			}
		} else {
			System.out.println("Drop succeeded: Moved piece to empty cell.");
			draw(movingPiece); // Place the moving piece in the empty cell
			return true;
		}
	}

	private boolean validateMove(Piece movingPiece, int startX, int startY, int targetX, int targetY,
			ChessPane chessPane) {
		return movingPiece != null && movingPiece.validateMove(startX, startY, targetX, targetY, chessPane);
	}

}