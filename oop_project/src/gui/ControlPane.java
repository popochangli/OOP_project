package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameLogic;

public class ControlPane extends VBox {

	public Button startButton;
	private Text gameText;
	private Button newGameButton;
	private ChessPane chessPane;
	private TimerPane tp1;

	public ControlPane(ChessPane chessPane) {
		super();
		this.chessPane = chessPane;
		setAlignment(Pos.CENTER);
		setPrefWidth(400); // Adjust width
		setSpacing(20);
		setStyle("-fx-background-color: beige;");
		initializeGameText();
		initializeSelectLevelButton();
		inttializeTimeText();
		initializeStartButton();
		getChildren().addAll(gameText, newGameButton, startButton);
	}

	public ControlPane() {
		super();
		setAlignment(Pos.CENTER);
		setPrefWidth(300);
		setSpacing(20);
		setStyle("-fx-background-color: beige;");
		initializeGameText();
		initializeSelectLevelButton();
		inttializeTimeText();
		initializeStartButton();
		getChildren().addAll(gameText, newGameButton, startButton);
		startButton.setDisable(true);
	}

	public ChessPane getChessPane() {
		return chessPane;
	}

	public void setChessPane(ChessPane chessPane) {
		this.chessPane = chessPane;
	}

	private void initializeGameText() {

		gameText = new Text("Chess Puzzle");
		gameText.setFont(Font.font("Impact", FontWeight.BOLD, 50)); // Bold font with a larger size

		// Apply a solid brown color
		gameText.setFill(Color.BROWN); // Use JavaFX's built-in brown color
	}

	private void inttializeTimeText() {
		tp1 = new TimerPane(0);
		tp1.setMove(0);
		GameLogic.setTimerPane(0, tp1);
	}

	public void updateGameText(String text) {
		gameText.setText(text);

	}

	private void initializeSelectLevelButton() {
		this.newGameButton = new Button("Select Level");
		newGameButton.setPrefWidth(100);
		newGameButton.setOnAction(e -> selectLevelButtonHandler());
		newGameButton.setStyle("-fx-background-color: linear-gradient(to right, #506432, #7AA23E);" +

				"-fx-text-fill: white;" + "-fx-font-size: 14px;" + "-fx-font-weight: bold;"
				+ "-fx-background-radius: 10;" + "-fx-border-color: #7AA23E;" + "-fx-border-radius: 10;"
				+ "-fx-border-width: 2;");
	}

	private void initializeStartButton() {
		this.startButton = new Button("Start");
		startButton.setPrefWidth(100);
		startButton.setOnAction(e -> newStartButtonHandler());
		startButton.setStyle("-fx-background-color: linear-gradient(to right, #C14C32, #F18E3F);" +

				"-fx-text-fill: white;" + "-fx-font-size: 14px;" + "-fx-font-weight: bold;"
				+ "-fx-background-radius: 10;" + "-fx-border-color: #F18E3F;" + "-fx-border-radius: 10;"
				+ "-fx-border-width: 2;");
	}

	private void newStartButtonHandler() {
		updateGameText("Moveeeeeeee");
		GameLogic.setGameStart(true);
		inttializeTimeText();
		GameLogic.getInstance().startCountDownTimer(0);
		getChildren().addAll(tp1);
		startButton.setDisable(true);
	}

	private void selectLevelButtonHandler() {
		GameLogic.setGameStart(false);

		ControlPane controlPane = new ControlPane();
		GameLogic.getInstance().setControlPane();

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