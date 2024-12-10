package pieces;

import javafx.scene.image.Image;

public class PieceFactory {
	public static Piece createPiece(String type) {
		String team = type.substring(type.length() - 1); // Extract "W" or "B"
		String pieceType = type.substring(0, type.length() - 1).toLowerCase();

		Image pieceImage = loadImage(pieceType, team);
		if (pieceImage == null) {
			return null;
		}

		switch (pieceType) {
		case "pawn":
			return new Pawn(team, pieceImage);
		case "rook":
			return new Rook(team, pieceImage);
		case "knight":
			return new Knight(team, pieceImage);
		case "bishop":
			return new Bishop(team, pieceImage);
		case "queen":
			return new Queen(team, pieceImage);
		case "king":
			return new King(team, pieceImage);
		default:
			return null;
		}
	}

	private static Image loadImage(String pieceType, String team) {
		String imagePath = "/piece/" + pieceType + team + ".png";
		try {
			return new Image(PieceFactory.class.getResourceAsStream(imagePath));
		} catch (Exception e) {
			System.err.println("Failed to load image: " + imagePath);
			return null;
		}
	}
}
