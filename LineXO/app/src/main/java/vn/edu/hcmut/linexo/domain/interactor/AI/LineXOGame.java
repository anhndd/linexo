package vn.edu.hcmut.linexo.domain.interactor.AI;

import java.util.List;

public class LineXOGame {
    public final static int MAX_NODES = 100000;

    public List<LineXOLocation> getActions(LineXOState state) {
        return state.getNotDrawnLine();
    }

    public LineXOState getRootState(byte[][] rootBoard, String playerToMove) {
        return new LineXOState(rootBoard, playerToMove);
    }

    public String getPlayer(LineXOState state) {
        return state.getPlayerToMove();
    }

    public LineXOState getResult(LineXOState state, LineXOLocation location) {
        LineXOState result = state.clone();
        result.mark(location);
        return result;
    }

    public double getUtility(LineXOState state, String player) {
        return state.getUtility(player);
    }

    public boolean isTerminal(LineXOState state) {
        for (int i = 0; i < state.getBoard().length; ++i) {
            byte[] row = state.getBoard()[i];
            for (int j = 0; j < row.length; ++j) {
                if (row[j] == LineXOState.LINE_NOT_DRAWN) {
                    return false;
                }
            }
        }
        return true;
    }
}
