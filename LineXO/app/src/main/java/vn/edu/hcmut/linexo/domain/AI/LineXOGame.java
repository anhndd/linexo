package vn.edu.hcmut.linexo.domain.AI;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Board;

public class LineXOGame {
    public final static int MAX_NODES = 100000;

    public List<LineXOMove> getActions(LineXOBoard state) {
        return state.getNotDrawnLine();
    }

    public LineXOBoard getRootState(byte[][] rootBoard, String playerToMove) {
        return new LineXOBoard(rootBoard, playerToMove);
    }

    public String getPlayer(LineXOBoard state) {
        return state.getPlayerToMove();
    }

    public LineXOBoard getResult(LineXOBoard state, LineXOMove location) {
        LineXOBoard result = state.clone();
        result.mark(location);
        return result;
    }

    public double getUtility(LineXOBoard state, String player) {
        return state.getUtility(player);
    }

    public boolean isTerminal(LineXOBoard state) {
        for (int i = 0; i < state.getBoard().length; ++i) {
            byte[] row = state.getBoard()[i];
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == Board.LINE_NOT_DRAWN) {
                    return false;
                }
            }
        }
        return true;
    }
}
