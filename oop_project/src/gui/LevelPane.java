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

    public LevelPane(Level level, LevelSelectPane levelSelectPane){
        this.setPrefWidth(428);
        this.setPrefHeight(500);
        this.setHgap(8);
        this.setPadding(new Insets(4));
        
        Text starsText = new Text("★".repeat(level.getStars()) + "☆".repeat(5 - level.getStars()));
        starsText.setFont(Font.font(14));
        starsText.fillProperty().set(Color.DARKRED);

        Text nameText = new Text(level.getName());
        nameText.setFont(Font.font(14));

        ChessPane chessPane = new ChessPane(level.getWidth(), level.getHeight(), level.getPieces());

        this.add(nameText, 0, 0);
        this.add(starsText, 0, 1);
        this.add(chessPane, 0, 2);

        EventHandler<MouseEvent> gotoLevelPage = mouseEvent -> LevelSelectPane.levelPage(level, levelSelectPane);
        this.setOnMouseClicked(gotoLevelPage);
    }
}
