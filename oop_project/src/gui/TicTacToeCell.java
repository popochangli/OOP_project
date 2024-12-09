package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.GameLogic;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class TicTacToeCell extends Pane {
	private boolean isDrawn;
	private Color baseColor;
	private int xPosition;
	private int yPosition;
	private String oURL = "o.png";
	private String xURL = "x.png";
	private Button button = new Button();

	public TicTacToeCell(int x, int y) {
		this.setxPosition(x);
		this.setyPosition(y);
		this.setBaseColor(Color.MOCCASIN);
		this.initializeCellColor();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				TicTacToeCell.this.onClickHandler();
			}
		});
	}

	private void onClickHandler() {
		if (!GameLogic.getInstance().isGameEnd() && GameLogic.getInstance().isGameStart() && !this.isDrawn) {
			if (GameLogic.getInstance().isOturn()) {
				this.draw(new Image(ClassLoader.getSystemResource(this.oURL).toString()), Color.AQUA, 150);
				GameLogic.getInstance().beginTurns(1);
			} else {
				this.draw(new Image(ClassLoader.getSystemResource(this.xURL).toString()), Color.YELLOW, 150);
				GameLogic.getInstance().beginTurns(0);
			}

			GameLogic.getInstance().drawNumber(this.xPosition, this.yPosition);
		}

	}

	public void draw(Image image, Color backgroundColor, int size) {
		this.getChildren().add(this.button);
		this.getChildren().remove(this.button);
		BackgroundFill bgFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
		BackgroundFill[] bgFillA = new BackgroundFill[]{bgFill};
		BackgroundSize bgSize = new BackgroundSize((double)size, (double)size, false, false, true, false);
		this.setBackground(new Background(bgFillA));
		BackgroundImage bgImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
		BackgroundImage[] bgImgA = new BackgroundImage[]{bgImg};
		this.setBackground(new Background(bgFillA, bgImgA));
		this.setDrawn(true);
	}

	public void initializeCellColor() {
		this.setDrawn(false);
		this.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(this.baseColor, (CornerRadii)null, (Insets)null)}));
	}

	public boolean isDrawn() {
		return this.isDrawn;
	}

	public void setDrawn(boolean isDrawn) {
		this.isDrawn = isDrawn;
	}

	public int getxPosition() {
		return this.xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return this.yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public Color getBaseColor() {
		return this.baseColor;
	}

	public void setBaseColor(Color baseColor) {
		this.baseColor = baseColor;
	}
}
