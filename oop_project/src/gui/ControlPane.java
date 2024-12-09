package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;
import logic.Timer;

public class ControlPane extends VBox{
	
	private Text gameText;
	private Button newGameButton;
	private Button startButton;

	public ChessPane getChessPane() {
		return chessPane;
	}

	public void setChessPane(ChessPane chessPane) {
		this.chessPane = chessPane;
	}

	private ChessPane chessPane;
	private TimerPane tp1;
	private TimerPane tp2;
	
	public ControlPane(ChessPane chessPane) {
		super();
		this.chessPane = chessPane;
		setAlignment(Pos.CENTER);
		setPrefWidth(300);
		setSpacing(20);
		initializeGameText();
		initializeSelectLevelButton();
		inttializeTimeText();
		initializeStartButton();
		getChildren().addAll(gameText,newGameButton,startButton);

	}

	public ControlPane(){
		super();
		setAlignment(Pos.CENTER);
		setPrefWidth(300);
		setSpacing(20);
		initializeGameText();
		initializeSelectLevelButton();
		inttializeTimeText();
		initializeStartButton();
		getChildren().addAll(gameText,newGameButton,startButton);
		startButton.setDisable(true);
	}

	
	private void initializeGameText() {
		gameText=new Text("Tic Tac Toe");
		gameText.setFont(new Font(35));

	}

	private void inttializeTimeText(){
		tp1 = new TimerPane(0);
		tp2 = new TimerPane(1);
		GameLogic.setTimerPane(0, tp1);
		GameLogic.setTimerPane(1, tp2);
	}
	
	public void updateGameText(String text) {
		gameText.setText(text);
		
	}
	
	private void initializeSelectLevelButton() {
		this.newGameButton=new Button("Select Level");
		newGameButton.setPrefWidth(100);
		newGameButton.setOnAction(e->selectLevelButtonHandler());
		
	}
	private void initializeStartButton() {
		this.startButton=new Button("Start");
		startButton.setPrefWidth(100);
		startButton.setOnAction(e->newStartButtonHandler());
	}

	private void newStartButtonHandler() {
		updateGameText("O Turn");
		GameLogic.setGameStart(true);
		GameLogic.getInstance().startCountDownTimer(0);
		GameLogic.getInstance().startCountDownTimer(1);
		getChildren().addAll(tp1,tp2);
		startButton.setDisable(true);
	}
	private void selectLevelButtonHandler() {
		GameLogic.setGameStart(false);

		ControlPane controlPane = new ControlPane();
		GameLogic.getInstance().setControlPane(controlPane);

		LevelSelectPane levelSelectPane = LevelSelectPane.getInstance();

		levelSelectPane.showLevels();

		ScrollPane scrollPane = new ScrollPane(levelSelectPane);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		HBox parent = (HBox) getParent();

		parent.getChildren().clear();
		parent.getChildren().add(scrollPane);
		parent.getChildren().add(controlPane);

		startButton.setDisable(false);
	}
}
