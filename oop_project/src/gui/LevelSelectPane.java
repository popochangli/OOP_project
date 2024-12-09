package gui;

import item.Level;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectPane extends VBox{

    private static LevelSelectPane instance;
    private ArrayList<Level> levels;

    private LevelSelectPane() {
        levels = new ArrayList<>();
        levels.add(new Level("Hairy Panner and the professor's stove", "J.K. Kidding", 2, 1, 2, new ArrayList<>(List.of("king", "rook"))));
        levels.add(new Level("Hairy Panner and the Kitchen of secrets", "J.K. Kidding", 3, 5, 8, new ArrayList<>(List.of("king", "queen", "rook", "knight", "bishop", "pawn", "knight", "rook", "king", "queen", "pawn", "knight", "king", "bishop", "pawn", "queen", "rook", "pawn", "king", "queen", "pawn", "knight", "pawn", "rook", "bishop", "pawn", "knight", "queen", "king", "queen", "knight", "pawn", "knight", "pawn", "king", "rook", "pawn", "bishop", "bishop", "bishop"))));
        levels.add(new Level("Hairy Panner and the Chef of AsKarnBan", "J.K. Kidding", 1, 3, 3, new ArrayList<>(List.of("king", "pawn", "rook", "queen", "knight", "pawn", "knight", "bishop", "pawn"))));

        this.setFillWidth(true);
        this.setAlignment(Pos.CENTER);
        this.showLevels();
    }

    public static LevelSelectPane getInstance() {
        if (instance == null) {
            instance = new LevelSelectPane();
        }
        return instance;
    }

    public void showLevels() {
        this.getChildren().clear();
        Background white = new Background(new BackgroundFill(Color.WHITE, null, null));
        Background nearWhite = new Background(new BackgroundFill(Color.color(0, 0, 0, 0.05), null, null));

        for (int i = 0; i < levels.size(); i++) {
            LevelPane newLevelPane = new LevelPane(levels.get(i), this); // Pass the current instance here
            newLevelPane.setPrefWidth(500); // Adjust size
            newLevelPane.setPrefHeight(600); // Adjust size

            if (i % 2 == 0) {
                newLevelPane.setBackground(white);
            } else {
                newLevelPane.setBackground(nearWhite);
            }

            this.getChildren().add(newLevelPane);
        }
    }

    public static void levelPage(Level level, LevelSelectPane levelSelectPane){
        levelSelectPane.getChildren().clear();

        // Set the preferred width and height for the level display page
        levelSelectPane.setPrefWidth(800);  // Adjusted width
        levelSelectPane.setPrefHeight(600); // Adjusted height
        levelSelectPane.setPadding(new Insets(20));

        // Create text for stars and level name
        Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5 - level.getStars()));
        starsText.setFont(Font.font(18));
        starsText.setFill(Color.DARKRED);

        Text nameText = new Text(level.getName());
        nameText.setFont(Font.font(24)); // Larger font for the level name
        nameText.setFill(Color.BLACK);

        // Create a ChessPane for the level
        ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight(), level.getPieces(), 400); // Pass a panel size (adjust as needed)

        // Add the name and stars text, and chess board to the levelSelectPane
        levelSelectPane.getChildren().add(nameText);
        levelSelectPane.getChildren().add(starsText);
        levelSelectPane.getChildren().add(chessPane);

        // Control Pane (e.g., back to level selection or start game)
        ControlPane controlPane = new ControlPane(chessPane);
        levelSelectPane.getChildren().add(controlPane); // Add control buttons below the chess pane

        // Adjust layout of root container
        HBox root = (HBox) levelSelectPane.getScene().getRoot();
        root.getChildren().clear();

        root.getChildren().add(levelSelectPane);
        root.getChildren().add(controlPane);
    }

}
