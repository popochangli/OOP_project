package gui;

import item.Level;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelPane extends GridPane {

    public LevelPane(Level level){
        this.setPrefWidth(428);
        this.setPrefHeight(500);
        this.setHgap(8);
        this.setPadding(new Insets(4));

        Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5- level.getStars()));
        starsText.fillProperty().set(Color.DARKRED);

        Text nameText = new Text(level.getName());

        ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight());

        this.add(nameText,1,0);
        this.add(starsText, 1, 3);
        this.add(chessPane, 1, 4);

    }
}
