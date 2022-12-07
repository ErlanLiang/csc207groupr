package model;

public class EasyMode implements Difficulty {
    @Override
    public double changeDifficultySpeed() {
        return 0.5;
    }

    @Override
    public int getBoardWidth() {
        return 10;
    }

    @Override
    public int getBoardHeight() {
        return 21;
    }
}