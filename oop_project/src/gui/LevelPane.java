package gui;

import item.Level;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class LevelPane extends GridPane {

    public LevelPane(Level level){
        this.setPrefWidth(428);
        this.setHgap(8);
        this.setPadding(new Insets(4));

        Text nameText = new Text("hi");

        this.add(nameText,1,0);

    }
}
