package vn.edu.hcmut.linexo.presentation.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import vn.edu.hcmut.linexo.domain.AI.LineXOMove;

public class Board {

    public static final byte NOT_USED           = 0;
    public static final byte LINE_NOT_DRAWN     = 1;
    public static final byte LINE_DRAWN         = 3;
    public static final byte CELL_NOT_MARKED    = 4;
    public static final byte CELL_X             = 12;
    public static final byte CELL_O             = 20;
    public static final byte IMPEDIMENT         = 28;
    public static final byte X                  = 1;
    public static final byte O                  = 2;

    private byte[][] pattern;

    private int     lastPlayer;
    private int[]   lastMove;

    private int     playerToMove;
    private int x_cells;
    private int o_cells;

    public Board(byte[][] pattern) {
        this(pattern, 0, 0, null);
    }

    public Board(byte[][] pattern, int lastPlayer, int playerToMove, int[] lastMove) {
        this.pattern        = pattern;
        this.lastPlayer     = lastPlayer;
        this.playerToMove   = playerToMove;
        this.lastMove       = lastMove;
        x_cells = 0;
        o_cells = 0;
        for (int i = 0; i < pattern.length; ++i) {
            byte[] row = pattern[i];
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == Board.CELL_X) {
                    ++x_cells;
                } else if (row[j] == Board.CELL_O) {
                    ++o_cells;
                }
            }
        }
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

    public byte[][] getPattern() {
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

    public Board duplicate() {
        byte[][] board_copy = new byte[getHeight()][getWidth()];
        for (int i = 0; i < board_copy.length; ++i) {
            System.arraycopy(pattern[i], 0, board_copy[i], 0, pattern[i].length);
        }
        Board copy = new Board(board_copy, lastPlayer, playerToMove, lastMove);
        return copy;
    }

    public List<LineXOMove> getNotDrawnLine() {
        List<LineXOMove> actions = new LinkedList<LineXOMove>();
        for (int y = 0; y < getHeight(); ++y) {
            for (int x = 0; x < getWidth(); ++x) {
                if (getValueAt(x, y) == Board.LINE_NOT_DRAWN) {
                    actions.add(new LineXOMove(x, y));
                }
            }
        }
        Collections.shuffle(actions);
        return actions;
    }

    public void mark(LineXOMove location) {
        int x = location.getX();
        int y = location.getY();
        pattern[y][x] = Board.LINE_DRAWN;
        byte sign = playerToMove == Board.X ? Board.CELL_X : Board.CELL_O;
        int count = 0;
        if (x % 2 == 0 && y % 2 == 1) {
            if (pattern[y - 1][x - 1] == Board.LINE_DRAWN && pattern[y + 1][x - 1] == Board.LINE_DRAWN
                    && pattern[y][x - 2] == Board.LINE_DRAWN) {
                pattern[y][x - 1] = sign;
                count++;
            }
            if (pattern[y - 1][x + 1] == Board.LINE_DRAWN && pattern[y + 1][x + 1] == Board.LINE_DRAWN
                    && pattern[y][x + 2] == Board.LINE_DRAWN) {
                pattern[y][x + 1] = sign;
                count++;
            }
        } else if (x % 2 == 1 && y % 2 == 0) {
            if (pattern[y - 1][x - 1] == Board.LINE_DRAWN && pattern[y - 1][x + 1] == Board.LINE_DRAWN
                    && pattern[y - 2][x] == Board.LINE_DRAWN) {
                pattern[y - 1][x] = sign;
                count++;
            }
            if (pattern[y + 1][x - 1] == Board.LINE_DRAWN && pattern[y + 1][x + 1] == Board.LINE_DRAWN
                    && pattern[y + 2][x] == Board.LINE_DRAWN) {
                pattern[y + 1][x] = sign;
                count++;
            }
        }
        if (playerToMove == Board.X) {
            x_cells += count;
        } else {
            o_cells += count;
        }
        lastPlayer = playerToMove;
        lastMove = new int[]{location.getX(), location.getY()};
        if (count == 0) {
            playerToMove = playerToMove == Board.X ? Board.O : Board.X;
        }
    }

    public int getUtility(int player) {
        return player == Board.X ? x_cells - o_cells : o_cells - x_cells;
    }

    public boolean isValid(LineXOMove move) {
        if (pattern[move.getX()][move.getY()] == Board.LINE_NOT_DRAWN){
            return true;
        }
        return false;
    }
}
