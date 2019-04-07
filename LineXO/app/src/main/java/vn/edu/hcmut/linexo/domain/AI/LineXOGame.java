package vn.edu.hcmut.linexo.domain.AI;

import java.util.List;

import vn.edu.hcmut.linexo.presentation.model.Board;

public class LineXOGame {
    public final static int MAX_NODES = 50000;
    public final static int MAX_DEPTH = 5;
    public List<LineXOMove> getActions(Board state) {
        return state.getNotDrawnLine();
    }

    public Board getRootState(byte[][] rootBoard, int playerToMove) {
        return new Board(rootBoard, 0, playerToMove, null);
    }

    public int getPlayer(Board state) {
        return state.getPlayerToMove();
    }

    public Board getResult(Board state, LineXOMove location) {
        Board result = state.duplicate();
        result.mark(location);
        return result;
    }

    public double getUtility(Board state, int player) {
        return state.getUtility(player);
    }

    public boolean isTerminal(Board state) {
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
