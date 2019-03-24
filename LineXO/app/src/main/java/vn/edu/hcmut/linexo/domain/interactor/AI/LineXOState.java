package vn.edu.hcmut.linexo.domain.interactor.AI;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LineXOState implements Cloneable {
    public static final byte NOT_USED = 0;
    public static final byte LINE_NOT_DRAWN = 1;
    public static final byte LINE_DRAWN = 3;
    public static final byte CELL_NOT_MARKED = 4;
    public static final byte CELL_X = 12;
    public static final byte CELL_O = 20;
    public static final byte IMPEDIMENT = 28;
    public static final String X = "X";
    public static final String O = "O";

    private byte[][] board;
    private String playerToMove;
    private int x_cells;
    private int o_cells;

    public LineXOState(byte[][] board, String playerToMove) {
        super();
        x_cells = 0;
        o_cells = 0;
        this.board = board;
        this.playerToMove = playerToMove;
        for (int i = 0; i < board.length; ++i) {
            byte[] row = board[i];
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == LineXOState.CELL_X) {
                    ++x_cells;
                } else if (row[j] == LineXOState.CELL_O) {
                    ++o_cells;
                }
            }
        }
    }

    public String getPlayerToMove() {
        return playerToMove;
    }

    public byte[][] getBoard() {
        return board;
    }

    @Override
    protected LineXOState clone() {
        byte[][] board_copy = new byte[board.length][board[0].length];
        for (int i = 0; i < board_copy.length; ++i) {
            System.arraycopy(board[i], 0, board_copy[i], 0, board[i].length);
        }
        LineXOState copy = new LineXOState(board_copy, playerToMove);
        return copy;
    }

    public List<LineXOLocation> getNotDrawnLine() {
        List<LineXOLocation> actions = new LinkedList<LineXOLocation>();
        for (int y = 0; y < board.length; ++y) {
            byte[] row = board[y];
            for (int x = 0; x < row.length; ++x) {
                if (row[x] == LineXOState.LINE_NOT_DRAWN) {
                    actions.add(new LineXOLocation(x, y));
                }
            }
        }
        Collections.shuffle(actions);
        return actions;
    }

    public void mark(LineXOLocation location) {
        int x = location.getX();
        int y = location.getY();
        board[y][x] = LineXOState.LINE_DRAWN;
        byte sign = playerToMove == LineXOState.X ? LineXOState.CELL_X : LineXOState.CELL_O;
        int count = 0;
        if (x % 2 == 0 && y % 2 == 1) {
            if (board[y - 1][x - 1] == LineXOState.LINE_DRAWN && board[y + 1][x - 1] == LineXOState.LINE_DRAWN
                    && board[y][x - 2] == LineXOState.LINE_DRAWN) {
                board[y][x - 1] = sign;
                count++;
            }
            if (board[y - 1][x + 1] == LineXOState.LINE_DRAWN && board[y + 1][x + 1] == LineXOState.LINE_DRAWN
                    && board[y][x + 2] == LineXOState.LINE_DRAWN) {
                board[y][x + 1] = sign;
                count++;
            }
        } else if (x % 2 == 1 && y % 2 == 0) {
            if (board[y - 1][x - 1] == LineXOState.LINE_DRAWN && board[y - 1][x + 1] == LineXOState.LINE_DRAWN
                    && board[y - 2][x] == LineXOState.LINE_DRAWN) {
                board[y - 1][x] = sign;
                count++;
            }
            if (board[y + 1][x - 1] == LineXOState.LINE_DRAWN && board[y + 1][x + 1] == LineXOState.LINE_DRAWN
                    && board[y + 2][x] == LineXOState.LINE_DRAWN) {
                board[y + 1][x] = sign;
                count++;
            }
        }
        if (playerToMove == LineXOState.X) {
            x_cells += count;
        } else {
            o_cells += count;
        }
        if (count == 0) {
            playerToMove = playerToMove == LineXOState.X ? LineXOState.O : LineXOState.X;
        }
    }

    public int getUtility(String player) {
        return player == LineXOState.X ? x_cells - o_cells : o_cells - x_cells;
    }
}
