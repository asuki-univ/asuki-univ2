package player.ai.simple;

import java.util.List;

import player.ai.EvalResult;
import board.Board;
import board.Position;
import board.Turn;

public class MinMaxSimpleAIPlayer extends SimpleAIPlayer {
    private final int maxDepth;

    public MinMaxSimpleAIPlayer(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return evalMyTurn(board, maxDepth, 0).getPosition();
    }

    private EvalResult evalMyTurn(Board board, int restDepth, int scoreSum) {
        // これ以上は読まない
        if (restDepth == 0)
            return new EvalResult(scoreSum, null);

        // 置ける手を全て列挙
        List<Position> puttablePositions = board.findPuttableHands(turn.stone());

        // 自分がおける場所がないならば、相手のターンになる。
        if (puttablePositions.isEmpty()) {
            double score = evalEnemyTurn(board, restDepth - 1, scoreSum).getScore();
            return new EvalResult(score, null);
        }

        // 自分の番では、相手が評価を最も小さくしてくるものの中で、評価が最も大きくなるものを選ぶ。
        double maxScore = Double.NEGATIVE_INFINITY;
        Position selectedPosition = null;

        for (Position p : puttablePositions) {
            Board b = board.clone();
            b.put(p.x, p.y, turn.stone());
            double score = evalEnemyTurn(b, restDepth - 1, scoreSum + EVAL_VALUES[p.y - 1][p.x - 1]).getScore();
            if (maxScore < score) {
                maxScore = score;
                selectedPosition = p;
            }
        }

        return new EvalResult(maxScore, selectedPosition);
    }

    private EvalResult evalEnemyTurn(Board board, int restDepth, int scoreSum) {
        // これ以上は読まない
        if (restDepth == 0)
            return new EvalResult(scoreSum, null);

        // 置ける手を全て列挙
        List<Position> puttablePositions = board.findPuttableHands(turn.flip().stone());

        // 相手がおける場所がないならば、自分のターンになる。
        if (puttablePositions.isEmpty()) {
            double score = evalMyTurn(board, restDepth - 1, scoreSum).getScore();
            return new EvalResult(score, null);
        }

        // 自分の番では、相手が評価を最も大きくしてくるものの中で、評価が最も小さくなるものを選ぶ。
        double minScore = Double.POSITIVE_INFINITY;
        Position selectedPosition = null;

        for (Position p : puttablePositions) {
            Board b = board.clone();
            b.put(p.x, p.y, turn.flip().stone());
            double score = evalMyTurn(b, restDepth - 1, scoreSum - EVAL_VALUES[p.y - 1][p.x - 1]).getScore();
            if (score < minScore) {
                minScore = score;
                selectedPosition = p;
            }
        }

        return new EvalResult(minScore, selectedPosition);
    }
}
