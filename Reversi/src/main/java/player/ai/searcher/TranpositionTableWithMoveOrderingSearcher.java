package player.ai.searcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.ai.EvalResult;
import player.ai.eval.Evaluator;
import board.Board;
import board.Position;
import board.Turn;

public class TranpositionTableWithMoveOrderingSearcher implements Searcher {

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

        doMoveOrdering(board, puttablePositions, currentTurn, evaluator);

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

    protected List<Position> doMoveOrdering(Board board, List<Position> ps, Turn currentTurn, Evaluator evaluator) {
        List<EvalResult> shallowEvaluation = new ArrayList<EvalResult>();
        for (Position p : ps) {
            Board b = board.clone();
            Evaluator e = evaluator.clone();

            e.willPut(board, p.x, p.y, currentTurn.stone());
            b.put(p.x, p.y, currentTurn.stone());

            EvalResult er = eval(b, 2, currentTurn.flip(), e);
            shallowEvaluation.add(new EvalResult(-er.getScore(), p));
        }

        // スコアの大きい方から並べる。
        Collections.sort(shallowEvaluation, new HandComparator());

        List<Position> result = new ArrayList<Position>();
        for (EvalResult er : shallowEvaluation)
            result.add(er.getPosition());
        return result;
    }
}
