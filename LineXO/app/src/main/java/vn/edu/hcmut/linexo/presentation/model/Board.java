package vn.edu.hcmut.linexo.presentation.model;

public class Board {
    private int width  = 19;
    private int height = 11;

    // maxtrix

    private int lastPlayer;
    private int lastMoveX;
    private int lastMoveY;

    public Board updateWidth(int width) {
        return new Board();
    }

    public int getWidth() {
        return width;
    }

    public Board updateHeight(int height) {
        return new Board();
    }

    public int getHeight() {
        return height;
    }
}
