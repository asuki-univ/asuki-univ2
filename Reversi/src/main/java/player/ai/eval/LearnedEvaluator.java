package player.ai.eval;

import java.io.IOException;

import wthor.WThorFeature;
import wthor.WThorParams;
import board.Board;
import board.Stone;
import board.Turn;

public class LearnedEvaluator implements Evaluator {
    private final Turn turn;
    private final WThorParams params;

    public LearnedEvaluator(Turn turn, String filename) {
        this.turn = turn;
        try {
            this.params = new WThorParams(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LearnedEvaluator(Turn turn, WThorParams params) {
        this.turn = turn;
        this.params = params;
    }

    @Override
    public Evaluator clone() {
        return new LearnedEvaluator(turn, params);
    }

    @Override
    public double score(Board board, Turn currentTurn) {
        WThorFeature feature = new WThorFeature();
        feature.collect(board);
        double score = feature.calculateScore(params);

        if (currentTurn == Turn.BLACK) {
            return score;
        } else {
            return -score;
        }
    }

    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        // Do nothing.
    }
}
