package vn.edu.hcmut.linexo.domain.AI;

import vn.edu.hcmut.linexo.presentation.model.Board;

public class LineXOAlphaBetaSearch {
    private LineXOGame game;

    public LineXOAlphaBetaSearch(LineXOGame game) {
        this.game = game;
    }

    public LineXOMove makeDecision(Board state) {
        LineXOMove result = null;
        double resultValue = Double.NEGATIVE_INFINITY;
        int player = game.getPlayer(state);
        for (LineXOMove action : game.getActions(state)) {
            Board resultState = game.getResult(state, action);
            double value = game.getUtility(resultState, player) > game.getUtility(state, player)
                    ? maxValue(resultState, player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1)
                    : minValue(resultState, player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            if (value > resultValue) {
                result = action;
                resultValue = value;
            }
        }
        return result;
    }

    public double maxValue(Board state, int player, double alpha, double beta, int depth) {
        if (game.isTerminal(state) || depth > LineXOGame.MAX_DEPTH || depth > Math.log(LineXOGame.MAX_NODES) / Math.log(state.getNotDrawnLine().size())) {
            return game.getUtility(state, player);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (LineXOMove action : game.getActions(state)) {
            Board resultState = game.getResult(state, action);
            value = resultState.getUtility(player) > state.getUtility(player)
                    ? Math.max(value, maxValue(resultState, player, alpha, beta, depth + 1))
                    : Math.max(value, minValue(resultState, player, alpha, beta, depth + 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public double minValue(Board state, int player, double alpha, double beta, int depth) {
        if (game.isTerminal(state) || depth > LineXOGame.MAX_DEPTH || depth > Math.log(LineXOGame.MAX_NODES) / Math.log(state.getNotDrawnLine().size())) {
            return game.getUtility(state, player);
        }
        double value = Double.POSITIVE_INFINITY;
        for (LineXOMove action : game.getActions(state)) {
            Board resultState = game.getResult(state, action);
            value = resultState.getUtility(player) < state.getUtility(player)
                    ? Math.min(value, minValue(resultState, player, alpha, beta, depth + 1))
                    : Math.min(value, maxValue(resultState, player, alpha, beta, depth + 1));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }
}
