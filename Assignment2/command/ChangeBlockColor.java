package command;

import javafx.scene.paint.Color;
import views.TetrisView;


/**
 * The command class ChangeBoardColor, called when the user wants to change the color of the Tetris block
 *
 */
public class ChangeBlockColor implements Command{
    TetrisView receiver;
    Color color;

    String colorString;

    public ChangeBlockColor(TetrisView tetrisView, String color) {
        this.colorString = color;
        this.color = Color.valueOf(color);
        this.receiver = tetrisView;
    }
    @Override
    public String execute() {
        this.receiver.blockColor = this.color;
        return "Block color set to " + this.colorString;
    }
}
