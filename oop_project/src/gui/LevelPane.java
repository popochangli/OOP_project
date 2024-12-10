//package gui;
//
//import item.Level;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.GridPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//
//
//public class LevelPane extends GridPane {
//
//    public LevelPane(Level level, LevelSelectPane levelSelectPane){
//        this.setPrefWidth(428);
//        this.setPrefHeight(500);
//        this.setHgap(8);
//        this.setPadding(new Insets(4));
//
//        Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5 - level.getStars()));
//        starsText.setFont(Font.font(14));
//        starsText.fillProperty().set(Color.DARKRED);
//
//        Text nameText = new Text(level.getName());
//        nameText.setFont(Font.font(14));
//
//        ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight(), level.getPieces());
//
//        this.add(nameText, 0, 0);
//        this.add(starsText, 0, 1);
//        this.add(chessPane, 0, 2);
//
//        EventHandler<MouseEvent> gotoLevelPage = mouseEvent -> LevelSelectPane.levelPage(level, levelSelectPane);
//        this.setOnMouseClicked(gotoLevelPage);
//    }
//}
package gui;

import item.Level;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class LevelPane extends GridPane {

    public LevelPane(Level level, LevelSelectPane levelSelectPane) {
        // Set initial preferred size for the LevelPane
        this.setPrefWidth(428);  // You can adjust based on your design requirements
        this.setPrefHeight(500); // You can adjust based on your design requirements
        this.setHgap(8);
        this.setVgap(8);
        this.setPadding(new Insets(8));

        // Create level name and stars text
        Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5 - level.getStars()));
        starsText.setFont(Font.font(14));  // Font size for stars
        starsText.setFill(Color.DARKRED);

        Text nameText = new Text(level.getName());
        nameText.setFont(Font.font(14));  // Font size for level name
        nameText.setFill(Color.BLACK);

        // Create ChessPane for the level
        ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight(), level.getPieces(), 250.0); // You can adjust the panel size here

        // Add the level name, stars, and chess board to the LevelPane
        this.add(nameText, 0, 0);
        this.add(starsText, 0, 1);
        this.add(chessPane, 0, 2);

        // Add EventHandler for clicking on the LevelPane to navigate to level details
        EventHandler<MouseEvent> gotoLevelPage = mouseEvent -> LevelSelectPane.levelPage(level, levelSelectPane);
        this.setOnMouseClicked(gotoLevelPage);
    }
}