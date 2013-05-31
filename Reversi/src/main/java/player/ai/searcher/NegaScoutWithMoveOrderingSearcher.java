package player.ai.searcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.ai.EvalResult;
import player.ai.eval.Evaluator;
import board.Board;
import board.Position;
import board.Turn;

public class NegaScoutWithMoveOrderingSearcher implements Searcher {
    public EvalResult eval(Board board, int restDepth, Turn currentTurn, Evaluator evaluator) {
        return eval(board, restDepth, currentTurn, evaluator, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private EvalResult eval(Board board, int restDepth, Turn currentTurn, Evaluator evaluator, double alpha, double beta) {
        if (restDepth == 0)
            return new EvalResult(evaluator.score(board, currentTurn), null);

        // 置ける手を全て列挙
        List<Position> puttablePositions = board.findPuttableHands(currentTurn.stone());

        // 自分がおける場所がないならば、相手のターンになる。
        if (puttablePositions.isEmpty()) {
            double score = -eval(board, restDepth - 1, currentTurn.flip(), evaluator, -beta, -alpha).getScore();
            return new EvalResult(score, null);
        }

        if (restDepth >= 5)
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
            double score = -eval(b, restDepth - 1, currentTurn.flip(), e, -(a+1), -a).getScore();
            if (a < score && score < beta) {
                e = evaluator.clone();
                e.willPut(board, p.x, p.y, currentTurn.stone());
                score = -eval(b, restDepth - 1, currentTurn.flip(), e, -beta, -score).getScore();
            }

            if (maxScore < score) {
                maxScore = score;
                selectedPosition = p;
            }

            // beta cut
            if (maxScore >= beta)
                return new EvalResult(score, p);
        }

        assert (selectedPosition != null);
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
