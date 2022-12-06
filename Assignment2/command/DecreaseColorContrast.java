package command;

import views.TetrisView;

/**
 * The command class DecreaseColorContrast, called when the user wants to decrease the color contrast
 *
 */
public class DecreaseColorContrast implements Command{
    TetrisView receiver;

    public DecreaseColorContrast(TetrisView tetrisView) {
        this.receiver = tetrisView;
    }
    @Override
    public String execute() {
        if (receiver.colorContrast > 0) {
            receiver.colorContrast--;
            return "Color contrast decreased";
        }
        return "Already minimum color contrast!";
    }
}
