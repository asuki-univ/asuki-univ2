package player.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Player;
import player.ai.eval.BoardScoreEvaluation;
import player.ai.eval.Evaluation;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

class HandComparator implements Comparator<EvalResult> {
    @Override
    public int compare(EvalResult lhs, EvalResult rhs) {
        if (lhs.getScore() != rhs.getScore())
            return lhs.getScore() > rhs.getScore() ? -1 : 1;
        return 0;
    }
}

public class TranpositionMoveOrderingSimpleAI extends Player {
    private final int maxDepth;

    public TranpositionMoveOrderingSimpleAI(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        List<Position> ps = findPuttableHands(board, turn.stone());
        ps = doMoveOrdering(board, ps);

        return eval(board, maxDepth, 5, turn.stone(), ps,
                new HashMap<TranpositionTableKey, TranpositionTableValue>(),
                new BoardScoreEvaluation(turn), Integer.MIN_VALUE, Integer.MAX_VALUE).getPosition();
    }

    protected List<Position> doMoveOrdering(Board board, List<Position> ps) {
        List<EvalResult> shallowEvaluation = new ArrayList<EvalResult>();
        for (Position p : ps) {
            Board b = board.clone();
            Evaluation e = new BoardScoreEvaluation(turn);
            e.willPut(board, p.x, p.y, turn.stone());
            b.put(p.x, p.y, turn.stone());

            EvalResult er = eval(b, 2, -1, turn.stone().flip(),
                    findPuttableHands(board, turn.stone().flip()),
                    new HashMap<TranpositionTableKey, TranpositionTableValue>(), e, -10000, 10000);
            shallowEvaluation.add(new EvalResult(-er.getScore(), p));
        }

        // スコアの大きい方から並べる。
        Collections.sort(shallowEvaluation, new HandComparator());

        List<Position> result = new ArrayList<Position>();
        for (EvalResult er : shallowEvaluation)
            result.add(er.getPosition());
        return result;
    }

    protected EvalResult eval(Board board, int restDepth, int restTranpositionDepth, Stone stone,
            List<Position> puttablePositions,
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
        Position maxScorePosition = null;

        for (Position p : puttablePositions) {
            int x = p.x, y = p.y;
            didPlayed = true;
            Board b = board.clone();
            Evaluation e = evaluation.clone();
            e.willPut(b, x, y, stone);
            b.put(x, y, stone);

            double a = Math.max(alpha, maxScore);
            List<Position> nextPuttablePositions = findPuttableHands(b, stone.flip());
            double score = -eval(b, restDepth - 1, restTranpositionDepth - 1, stone.flip(), nextPuttablePositions, tranpositionTable, e, -(a+1), -a).getScore();
            if (a < score && score < beta) {
                e = evaluation.clone();
                e.willPut(board, x, y, stone);
                score = -eval(b, restDepth - 1, restTranpositionDepth - 1, stone.flip(), nextPuttablePositions, tranpositionTable, e, -beta, -score).getScore();
            }

            if (maxScore < score) {
                maxScore = score;
                maxScorePosition = new Position(x, y);
            }

            // beta cut
            if (maxScore >= beta)
                break;
        }

        if (didPlayed) {
            assert (maxScorePosition != null);

            if (restTranpositionDepth >= 0) {
                if (maxScore <= alpha)
                    tranpositionTable.put(key,  new TranpositionTableValue(lower, maxScore, maxScorePosition));
                else if (beta <= maxScore)
                    tranpositionTable.put(key,  new TranpositionTableValue(maxScore, upper, maxScorePosition));
                else
                    tranpositionTable.put(key,  new TranpositionTableValue(maxScore, maxScore, maxScorePosition));
            }

            return new EvalResult(maxScore, maxScorePosition);
        } else {
            double score = -eval(board, restDepth - 1, restTranpositionDepth - 1, stone.flip(), findPuttableHands(board, stone.flip()), tranpositionTable, evaluation, -beta, -alpha).getScore();
            return new EvalResult(score, null);
        }
    }
}
