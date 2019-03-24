package vn.edu.hcmut.linexo.presentation.model;

public class Board {

    public static final byte NOT_USED           = 0;
    public static final byte LINE_NOT_DRAWN     = 1;
    public static final byte LINE_DRAWN         = 3;
    public static final byte CELL_NOT_MARKED    = 4;
    public static final byte CELL_X             = 12;
    public static final byte CELL_O             = 20;
    public static final byte IMPEDIMENT         = 28;


    private byte[][] pattern;

    private int     lastPlayer;
    private int[]   lastMove;

    private int     playerToMove;

    public Board(byte[][] pattern) {
        this(pattern, 0, 0, null);
    }

    public Board(byte[][] pattern, int lastPlayer, int playerToMove, int[] lastMove) {
        this.pattern        = pattern;
        this.lastPlayer     = lastPlayer;
        this.playerToMove   = playerToMove;
        this.lastMove       = lastMove;
    }

    public int getWidth() {
        return pattern[0].length;
    }

    public int getHeight() {
        return pattern.length;
    }

    public byte getValueAt(int x, int y) {
        return pattern[y][x];
    }

    public byte[][] gerPattern() {
        byte[][] copy = new byte[pattern.length][pattern[0].length];
        for (int i = 0; i < copy.length; ++i) {
            for (int j = 0; j < copy[0].length; ++j) {
                copy[i][j] = pattern[i][j];
            }
        }
        return copy;
    }

    public int getLastPlayer() {
        return lastPlayer;
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    public int[] getLastMove() {
        return new int[]{lastMove[0], lastMove[1]};
    }

    public Board updatePattern(byte[][] pattern) {
        return new Board(pattern, lastPlayer, playerToMove, lastMove);
    }

    public Board updateLastPlayer(int lastPlayer) {
        return new Board(pattern, lastPlayer, playerToMove, lastMove);
    }

    public Board updatePlayerToMove(int playerToMove) {
        return new Board(pattern, lastPlayer, playerToMove, lastMove);
    }

    public Board updateLastMove(int[] lastMove) {
        return new Board(pattern, lastPlayer, playerToMove, lastMove);
    }
}
