package gui;

import item.Level;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class LevelSelectPane extends VBox {

    private static LevelSelectPane instance;
    private final ArrayList<Level> levels;

    private LevelSelectPane() {
        levels = new ArrayList<>();

        levels.add(new Level("Hairy Panner and the professor's stove", 2, 1, 2, new ArrayList<>(List.of("kingW", "rookB"))));

        levels.add(new Level("Hairy Panner and the Kitchen of secrets", 3, 5, 8, new ArrayList<>(List.of("kingB", "queenW", "rookB", "knightW", "bishopB", "pawnW", "knightB", "rookW", "kingW", "wall", "pawnW", "knightB", "kingW", "bishopB", "pawnW", "queenW", "rookB", "pawnW", "kingB", "queenW", "wall", "knightW", "pawnB", "rookW", "bishopB", "pawnW", "knightB", "queenW", "kingB", "queenW", "knightB", "pawnW", "knightB", "wall", "kingW", "rookB", "pawnW", "bishopB", "bishopW", "bishopW"))));

        levels.add(new Level("Hairy Panner and the Chef of AsKarnBan", 1, 3, 3, new ArrayList<>(List.of("kingW", "pawnB", "rookW", "queenB", "knightW", "pawnB", "knightW", "bishopB", "pawnW"))));

        levels.add(new Level("Hairy Panner and the Chef of AsKarnBan", 3, 4, 4, new ArrayList<>(List.of("wall", "knightB", "knightW", "pawnB", "knightW", "knightB", "knightW", "knightB", "knightW", "wall", "knightW", "knightB", "knightW", "knightB", "knightW", "wall"))));

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

    public static void levelPage(Level level, LevelSelectPane levelSelectPane) {
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

    public void showLevels() {
        this.getChildren().clear();
        Background white = new Background(new BackgroundFill(Color.WHITE, null, null));
        Background nearWhite = new Background(new BackgroundFill(Color.color(0, 0, 0, 0.05), null, null));

        for (int i = 0; i < levels.size(); i++) {
            LevelPane newLevelPane = new LevelPane(levels.get(i), this); // Pass the current instance here
            newLevelPane.setPrefWidth(500); // Adjust size
            newLevelPane.setPrefHeight(300); // Adjust size

            if (i % 2 == 0) {
                newLevelPane.setBackground(white);
            } else {
                newLevelPane.setBackground(nearWhite);
            }

            this.getChildren().add(newLevelPane);
        }
    }

}