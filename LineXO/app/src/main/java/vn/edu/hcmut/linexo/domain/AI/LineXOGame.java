package vn.edu.hcmut.linexo.domain.AI;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Board;


public class LineXOGame {
    public final static int MAX_NODES = 50000;
    public final static int MAX_DEPTH = 3;
    public List<LineXOMove> getActions(LineXOBoard state) {
        return state.getNotDrawnLine();
    }

    public LineXOBoard getRootState(byte[][] rootBoard, int playerToMove) {
        return null;
    }

    public int getPlayer(LineXOBoard state) {
        return state.getPlayerToMove();
    }

    public LineXOBoard getResult(LineXOBoard state, LineXOMove location) {
        LineXOBoard result = state.clone();
        result.mark(location);
        return result;
    }

    public double getUtility(LineXOBoard state, int player) {
        return state.getUtility(player);
    }

    public boolean isTerminal(LineXOBoard state) {
        for (int i = 0; i < state.getHeight(); ++i) {
            for (int j = 0; j < state.getWidth(); ++j) {
                if (state.getValueAt(j, i) == Board.LINE_NOT_DRAWN) {
                    return false;
                }
            }
        }
        return true;
    }
}