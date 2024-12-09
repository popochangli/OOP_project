package gui;

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
			if (pieceImage == null  || pieceType == null)
				return; // Only allow dragging if there is a piece
			
			 // Allow drag only if the piece belongs to the white team
	        char pieceColor = pieceType.charAt(pieceType.length() - 1);
	        if (pieceColor != 'W') {
	            System.out.println("Only white pieces can move.");
	            event.consume();
	            return; // Do not allow dragging black pieces
	        }
			
			Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putImage(pieceImage);
			content.putString(pieceType);
			db.setContent(content);

			// Temporarily clear the piece from the source cell
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

		// Drop on target cell
		/*
		this.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasImage() && db.hasString()) {
				String incomingPieceType = db.getString();
				char incomingColor = incomingPieceType.charAt(incomingPieceType.length() - 1);

				if (this.hasPiece()) {
					String currentPieceType = this.getPieceType();
					char currentColor = currentPieceType.charAt(currentPieceType.length() - 1);

					if (currentColor != incomingColor) {
						System.out.println("Drop succeeded: Opposite team piece replaced.");
						String newType = currentPieceType.substring(0, currentPieceType.length() - 1) + incomingColor;
						Image updatedImage = new Image(
								ClassLoader.getSystemResource("piece/" + newType + ".png").toString());
						draw(updatedImage, newType);
						success = true;
					} else {
						System.out.println("Drop failed: Same team pieces.");
					}
				} else {
					System.out.println("Drop succeeded: Moved piece to empty cell.");
					draw(db.getImage(), incomingPieceType);
					success = true;
				}
			}

			event.setDropCompleted(success);
			event.consume();
		});
		*/
		this.setOnDragDropped(event -> {
		    Dragboard db = event.getDragboard();
		    boolean success = false;

		    if (db.hasImage() && db.hasString()) {
		        String incomingPieceType = db.getString();
		        ChessPane chessPane = (ChessPane) this.getParent(); // Assuming the parent is ChessPane

		        // Get source and target coordinates
		        TicTacToeCell sourceCell = (TicTacToeCell) event.getGestureSource();
		        int startX = GridPane.getColumnIndex(sourceCell);
		        int startY = GridPane.getRowIndex(sourceCell);
		        int targetX = GridPane.getColumnIndex(this);
		        int targetY = GridPane.getRowIndex(this);

		        // Validate the move
		        if (validateMove(incomingPieceType, startX, startY, targetX, targetY, chessPane)) {
		            // Check if the target cell is occupied
		            if (this.hasPiece()) {
		                String currentPieceType = this.getPieceType();
		                char currentColor = currentPieceType.charAt(currentPieceType.length() - 1);
		                char incomingColor = incomingPieceType.charAt(incomingPieceType.length() - 1);

		                if (currentColor != incomingColor) {
		                    // Replace with enemy piece
		                    System.out.println("Drop succeeded: Opposite team piece replaced.");
		                    String newType = currentPieceType.substring(0, currentPieceType.length() - 1) + incomingColor;
		                    Image updatedImage = new Image(
		                            ClassLoader.getSystemResource("piece/" + newType + ".png").toString());
		                    draw(updatedImage, newType);
		                    success = true;
		                } else {
		                    System.out.println("Drop failed: Same team pieces.");
		                }
		            } else {
		                // Move to empty cell
		                System.out.println("Drop succeeded: Moved piece to empty cell.");
		                draw(db.getImage(), incomingPieceType);
		                success = true;
		            }
		        } else {
		            System.out.println("Invalid move for " + incomingPieceType);
		        }
		    }

		    event.setDropCompleted(success);
		    event.consume();
		});


		// Drag done on source cell
		this.setOnDragDone(event -> {
			System.out.println("TransferMode: " + event.getTransferMode());
			if (event.getTransferMode() == TransferMode.MOVE) {
				// Clear the piece only if the transfer mode is MOVE
				clearPiece();
			} else {
				// Revert the piece if the transfer was not MOVE
				Dragboard db = event.getDragboard();
				if (db.hasImage() && db.hasString()) {
					draw(db.getImage(), db.getString());
				}
			}
			event.consume();
		});

	}
	
	private boolean validateMove(String pieceType, int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
	    int dx = targetX - startX;
	    int dy = targetY - startY;
	    boolean isEnemy = chessPane.getCell(targetX, targetY).hasPiece() &&
	                      chessPane.getCell(targetX, targetY).getPieceType().charAt(pieceType.length() - 1) != pieceType.charAt(pieceType.length() - 1);

	    switch (pieceType) {
	        case "pawnW":
	            // Pawn moves one square forward (y-1) or diagonally to attack
	            if (dx == 0 && dy == -1 && !chessPane.getCell(targetX, targetY).hasPiece()) {
	                return true; // Forward to empty
	            }
	            if (Math.abs(dx) == 1 && dy == -1 && isEnemy) {
	                return true; // Diagonal attack
	            }
	            break;

	        case "rookW":
	            // Rook moves in a straight line
	            if ((dx == 0 || dy == 0) && isPathClear(startX, startY, targetX, targetY, chessPane)) {
	                return true; // Straight path is clear
	            }
	            break;

	        case "knightW":
	            // Knight moves in an L-shape
	            if ((Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2)) {
	                return true; // Valid knight move
	            }
	            break;

	        case "bishopW":
	            // Bishop moves diagonally
	            if (Math.abs(dx) == Math.abs(dy) && isPathClear(startX, startY, targetX, targetY, chessPane)) {
	                return true; // Diagonal path is clear
	            }
	            break;

	        case "queenW":
	            // Queen moves straight or diagonally
	            if ((dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy)) &&
	                isPathClear(startX, startY, targetX, targetY, chessPane)) {
	                return true; // Path is clear
	            }
	            break;

	        case "kingW":
	            // King moves one square in any direction
	            if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
	                return true; // One square move
	            }
	            break;

	        default:
	            break;
	    }

	    return false; // Invalid move
	}
	
	private boolean isPathClear(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
	    int dx = Integer.compare(targetX, startX); // Step in x direction (-1, 0, 1)
	    int dy = Integer.compare(targetY, startY); // Step in y direction (-1, 0, 1)

	    int x = startX + dx;
	    int y = startY + dy;

	    while (x != targetX || y != targetY) {
	        if (chessPane.getCell(x, y).hasPiece()) {
	            return false; // Path blocked
	        }
	        x += dx;
	        y += dy;
	    }

	    return true; // Path is clear
	}
	
	
	
}