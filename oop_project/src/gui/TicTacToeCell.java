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
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class TicTacToeCell extends Pane {

	//private Image pieceImage;
	private Color cellColor;
	private boolean isOccupied;
	//private String pieceType; // e.g., "rookW", "kingB"
	private Piece piece; 
	
	public TicTacToeCell(int x, int y) {
		super();
		this.cellColor = (x + y) % 2 == 0 ? Color.BEIGE : Color.BROWN;
		this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
		initializeDragAndDrop();
	}

	
//	public void draw(Piece piece) {
////		this.pieceImage = null;
////		this.pieceType = null;
//	    this.piece = piece;
//	    this.isOccupied = true;
//
//	    double cellWidth = this.getPrefWidth();
//	    double cellHeight = this.getPrefHeight();
//
//	    BackgroundImage bgImg = new BackgroundImage(piece.getImage(), BackgroundRepeat.NO_REPEAT, 
//	                                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//	                                                new BackgroundSize(cellWidth, cellHeight, false, false, false, false));
//	    this.setBackground(new Background(new BackgroundFill[] { new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
//	                                        new BackgroundImage[] { bgImg }));
//	}
//	public void draw(Piece piece) {
//	    this.piece = piece;
//	    this.isOccupied = true;
//
//	    double cellWidth = this.getPrefWidth();
//	    double cellHeight = this.getPrefHeight();
//
//	    // Fetch the correct image from the piece
//	    Image pieceImage = piece.getImage();
//	    
//	    BackgroundImage bgImg = new BackgroundImage(pieceImage, BackgroundRepeat.NO_REPEAT, 
//	                                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//	                                                new BackgroundSize(cellWidth, cellHeight, false, false, false, false));
//	    this.setBackground(new Background(new BackgroundFill[] { 
//	                                            new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
//	                                        new BackgroundImage[] { bgImg }));
//	    System.out.println("Updated cell image to: " + piece.getClass().getSimpleName() + " (" + piece.getTeam() + ")");
//	}
	public void draw(Piece piece) {
	    this.piece = piece;
	    this.isOccupied = true;

	    double cellWidth = this.getPrefWidth();
	    double cellHeight = this.getPrefHeight();

	    // Dynamically generate the image path
	    String imagePath = "/piece/" + piece.getClass().getSimpleName().toLowerCase() + piece.getTeam() + ".png";
	    Image pieceImage;

	    try {
	        pieceImage = new Image(getClass().getResourceAsStream(imagePath));
	    } catch (Exception e) {
	        System.out.println("Failed to load image from path: " + imagePath);
	        pieceImage = null; // Set to null or fallback to a default image if required
	    }

	    if (pieceImage != null) {
	        BackgroundImage bgImg = new BackgroundImage(pieceImage, BackgroundRepeat.NO_REPEAT, 
	                                                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
	                                                    new BackgroundSize(cellWidth, cellHeight, false, false, false, false));
	        this.setBackground(new Background(new BackgroundFill[] { 
	                                                new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
	                                            new BackgroundImage[] { bgImg }));
	        System.out.println("Updated cell image to: " + piece.getClass().getSimpleName() + " (" + piece.getTeam() + ") from path: " + imagePath);
	    } else {
	        System.out.println("Could not update cell image, no valid image found.");
	        this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
	    }
	}



	public void clearPiece() {
//		this.pieceImage = null;
//		this.pieceType = null;
	    this.piece = null;
	    this.isOccupied = false;
	    this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
	}


	public boolean hasPiece() {
		return isOccupied;
	}

//	public String getPieceType() {
//		return pieceType;
//	}

	public Piece getPiece() {
	    return piece;
	}
	
	private void initializeDragAndDrop() {
		// Drag detected on source cell
//		this.setOnDragDetected(event -> {
//			if (pieceImage == null || pieceType == null || !isWhitePiece(pieceType)) {
//				System.out.println("Only white pieces can move.");
//				event.consume();
//				return;
//			}
//
//			Dragboard db = startDragAndDrop(TransferMode.MOVE);
//			ClipboardContent content = new ClipboardContent();
//			content.putImage(pieceImage);
//			content.putString(pieceType);
//			db.setContent(content);
//
//			highlightValidMoves();
//			clearPiece();
//			event.consume();
//		});
		this.setOnDragDetected(event -> {
	        if (piece == null || !"W".equals(piece.getTeam())) {
	            System.out.println("Only white pieces can move.");
	            event.consume();
	            return;
	        }
	        
	        System.out.println("Drag detected: Piece " + piece.getClass().getSimpleName() +
                    " (" + piece.getTeam() + ") is being moved from position (" +
                    GridPane.getColumnIndex(this) + ", " + GridPane.getRowIndex(this) + ").");
	        
	        Dragboard db = startDragAndDrop(TransferMode.MOVE);
	        ClipboardContent content = new ClipboardContent();
	        content.putImage(piece.getImage());
	        
	        content.putString(piece.getClass().getSimpleName() + piece.getTeam()); // Add the piece type and team

	        
	        db.setContent(content);
	        System.out.println("Dragboard content set: {image=" + piece.getImage() +
                    ", type=" + piece.getClass().getSimpleName() + piece.getTeam() + "}");

	        
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

//		this.setOnDragDropped(event -> {
//			Dragboard db = event.getDragboard();
//			boolean success = false;
//
//			if (db.hasImage() && db.hasString()) {
//				ChessPane chessPane = (ChessPane) getParent();
//				TicTacToeCell sourceCell = (TicTacToeCell) event.getGestureSource();
//				int startX = GridPane.getColumnIndex(sourceCell);
//				int startY = GridPane.getRowIndex(sourceCell);
//				int targetX = GridPane.getColumnIndex(this);
//				int targetY = GridPane.getRowIndex(this);
//
////				if (validateMove(db.getString(), startX, startY, targetX, targetY, chessPane)) {
////					processMove(db.getImage(), db.getString());
////					success = true;
////				} else {
////					System.out.println("Invalid move for " + db.getString());
////				}
//				if (db.hasImage() && db.hasString()) {
//				    // Recreate the Piece object from the Dragboard data
//				    //Piece movingPiece = createPieceFromType(db.getString(), db.getImage());
//					// Access the parent ChessPane instance
//				    //ChessPane chessPane = (ChessPane) getParent();
//
//				    // Recreate the Piece object from the Dragboard data
//				    
//					Piece movingPiece = chessPane.createPieceFromType(db.getString(), db.getImage());
//				    
//				    if (movingPiece == null) {
//				        System.out.println("Failed to create piece from dragboard data.");
//				        event.setDropCompleted(false);
//				        event.consume();
//				        return;
//				    }
//				    
//				    System.out.println("Drop detected: Piece " + movingPiece.getClass().getSimpleName() +
//	                           " (" + movingPiece.getTeam() + ") is being dropped on position (" +
//	                           targetX + ", " + targetY + ").");
//
//				    // Validate the move using the Piece object
//				    if (validateMove(movingPiece, startX, startY, targetX, targetY, chessPane)) {
//				        processMove(movingPiece);
//				        success = processMove(movingPiece); //true;
//				    } else {
//				        System.out.println("Invalid move for " + movingPiece.getClass().getSimpleName());
//				    }
//				}
//
//			}
//
//			event.setDropCompleted(success);
//			event.consume();
//		});
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

		        Piece movingPiece = chessPane.createPieceFromType(db.getString(), db.getImage());

		        if (movingPiece != null) {
		            System.out.println("Drop detected: Piece " + movingPiece.getClass().getSimpleName() +
		                               " (" + movingPiece.getTeam() + ") is being dropped on position (" +
		                               targetX + ", " + targetY + ").");

		            // Validate and process the move
		            if (validateMove(movingPiece, startX, startY, targetX, targetY, chessPane)) {
		                success = processMove(movingPiece); // Process the move only if valid
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
			//if (event.getTransferMode() != TransferMode.MOVE) {
			if (event.getTransferMode() == null || event.getTransferMode() != TransferMode.MOVE) {
				revertPiece(event.getDragboard());
			}
			event.consume();
		});
	}

//	private boolean isWhitePiece(String pieceType) {
//		return pieceType.endsWith("W");
//	}

	private void highlightValidMoves() {
		ChessPane chessPane = (ChessPane) getParent();
		int startX = GridPane.getColumnIndex(this);
		int startY = GridPane.getRowIndex(this);

//        if (startX == null || startY == null) {
//            System.out.println("Source cell coordinates could not be determined.");
//            return;
//        }

//		for (int x = 0; x < chessPane.getChessPaneWidth(); x++) {
//			for (int y = 0; y < chessPane.getChessPaneHeight(); y++) {
//				TicTacToeCell targetCell = chessPane.getCell(x, y);
//				if (targetCell != null && validateMove(pieceType, startX, startY, x, y, chessPane)) {
//					if (!targetCell.hasPiece() || !isWhitePiece(targetCell.getPieceType())) {
//						targetCell.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
//								CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//					}
//				}
//			}
//		}
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

//	private void revertPiece(Dragboard db) {
//		if (db.hasImage() && db.hasString()) {
//			draw(db.getImage(), db.getString());
//		}
//	}
	private void revertPiece(Dragboard db) {
	    if (db.hasImage() && db.hasString()) {
	        String pieceType = db.getString();
	        Image pieceImage = db.getImage();

	        // Recreate the Piece object
	        //Piece piece = createPieceFromType(pieceType, pieceImage);
	     // Access the parent ChessPane instance
	        ChessPane chessPane = (ChessPane) getParent();

	        // Recreate the Piece object using the ChessPane's createPieceFromType method
	        Piece piece = chessPane.createPieceFromType(pieceType, pieceImage);
	        if (piece != null) {
	            draw(piece);
	        }
	    }
	}
	
	



//	private void processMove(Image pieceImage, String pieceType) {
//		if (hasPiece()) {
//			char currentColor = getPieceType().charAt(getPieceType().length() - 1);
//			char incomingColor = pieceType.charAt(pieceType.length() - 1);
//			if (currentColor != incomingColor) {
//				System.out.println("Drop succeeded: Opposite team piece replaced.");
//				draw(pieceImage, pieceType);
//			} else {
//				System.out.println("Drop failed: Same team pieces.");
//			}
//		} else {
//			System.out.println("Drop succeeded: Moved piece to empty cell.");
//			draw(pieceImage, pieceType);
//		}
//		
//		ChessPane chessPane = (ChessPane) getParent();
//	    if (chessPane.checkWin()) {
//	        chessPane.announceWinner();
//	    }
//	}
	
//	private void processMove(Piece movingPiece) {
//	    if (hasPiece()) {
//	        // Compare teams of the current piece and the moving piece
//	        if (!piece.getTeam().equals(movingPiece.getTeam())) {
//	            System.out.println("Drop succeeded: Opposite team piece replaced.");
//	            draw(movingPiece); // Replace the existing piece with the moving piece
//	        } else {
//	            System.out.println("Drop failed: Same team pieces.");
//	        }
//	    } else {
//	        System.out.println("Drop succeeded: Moved piece to empty cell.");
//	        draw(movingPiece); // Place the moving piece in the empty cell
//	    }
//
//	    // Check for winning condition
//	    ChessPane chessPane = (ChessPane) getParent();
//	    if (chessPane.checkWin()) {
//	        chessPane.announceWinner();
//	    }
//	}
//	private void processMove(Piece movingPiece) {
//	    ChessPane chessPane = (ChessPane) getParent(); // Access ChessPane
//
//	    if (hasPiece()) {
//	        // Compare teams of the current piece and the moving piece
//	        if (!piece.getTeam().equals(movingPiece.getTeam())) {
////	            System.out.println("Drop succeeded: Opposite team piece replaced.");
////	            //movingPiece.setTeam(piece.getTeam()); // Update team of the moving piece to match the current cell's team
////	            //draw(movingPiece); // Replace the existing piece with the moving piece
////	            System.out.println("Transforming piece: " + piece.getClass().getSimpleName() + 
////	                    " (" + piece.getTeam() + ") to team " + movingPiece.getTeam());
////
////	            piece.setTeam(movingPiece.getTeam()); // Change the team of the existing piece
////	            draw(piece); // Draw the transformed piece (retain its type)
//	        	System.out.println("Drop succeeded: Opposite team piece replaced.");
//	            System.out.println("Transforming piece: " + piece.getClass().getSimpleName() + 
//	                               " (" + piece.getTeam() + ") to team " + movingPiece.getTeam());
//
//	            // Create a new piece of the same type but with the moving piece's team
//	            Piece transformedPiece = chessPane.createPieceFromType(
//	                piece.getClass().getSimpleName() + movingPiece.getTeam(),
//	                piece.getImage() // You may need a new image for the transformed team
//	            );
//
//	            if (transformedPiece != null) {
//	                draw(transformedPiece); // Replace the existing piece with the transformed one
//	                this.piece = transformedPiece; // Update the internal state
//	            } else {
//	                System.out.println("Failed to transform the piece.");
//	                return;
//	            }
//	        } else {
//	            System.out.println("Drop failed: Same team pieces.");
//	            return; // Prevent further processing
//	        }
//	    } else {
//	        System.out.println("Drop succeeded: Moved piece to empty cell.");
//	        movingPiece.setTeam(getTeamFromCell(chessPane)); // Update team to match the team of the cell
//	        draw(movingPiece); // Place the moving piece in the empty cell
//	    }
//
//	    // Check for winning condition
//	    if (chessPane.checkWin()) {
//	        chessPane.announceWinner();
//	    }
//	}
	private boolean processMove(Piece movingPiece) {
	    ChessPane chessPane = (ChessPane) getParent(); // Access ChessPane

	    if (hasPiece()) {
	        // Compare teams of the current piece and the moving piece
	        if (!piece.getTeam().equals(movingPiece.getTeam())) {
	            System.out.println("Drop succeeded: Opposite team piece replaced.");
	            System.out.println("Transforming piece: " + piece.getClass().getSimpleName() + 
	                    " (" + piece.getTeam() + ") to team " + movingPiece.getTeam());

	            // Transform the current piece
	            piece.setTeam(movingPiece.getTeam()); // Change the team of the existing piece
	            piece = chessPane.createPieceFromType(piece.getClass().getSimpleName() + piece.getTeam(), piece.getImage());
	            draw(piece); // Redraw the transformed piece (retain its type)
	            return true;
	        } else {
	            System.out.println("Drop failed: Same team pieces.");
	           // revertPieceToSource(movingPiece); // Restore the source cell
	            return false; // Prevent further processing
	        }
	    } else {
	        System.out.println("Drop succeeded: Moved piece to empty cell.");
	        draw(movingPiece); // Place the moving piece in the empty cell
	        return true;
	    }

//	    // Check for winning condition
//	    if (chessPane.checkWin()) {
//	        chessPane.announceWinner();
//	    }
	}
	
//	private void revertPieceToSource(Piece movingPiece) {
//	    ChessPane chessPane = (ChessPane) getParent();
//	    TicTacToeCell sourceCell = chessPane.getCell(
//	        GridPane.getColumnIndex(this),
//	        GridPane.getRowIndex(this)
//	    );
//
//	    if (sourceCell != null) {
//	        System.out.println("Reverting piece: " + movingPiece.getClass().getSimpleName() + " (" + movingPiece.getTeam() + ")");
//	        sourceCell.draw(movingPiece); // Redraw the original piece
//	    } else {
//	        System.out.println("Source cell not found for piece restoration.");
//	    }
//	}


	//WRONG
//	private String getTeamFromCell(ChessPane chessPane) {
//	    int cellX = GridPane.getColumnIndex(this);
//	    int cellY = GridPane.getRowIndex(this);
//
//	    // Example logic to determine team based on position
//	    return (cellX + cellY) % 2 == 0 ? "B" : "W";
//	}


//	private boolean validateMove(String pieceType, int startX, int startY, int targetX, int targetY,
//			ChessPane chessPane) {
//		int dx = targetX - startX;
//		int dy = targetY - startY;
//
//		switch (pieceType) {
//		case "pawnW":
//			return dx == 0 && dy == -1 && !chessPane.getCell(targetX, targetY).hasPiece();
//		case "rookW":
//			return (dx == 0 || dy == 0) && isPathClear(startX, startY, targetX, targetY, chessPane);
//		case "knightW":
//			return (Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
//		case "bishopW":
//			return Math.abs(dx) == Math.abs(dy) && isPathClear(startX, startY, targetX, targetY, chessPane);
//		case "queenW":
//			return (dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy))
//					&& isPathClear(startX, startY, targetX, targetY, chessPane);
//		case "kingW":
//			return Math.abs(dx) <= 1 && Math.abs(dy) <= 1;
//		default:
//			return false;
//		}
//	}
	
	private boolean validateMove(Piece movingPiece, int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
	    return movingPiece != null && movingPiece.validateMove(startX, startY, targetX, targetY, chessPane);
	}


//	private boolean isPathClear(int startX, int startY, int targetX, int targetY, ChessPane chessPane) {
//		int dx = Integer.compare(targetX, startX);
//		int dy = Integer.compare(targetY, startY);
//
//		int x = startX + dx;
//		int y = startY + dy;
//
//		while (x != targetX || y != targetY) {
//			if (chessPane.getCell(x, y).hasPiece()) {
//				return false;
//			}
//			x += dx;
//			y += dy;
//		}
//
//		return true;
//	}

}