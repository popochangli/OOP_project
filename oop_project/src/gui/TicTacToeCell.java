//package gui;
//
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import logic.GameLogic;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//
//public class TicTacToeCell extends Pane {
//	private boolean isDrawn;
//	private Color baseColor;
//	private int xPosition;
//	private int yPosition;
//	private String oURL = "o.png";
//	private String xURL = "x.png";
//	private Button button = new Button();
//
//	public TicTacToeCell(int x, int y) {
//		this.setxPosition(x);
//		this.setyPosition(y);
//		this.setBaseColor(Color.MOCCASIN);
//		this.initializeCellColor();
//		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent e) {
//				TicTacToeCell.this.onClickHandler();
//			}
//		});
//	}
//
//	private void onClickHandler() {
//		if (!GameLogic.getInstance().isGameEnd() && GameLogic.getInstance().isGameStart() && !this.isDrawn) {
//			if (GameLogic.getInstance().isOturn()) {
//				this.draw(new Image(ClassLoader.getSystemResource(this.oURL).toString()), Color.AQUA, 150);
//				GameLogic.getInstance().beginTurns(1);
//			} else {
//				this.draw(new Image(ClassLoader.getSystemResource(this.xURL).toString()), Color.YELLOW, 150);
//				GameLogic.getInstance().beginTurns(0);
//			}
//
//			GameLogic.getInstance().drawNumber(this.xPosition, this.yPosition);
//		}
//
//	}
//
//	public void draw(Image image, Color backgroundColor, int size) {
//		this.getChildren().add(this.button);
//		this.getChildren().remove(this.button);
//		BackgroundFill bgFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
//		BackgroundFill[] bgFillA = new BackgroundFill[]{bgFill};
//		BackgroundSize bgSize = new BackgroundSize((double)size, (double)size, false, false, true, false);
//		this.setBackground(new Background(bgFillA));
//		BackgroundImage bgImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
//		BackgroundImage[] bgImgA = new BackgroundImage[]{bgImg};
//		this.setBackground(new Background(bgFillA, bgImgA));
//		this.setDrawn(true);
//	}
//
//	public void initializeCellColor() {
//		this.setDrawn(false);
//		this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(this.baseColor, (CornerRadii)null, (Insets)null)}));
//	}
//
//	public boolean isDrawn() {
//		return this.isDrawn;
//	}
//
//	public void setDrawn(boolean isDrawn) {
//		this.isDrawn = isDrawn;
//	}
//
//	public int getxPosition() {
//		return this.xPosition;
//	}
//
//	public void setxPosition(int xPosition) {
//		this.xPosition = xPosition;
//	}
//
//	public int getyPosition() {
//		return this.yPosition;
//	}
//
//	public void setyPosition(int yPosition) {
//		this.yPosition = yPosition;
//	}
//
//	public Color getBaseColor() {
//		return this.baseColor;
//	}
//
//	public void setBaseColor(Color baseColor) {
//		this.baseColor = baseColor;
//	}
//}

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
//	private boolean isDrawn;
//	private Color baseColor;
//	private int xPosition;
//	private int yPosition;
//	private String oURL = "o.png";
//	private String xURL = "x.png";
//	private Button button = new Button();
	private Image pieceImage;
    private Color cellColor;
    private boolean isOccupied;
    private String pieceType; // e.g., "rookW", "kingB"
//	public TicTacToeCell(int x, int y) {
//		this.setxPosition(x);
//		this.setyPosition(y);
//		this.setBaseColor(Color.MOCCASIN);
//		this.initializeCellColor();
//		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent e) {
//				TicTacToeCell.this.onClickHandler();
//			}
//		});
//	}
	public TicTacToeCell(int x, int y) {
        super();
        this.cellColor = (x + y) % 2 == 0 ? Color.BEIGE : Color.BROWN;
        this.setBackground(new Background(new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY)));
        initializeDragAndDrop();
    }

	/*private void onClickHandler() {
		if (!GameLogic.getInstance().isGameEnd() && GameLogic.getInstance().isGameStart() && !this.isDrawn) {
			if (GameLogic.getInstance().isOturn()) {
				this.draw(new Image(ClassLoader.getSystemResource(this.oURL).toString()), Color.AQUA, 150);
				GameLogic.getInstance().beginTurns(1);
			} else {
				this.draw(new Image(ClassLoader.getSystemResource(this.xURL).toString()), Color.YELLOW, 150);
				GameLogic.getInstance().beginTurns(0);
			}

			GameLogic.getInstance().drawNumber(this.xPosition, this.yPosition);
		}

	}*/

