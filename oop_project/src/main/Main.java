package main;

import gui.ControlPane;
import gui.ChessPane;
import gui.LevelSelectPane;
import gui.TimerPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(500);
		root.setPrefWidth(800);
		
		GameLogic.getInstance();
		
		ChessPane chessPane = new ChessPane(3,3, new ArrayList<>(List.of("king", "pawn", "rook", "queen", "knight", "pawn", "knight", "bishop", "pawn")));
		
		ControlPane controlPane = new ControlPane(chessPane);
		
		GameLogic.getInstance().setControlPane(controlPane);
		
		//Display level selection options
		//Wraps LevelSelectPane.getInstance() in a scrollable pane
		ScrollPane scrollPane = new ScrollPane(LevelSelectPane.getInstance());
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		//Adding Components to HBox root
		root.getChildren().add(scrollPane);
		root.getChildren().add(controlPane);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tic-Tac-Toe");
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				Platform.exit();
				System.exit(0);
			}
		});

	}


	public static void main(String[] args) {
		launch(args);
	}
}
