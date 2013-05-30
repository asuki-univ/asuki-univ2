package player.ai.searcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.ai.EvalResult;
import player.ai.eval.Evaluator;
import board.Board;
import board.Position;
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

public class TranpositionTableSearcher implements Searcher {

    @Override
    public EvalResult eval(Board board, int restDepth, Turn currentTurn, Evaluator evaluator) {
        return eval(board, restDepth, 5, currentTurn,
                new HashMap<TranpositionTableKey, TranpositionTableValue>(),
                evaluator, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    protected EvalResult eval(Board board, int restDepth, int restTranpositionDepth, Turn currentTurn,
            Map<TranpositionTableKey, TranpositionTableValue> tranpositionTable, Evaluator evaluator, double alpha, double beta) {
        if (restDepth == 0)
            return new EvalResult(evaluator.score(board, currentTurn), null);

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

        List<Position> puttablePositions = board.findPuttableHands(currentTurn.stone());
        if (puttablePositions.isEmpty()) {
            double score = -eval(board, restDepth - 1, restTranpositionDepth - 1, currentTurn.flip(), tranpositionTable, evaluator, -beta, -alpha).getScore();
            return new EvalResult(score, null);
        }

        // 自分の番では、評価が最も大きくなるものを選ぶ。

        double maxScore = Double.NEGATIVE_INFINITY;
        Position selectedPosition = null;

        for (Position p : puttablePositions) {
            Board b = board.clone();
            Evaluator e = evaluator.clone();

            e.willPut(b, p.x, p.y, currentTurn.stone());
            b.put(p.x, p.y, currentTurn.stone());

            double a = Math.max(alpha, maxScore);
            double score = -eval(b, restDepth - 1, restTranpositionDepth - 1, currentTurn.flip(), tranpositionTable, e, -(a+1), -a).getScore();
            if (a < score && score < beta) {
                e = evaluator.clone();
                e.willPut(board, p.x, p.y, currentTurn.stone());
                score = -eval(b, restDepth - 1, restTranpositionDepth - 1, currentTurn.flip(), tranpositionTable, e, -beta, -score).getScore();
            }

            if (maxScore < score) {
                maxScore = score;
                selectedPosition = p;
            }

            // beta cut
            if (maxScore >= beta)
                break;
        }

        assert (selectedPosition != null);

        if (restTranpositionDepth >= 0) {
            if (maxScore <= alpha)
                tranpositionTable.put(key,  new TranpositionTableValue(lower, maxScore, selectedPosition));
            else if (beta <= maxScore)
                tranpositionTable.put(key,  new TranpositionTableValue(maxScore, upper, selectedPosition));
            else
                tranpositionTable.put(key,  new TranpositionTableValue(maxScore, maxScore, selectedPosition));
        }

        return new EvalResult(maxScore, selectedPosition);
    }
}
