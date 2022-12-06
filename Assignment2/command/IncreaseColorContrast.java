package command;

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
        if (receiver.colorContrast < 2) {
            receiver.colorContrast++;
            return "Color contrast increased";
        }
        return "Already maximum color contrast!";
    }
}
