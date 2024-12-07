package main;

import gui.ControlPane;
import gui.ChessPane;
import gui.TimerPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.GameLogic;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		HBox root = new HBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		root.setPrefHeight(500);

		root.setPrefWidth(800);
		GameLogic.getInstance();
		ChessPane chessPane = new ChessPane(5,3);
		ControlPane controlPane = new ControlPane(chessPane);
		
		GameLogic.getInstance().setControlPane(controlPane);


		root.getChildren().add(chessPane);
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
