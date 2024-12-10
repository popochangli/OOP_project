package logic;

import gui.ChessCell;
import gui.ChessPane;
import gui.ControlPane;
import gui.TimerPane;
import javafx.application.Platform;

public class GameLogic {

    private static GameLogic instance = null;
    private static boolean gameStart = false;
    private static Timer[] playerTimer = new Timer[]{new Timer(0, 0, 0)};
    private static TimerPane[] timerPane;

    private GameLogic() {
        playerTimer = new Timer[]{new Timer(0, 0, 0)};
        timerPane = new TimerPane[1];
        this.newGame();
    }

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public static Timer getPlayerTimer(int pl) {
        return playerTimer[pl];
    }

    public static void setTimerPane(int pl, TimerPane timerPane) {
        GameLogic.timerPane[pl] = timerPane;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public static void setGameStart(boolean gameStart) {
        GameLogic.gameStart = gameStart;
    }


    public void startCountDownTimer(int pl) {

        Thread timerThread = new Thread(() -> {
            try {
                runCountDownTimer(pl);
            } catch (InterruptedException ignored) {
            }

        });

        timerThread.start();
    }

    public void runCountDownTimer(int pl) throws InterruptedException {
        Timer plTimer = new Timer(0, 0, 1);
        plTimer.setMove(0);
        GameLogic.getPlayerTimer(0).incrementMove(-GameLogic.getPlayerTimer(0).getMove());
        plTimer.setStop(false);
        if (pl == 0) {
            while (gameStart) {
                Thread.sleep(20);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timerPane[pl].setTimer(plTimer);
                    }
                });

                plTimer.incrementTimer(2);
            }
        }

        plTimer.setStop(true);

    }

    public void newGame() {
        setGameEnd();
        gameStart = false;
        playerTimer = new Timer[]{new Timer(0, 0, 1)};
        timerPane = new TimerPane[1];
        timerPane[0] = new TimerPane(0);
        timerPane[0].setTimer(playerTimer[0]);
        startCountDownTimer(0);
    }

    public static void setGameEnd() {
    }


    public void setControlPane() {
    }

    public void checkGameEnd() {
        boolean endgame = false;
        ControlPane k = (ControlPane) timerPane[0].getParent();
        System.out.println(k.getChessPane());
        System.out.println(k);
        ChessPane chessPane = k.getChessPane();
        int numBlack = 0;
        for (ChessCell x : chessPane.getAllCells()) {
            if (x.hasPiece() && "B".equals(x.getPiece().getTeam())) {
                numBlack++;
            }
        }
        if (numBlack == 0) {
            endgame = true;
        }
        System.out.println(numBlack);
        System.out.println("hiiiiiiiiiiii" + endgame);
        if (endgame) {

            k.updateGameText("Win แล้ว");

            setGameEnd();
            gameStart = false;
        }
    }

    public TimerPane[] getTimerPane() {
        return timerPane;
    }

}

