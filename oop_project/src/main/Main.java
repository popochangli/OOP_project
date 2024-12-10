package main;

import gui.ControlPane;
import gui.LevelSelectPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import logic.GameLogic;

public class Main extends Application {
    private MediaPlayer backgroundMusic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        backgroundMusic = new MediaPlayer(new Media(ClassLoader.getSystemResource("background.mp3").toString()));
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusic.play();

        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);
        root.setPrefWidth(850); // Adjust window size
        root.setPrefHeight(600); // Adjust window size

        GameLogic.getInstance();
        ControlPane controlPane = new ControlPane();
        GameLogic.getInstance().setControlPane();
        ScrollPane scrollPane = new ScrollPane(LevelSelectPane.getInstance());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.getChildren().add(scrollPane);
        root.getChildren().add(controlPane);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess Puzzle");
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
