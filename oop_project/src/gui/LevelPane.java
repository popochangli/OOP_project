package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LevelPane extends GridPane {

    public LevelPane(){
        this.setPrefWidth(428);
        this.setHgap(8);
        this.setPadding(new Insets(4));

        Text nameText = new Text("hi");


    }
}