//	public void placePiece(Image image, Color backgroundColor, int size) {
//		this.getChildren().add(this.button);
//		this.getChildren().remove(this.button);
//		BackgroundFill bgFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
//		BackgroundFill[] bgFillA = new BackgroundFill[]{bgFill};
//		BackgroundSize bgSize = new BackgroundSize((double)size, (double)size, false, false, true, false);
//		this.setBackground(new Background(bgFillA));
//		BackgroundImage bgImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
//		BackgroundImage[] bgImgA = new BackgroundImage[]{bgImg};
//		this.setBackground(new Background(bgFillA, bgImgA));
//		this.setDrawn(true);
//	}
	public void draw(Image pieceImage, String pieceType) {
        this.pieceImage = pieceImage;
        this.isOccupied = true;
        this.pieceType = pieceType;

        double cellWidth = this.getPrefWidth(); // Get the preferred width of the cell
        double cellHeight = this.getPrefHeight(); // Get the preferred height of the cell

        BackgroundImage bgImg = new BackgroundImage(
                pieceImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(cellWidth, cellHeight, false, false, false, false) // Scale to fit cell
        );

        this.setBackground(new Background(
        	    new BackgroundFill[] { new BackgroundFill(cellColor, CornerRadii.EMPTY, Insets.EMPTY) },
        	    new BackgroundImage[] { bgImg }
        	));

    }
/*
	public void initializeCellColor() {
		this.setDrawn(false);
		this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(this.baseColor, (CornerRadii)null, (Insets)null)}));
	}

	public boolean isDrawn() {
		return this.isDrawn;
	}

	public void setDrawn(boolean isDrawn) {
		this.isDrawn = isDrawn;
	}

	public int getxPosition() {
		return this.xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return this.yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Color getBaseColor() {
		return this.baseColor;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}
}
*/
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
            if (pieceImage == null) return; // Only allow dragging if there is a piece

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
        /*this.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasImage() && db.hasString()) {
                String incomingPieceType = db.getString();
                Image updatedImage;
                String updatedPieceType;
                char incomingPieceTeam = incomingPieceType.charAt(incomingPieceType.length() - 1); // Team of incoming piece
                
                // If the target cell has a piece, inherit its type and change to the dropped piece's color
                //if (this.hasPiece()) {
                if (!this.hasPiece() || (this.hasPiece() && incomingPieceTeam != this.getPieceType().charAt(this.getPieceType().length() - 1))) {
                    String currentPieceType = this.getPieceType();
                    String newType = currentPieceType.substring(0, currentPieceType.length() - 1); // Extract type (e.g., "king")

                    // Change to the color of the incoming piece
                    char newColor = incomingPieceType.charAt(incomingPieceType.length() - 1); // Get the incoming piece's color
                    updatedPieceType = newType + newColor;
                    updatedImage = new Image(ClassLoader.getSystemResource("piece/" + updatedPieceType + ".png").toString());
                } else {
                    // If the target cell is empty, keep the incoming piece's type
                    updatedPieceType = incomingPieceType;
                    updatedImage = db.getImage();
                }

                placePiece(updatedImage, updatedPieceType);
                success = true;
            }

            // Indicate whether the drop was successful
            event.setDropCompleted(success);
            event.consume();
        });*/
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
                        Image updatedImage = new Image(ClassLoader.getSystemResource("piece/" + newType + ".png").toString());
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



        // Drag done on source cell
//        this.setOnDragDone(event -> {
//            if (!event.isDropCompleted()) {
//                // If the drop was unsuccessful, revert the piece back to the source cell
//                if (pieceImage == null && event.getDragboard().hasImage()) {
//                    placePiece(event.getDragboard().getImage(), event.getDragboard().getString());
//                }
//            } else {
//                // If the drop was successful, ensure the source cell remains cleared
//                clearPiece();
//            }
//            event.consume();
//        });
        /*
        this.setOnDragDone(event -> {
            if (event.isDropCompleted()) {
                clearPiece(); // Clear the source cell only after a successful drop
            } else {
                // Revert the piece if the drop was not completed
                placePiece(event.getDragboard().getImage(), event.getDragboard().getString());
            }
            event.consume();
        });*/
        
        /*
        this.setOnDragDone(event -> {
        	if (event.isDropCompleted()) {
        	//if (event.getTransferMode() == TransferMode.MOVE) {
				clearPiece();
			}
            event.consume();
        });
        */
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



}