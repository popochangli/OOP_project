package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        setPrefWidth(400);  // Adjust width
        setSpacing(20);
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
        gameText = new Text("Tic Tac Toe");
        gameText.setFont(new Font(35));

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

    }

    private void initializeStartButton() {
        this.startButton = new Button("Start");
        startButton.setPrefWidth(100);
        startButton.setOnAction(e -> newStartButtonHandler());
    }

    private void newStartButtonHandler() {
        updateGameText("O Turn");
        GameLogic.setGameStart(true);
        inttializeTimeText();
        GameLogic.getInstance().startCountDownTimer(0);
        getChildren().addAll(tp1);
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