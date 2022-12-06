package command;

import javafx.scene.paint.Color;
import views.TetrisView;

/**
 * The command class IncreaseColorContrast, called when the user wants to increase the color contrast
 *
 */
public class IncreaseColorContrast implements Command{
    TetrisView receiver;

    public IncreaseColorContrast(TetrisView tetrisView) {
        this.receiver = tetrisView;
    }
    @Override
    public String execute() {
        //set up the colors for board and block based on different values of colorContrast
        if (receiver.colorContrast == 0) {
            this.receiver.boardColor = Color.LIGHTGREY;
            this.receiver.blockColor = Color.BLACK;
            receiver.colorContrast++;
            return "Color contrast increased";
        } else if (receiver.colorContrast == 1) {
            this.receiver.boardColor = Color.WHITE;
            this.receiver.blockColor = Color.BLACK;
            receiver.colorContrast++;
            return "Color contrast increased";
        } else {
            return "Already maximum color contrast!";
        }
    }
}
