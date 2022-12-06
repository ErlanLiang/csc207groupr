package command;

import javafx.scene.paint.Color;
import views.TetrisView;

/**
 * The command class DecreaseColorContrast, called when the user wants to decrease the color contrast
 *
 */
public class DecreaseColorContrast implements Command {
    TetrisView receiver;

    public DecreaseColorContrast(TetrisView tetrisView) {
        this.receiver = tetrisView;
    }

    @Override
    public String execute() {
        //set up the colors for board and block based on different values of colorContrast
        if (receiver.colorContrast == 2) {
            this.receiver.boardColor = Color.LIGHTGREY;
            this.receiver.blockColor = Color.BLACK;
            receiver.colorContrast--;
            return "Color contrast decreased";
        } else if (receiver.colorContrast == 1) {
            this.receiver.boardColor = Color.GREEN;
            this.receiver.blockColor = Color.RED;
            receiver.colorContrast--;
            return "Color contrast decreased";
        } else {
            return "Already maximum color contrast!";
        }
    }
}