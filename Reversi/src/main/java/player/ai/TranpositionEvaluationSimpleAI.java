package player.ai;

import java.util.HashMap;
import java.util.Map;

import player.Player;
import player.ai.eval.BoardScoreEvaluation;
import player.ai.eval.Evaluation;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

class TranpositionTableKey {
    private final int restDepth;
    private final Board board;

    public TranpositionTableKey(int restDepth, Board board) {
        this.restDepth = restDepth;
        this.board = board;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TranpositionTableKey))
            return false;

        TranpositionTableKey lhs = this;
        TranpositionTableKey rhs = (TranpositionTableKey) obj;

        if (lhs.restDepth != rhs.restDepth)
            return false;
        return lhs.board.equals(rhs.board);
    }

    @Override
    public int hashCode() {
        return 37 * restDepth + board.hashCode();
    }
}

class TranpositionTableValue {
    public final double lower;
    public final double upper;
    public final Position position;

    public TranpositionTableValue(double lower, double upper, Position position) {
        this.lower = lower;
        this.upper = upper;
        this.position = position;
    }
}

public class TranpositionEvaluationSimpleAI extends Player {
    protected final int maxDepth;

    public TranpositionEvaluationSimpleAI(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return eval(board, maxDepth, 5, turn.stone(),
                new HashMap<TranpositionTableKey, TranpositionTableValue>(),
                new BoardScoreEvaluation(turn), Integer.MIN_VALUE, Integer.MAX_VALUE).getPosition();
    }

    protected EvalResult eval(Board board, int restDepth, int restTranpositionDepth, Stone stone,
            Map<TranpositionTableKey, TranpositionTableValue> tranpositionTable, Evaluation evaluation, double alpha, double beta) {
        if (restDepth == 0)
            return new EvalResult(evaluation.score(board, stone), null);

        TranpositionTableKey key = new TranpositionTableKey(restTranpositionDepth, board);
        double lower = Double.NEGATIVE_INFINITY;
        double upper = Double.POSITIVE_INFINITY;
        if (restTranpositionDepth >= 0 && tranpositionTable.containsKey(key)) {
            TranpositionTableValue v = tranpositionTable.get(key);
            lower = v.lower;
            upper = v.upper;
            if (beta <= v.lower)
                return new EvalResult(v.lower, v.position);
            if (v.upper <= alpha || v.upper == v.lower)
                return new EvalResult(v.upper, v.position);
            alpha = Math.max(alpha, v.lower);
            beta = Math.min(beta, v.upper);
        }

        // 自分の番では、評価が最も大きくなるものを選ぶ。
        boolean didPlayed = false;
        double maxScore = -10000;
        Position p = null;
        EXTERNAL_LOOP: for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (!board.isPuttable(x, y, stone))
                    continue;
                didPlayed = true;
                Board b = new Board(board);
                Evaluation e = evaluation.clone();
                e.willPut(b, x, y, stone);
                b.put(x, y, stone);

                double a = Math.max(alpha, maxScore);
                double score = -eval(b, restDepth - 1, restTranpositionDepth - 1, stone.flip(), tranpositionTable, e, -(a+1), -a).getScore();
                if (a < score && score < beta) {
                    e = evaluation.clone();
                    e.willPut(board, x, y, stone);
                    score = -eval(b, restDepth - 1, restTranpositionDepth - 1, stone.flip(), tranpositionTable, e, -beta, -score).getScore();
                }

                if (maxScore < score) {
                    maxScore = score;
                    p = new Position(x, y);
                }

                // beta cut
                if (maxScore >= beta)
                    break EXTERNAL_LOOP;
            }
        }

        if (didPlayed) {
            assert (p != null);

            if (restTranpositionDepth >= 0) {
                if (maxScore <= alpha)
                    tranpositionTable.put(key,  new TranpositionTableValue(lower, maxScore, p));
                else if (beta <= maxScore)
                    tranpositionTable.put(key,  new TranpositionTableValue(maxScore, upper, p));
                else
                    tranpositionTable.put(key,  new TranpositionTableValue(maxScore, maxScore, p));
            }

            return new EvalResult(maxScore, p);
        } else {
            double score = -eval(board, restDepth - 1, restTranpositionDepth - 1, stone.flip(), tranpositionTable, evaluation, -beta, -alpha).getScore();
            return new EvalResult(score, null);
        }
    }
}
