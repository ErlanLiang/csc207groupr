package command;

import javafx.scene.paint.Color;
import views.TetrisView;


/**
 * The command class ChangeBoardColor, called when the user wants to change the color of the Tetris board
 *
 */
public class ChangeBoardColor implements Command{
    TetrisView receiver;
    Color color;
    String colorString;

    public ChangeBoardColor(TetrisView tetrisView, String color) {
        this.colorString = color;
        this.color = Color.valueOf(color);
        this.receiver = tetrisView;
    }
    @Override
    public String execute() {
        this.receiver.boardColor = this.color;
        return "Board color set to " + this.colorString;
    }
}
