package model;

public class HardMode implements Difficulty {
    @Override
    public double changeDifficultySpeed() {
        return 2.0;
    }

    @Override
    public int getBoardWidth() {
        return 8;
    }

    @Override
    public int getBoardHeight() {
        return 15;
    }
}

