package player.ai.simple;

import java.util.List;

import player.ai.EvalResult;
import board.Board;
import board.Position;
import board.Turn;

public class NegaMaxSimpleAIPlayer extends SimpleAIPlayer {
    private final int maxDepth;

    public NegaMaxSimpleAIPlayer(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return eval(board, maxDepth, turn, 0).getPosition();
    }

    private EvalResult eval(Board board, int restDepth, Turn currentTurn, int scoreSum) {
        if (restDepth == 0)
            return new EvalResult(currentTurn == turn ? scoreSum : -scoreSum, null);

        // 置ける手を全て列挙
        List<Position> puttablePositions = board.findPuttableHands(currentTurn.stone());

        // 自分がおける場所がないならば、相手のターンになる。
        if (puttablePositions.isEmpty()) {
            double score = -eval(board, restDepth - 1, currentTurn.flip(), scoreSum).getScore();
            return new EvalResult(score, null);
        }

        // 自分の番では、評価が最も大きくなるものを選ぶ。
        double maxScore = Double.NEGATIVE_INFINITY;
        Position selectedPosition = null;

        for (Position p : puttablePositions) {
            Board b = board.clone();
            b.put(p.x, p.y, currentTurn.stone());
            // この評価値が反転していることに注意
            double score = -eval(b, restDepth - 1, currentTurn.flip(), scoreSum + (currentTurn == turn ? EVAL_VALUES[p.y - 1][p.x - 1] : -EVAL_VALUES[p.y - 1][p.x - 1])).getScore();
            if (maxScore < score) {
                maxScore = score;
                selectedPosition = p;
            }
        }

        assert (selectedPosition != null);
        return new EvalResult(maxScore, selectedPosition);
    }
}
