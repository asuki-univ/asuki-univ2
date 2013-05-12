package player.ai.eval;

import java.io.IOException;

import wthor.WThorFeature;
import wthor.WThorParams;
import board.Board;
import board.Stone;
import board.Turn;

public class LearnedEvaluation implements Evaluation {
    private final Turn turn;
    private final WThorParams params;

    public LearnedEvaluation(Turn turn, String filename) throws IOException {
        this.turn = turn;
        this.params = new WThorParams(filename);
    }

    private LearnedEvaluation(Turn turn, WThorParams params) {
        this.turn = turn;
        this.params = params;
    }

    @Override
    public Evaluation clone() {
        return new LearnedEvaluation(turn, params);
    }

    // TODO(mayah): int -> double
    @Override
    public int score(Board board, Stone stone) {
        WThorFeature feature = new WThorFeature();
        feature.collect(board);
        double score = feature.calculateScore(params);

        if (turn == Turn.BLACK) {
            return -(int)score; // TODO(mayah): We would like to return double.
        } else {
            return (int)score;
        }
    }

    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        // Do nothing.
    }
}
