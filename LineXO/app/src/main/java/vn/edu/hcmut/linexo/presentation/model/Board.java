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
    private int x_cells;
    private int o_cells;
    private int max_cells;

    public Board(byte[][] pattern, int x_cells, int o_cells, int max_cells) {
        this.pattern = pattern;
        this.x_cells = x_cells;
        this.o_cells = o_cells;
        this.max_cells = max_cells;
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

//    public int getLastPlayer() {
//        return 0;
//    }

//    public int getPlayerToMove() {
//        return 0;
//    }

//    public int[] getLastMove() {
//        return null;
//    }

//    public Board updatePattern(byte[][] pattern) {
//        return new Board(pattern,);
//    }

//    public Board updateLastPlayer(int lastPlayer) {
//        return null;
//    }
//
//    public Board updatePlayerToMove(int playerToMove) {
//        return null;
//    }
//
//    public Board updateLastMove(int[] lastMove) {
//        return null;
//    }

//    public Board duplicate() {
//        byte[][] board_copy = new byte[getHeight()][getWidth()];
//        for (int i = 0; i < board_copy.length; ++i) {
//            System.arraycopy(pattern[i], 0, board_copy[i], 0, pattern[i].length);
//        }
//       // Board copy = new Board(board_copy, lastPlayer, playerToMove, lastMove);
//        return null;
//    }

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

//    public void mark(LineXOMove location) {
////        int x = location.getX();
////        int y = location.getY();
////        pattern[y][x] = Board.LINE_DRAWN;
////        byte sign = playerToMove == Board.X ? Board.CELL_X : Board.CELL_O;
////        int count = 0;
////        if (x % 2 == 0 && y % 2 == 1) {
////            if (pattern[y - 1][x - 1] == Board.LINE_DRAWN && pattern[y + 1][x - 1] == Board.LINE_DRAWN
////                    && pattern[y][x - 2] == Board.LINE_DRAWN) {
////                pattern[y][x - 1] = sign;
////                count++;
////            }
////            if (pattern[y - 1][x + 1] == Board.LINE_DRAWN && pattern[y + 1][x + 1] == Board.LINE_DRAWN
////                    && pattern[y][x + 2] == Board.LINE_DRAWN) {
////                pattern[y][x + 1] = sign;
////                count++;
////            }
////        } else if (x % 2 == 1 && y % 2 == 0) {
////            if (pattern[y - 1][x - 1] == Board.LINE_DRAWN && pattern[y - 1][x + 1] == Board.LINE_DRAWN
////                    && pattern[y - 2][x] == Board.LINE_DRAWN) {
////                pattern[y - 1][x] = sign;
////                count++;
////            }
////            if (pattern[y + 1][x - 1] == Board.LINE_DRAWN && pattern[y + 1][x + 1] == Board.LINE_DRAWN
////                    && pattern[y + 2][x] == Board.LINE_DRAWN) {
////                pattern[y + 1][x] = sign;
////                count++;
////            }
////        }
////        if (playerToMove == Board.X) {
////            x_cells += count;
////        } else {
////            o_cells += count;
////        }
////        lastPlayer = playerToMove;
////        lastMove = new int[]{location.getX(), location.getY()};
////        if (count == 0) {
////            playerToMove = playerToMove == Board.X ? Board.O : Board.X;
////        }
//    }

    public int getUtility(int player) {
        return player == Board.X ? x_cells - o_cells : o_cells - x_cells;
    }

    public boolean isValid(LineXOMove move) {
        if (pattern[move.getX()][move.getY()] == Board.LINE_NOT_DRAWN){
            return true;
        }
        return false;
    }

    public int getX_cells() {
        return x_cells;
    }

    public void setX_cells(int x_cells) {
        this.x_cells = x_cells;
    }

    public int getO_cells() {
        return o_cells;
    }

    public void setO_cells(int o_cells) {
        this.o_cells = o_cells;
    }

    public int getMax_cells() {
        return max_cells;
    }

    public void setMax_cells(int max_cells) {
        this.max_cells = max_cells;
    }

}
