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

        // TODO: FILL CODE HERE
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

    public ArrayList<Level> getLevels() {
        return levels;
    }

//    public Button newLevelButton(){
//        Button button = new Button("Add New Level");
//        button.setPrefWidth(400);
//        button.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, null,null)));
//        button.setTextFill(Color.WHITE);
//        EventHandler<MouseEvent> gotoAddNewLevelPage = mouseEvent -> Goto.addNewLevelPage();
//        button.setOnMouseClicked(gotoAddNewLevelPage);
//
//        return button;
//    }

    public void showLevels(){

        this.getChildren().clear();
        Background white = new Background(new BackgroundFill(Color.WHITE,null,null));
        Background nearWhite = new Background(new BackgroundFill(Color.color(0,0,0,0.05),null,null));

        for(int i = 0;i < levels.size();i++){
            LevelPane newLevelPane = new LevelPane(levels.get(i));
            if(i%2 == 0){
                newLevelPane.setBackground(white);
            }else {
                newLevelPane.setBackground(nearWhite);
            }

            this.getChildren().add(newLevelPane);
        }

//        this.getChildren().add(newLevelButton());
    }

}
