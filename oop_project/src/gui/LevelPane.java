package gui;

import item.Level;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelPane extends VBox {

	public LevelPane(Level level, LevelSelectPane levelSelectPane) {
		// Set padding and spacing for the LevelPane
		this.setPadding(new Insets(16));
		this.setSpacing(8);
		this.setAlignment(Pos.TOP_CENTER); // Center contents horizontally

		// Create level name and stars text
		Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5 - level.getStars()));
		starsText.setFont(Font.font(14)); // Font size for stars
		starsText.setFill(Color.DARKRED);

		Text nameText = new Text(level.getName());
		nameText.setFont(Font.font(14)); // Font size for level name
		nameText.setFill(Color.BLACK);

		// Create ChessPane for the level
		ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight(), level.getPieces(), 250.0); // Panel
																											// size

		// Wrap ChessPane in a StackPane to center it horizontally
		StackPane chessPaneContainer = new StackPane(chessPane);
		chessPaneContainer.setAlignment(Pos.CENTER);

		// Add level name, stars, and ChessPane to the LevelPane
		this.getChildren().addAll(nameText, starsText, chessPaneContainer);

		// Add EventHandler for clicking on the LevelPane to navigate to level details
		EventHandler<MouseEvent> gotoLevelPage = mouseEvent -> LevelSelectPane.levelPage(level, levelSelectPane);
		this.setOnMouseClicked(gotoLevelPage);
	}
}
