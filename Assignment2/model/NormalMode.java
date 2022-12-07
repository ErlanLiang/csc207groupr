package model;

public class NormalMode implements Difficulty {
    @Override
    public double changeDifficultySpeed() {
        return 1.0;
    }

    @Override
    public int getBoardWidth() {
        return 9;
    }

    @Override
    public int getBoardHeight() {
        return 18;
    }
}
