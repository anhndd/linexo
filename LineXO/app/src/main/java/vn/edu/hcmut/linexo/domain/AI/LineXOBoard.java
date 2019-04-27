package vn.edu.hcmut.linexo.domain.AI;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Board;

public class LineXOBoard {
    private byte[][] board;
    private int playerToMove;
    private int x_cells;
    private int o_cells;

    public LineXOBoard(byte[][] board, int playerToMove) {
        x_cells = 0;
        o_cells = 0;
        this.board = board;
        this.playerToMove = playerToMove;
        for (int i = 0; i < board.length; ++i) {
            byte[] row = board[i];
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == Board.CELL_X) {
                    ++x_cells;
                } else if (row[j] == Board.CELL_O) {
                    ++o_cells;
                }
            }
        }
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

    public int getPlayerToMove() {
        return playerToMove;
    }

    public byte[][] getBoard() {
        return board;
    }

    public int getHeight() {
        return board.length;
    }

    public int getWidth() {
        return board[0].length;
    }

    public byte getValueAt(int x, int y) {
        return board[y][x];
    }

    @Override
    protected LineXOBoard clone() {
        byte[][] board_copy = new byte[board.length][board[0].length];
        for (int i = 0; i < board_copy.length; ++i) {
            System.arraycopy(board[i], 0, board_copy[i], 0, board[i].length);
        }
        LineXOBoard copy = new LineXOBoard(board_copy, playerToMove);
        return copy;
    }

    public List<LineXOMove> getNotDrawnLine() {
        List<LineXOMove> actions = new LinkedList<LineXOMove>();
        for (int y = 0; y < board.length; ++y) {
            byte[] row = board[y];
            for (int x = 0; x < row.length; ++x) {
                if (row[x] == Board.LINE_NOT_DRAWN) {
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
        board[y][x] = Board.LINE_DRAWN;
        byte sign = playerToMove == Board.X ? Board.CELL_X : Board.CELL_O;
        int count = 0;
        if (x % 2 == 0 && y % 2 == 1) {
            if (board[y - 1][x - 1] == Board.LINE_DRAWN && board[y + 1][x - 1] == Board.LINE_DRAWN
                    && board[y][x - 2] == Board.LINE_DRAWN) {
                board[y][x - 1] = sign;
                count++;
            }
            if (board[y - 1][x + 1] == Board.LINE_DRAWN && board[y + 1][x + 1] == Board.LINE_DRAWN
                    && board[y][x + 2] == Board.LINE_DRAWN) {
                board[y][x + 1] = sign;
                count++;
            }
        } else if (x % 2 == 1 && y % 2 == 0) {
            if (board[y - 1][x - 1] == Board.LINE_DRAWN && board[y - 1][x + 1] == Board.LINE_DRAWN
                    && board[y - 2][x] == Board.LINE_DRAWN) {
                board[y - 1][x] = sign;
                count++;
            }
            if (board[y + 1][x - 1] == Board.LINE_DRAWN && board[y + 1][x + 1] == Board.LINE_DRAWN
                    && board[y + 2][x] == Board.LINE_DRAWN) {
                board[y + 1][x] = sign;
                count++;
            }
        }
        if (playerToMove == Board.X) {
            x_cells += count;
        } else {
            o_cells += count;
        }
        if (count == 0) {
            playerToMove = playerToMove == Board.X ? Board.O : Board.X;
        }
    }

    public int getUtility(int player) {
        return player == Board.X ? x_cells - o_cells : o_cells - x_cells;
    }
}
