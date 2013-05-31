package player.ai.searcher;

import java.util.List;

import player.ai.EvalResult;
import player.ai.eval.Evaluator;
import board.Board;
import board.Position;
import board.Turn;

public class AlphaBetaSearcher implements Searcher {

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

        // 自分の番では、評価が最も大きくなるものを選ぶ。
        double maxScore = Double.NEGATIVE_INFINITY;
        Position selectedPosition = null;

        for (Position p : puttablePositions) {
            Board b = board.clone();
            Evaluator e = evaluator.clone();

            b.put(p.x, p.y, currentTurn.stone());
            e.willPut(board, p.x, p.y, currentTurn.stone());

            double score = -eval(b, restDepth - 1, currentTurn.flip(), e, -beta, -Math.max(alpha, maxScore)).getScore();
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
}
