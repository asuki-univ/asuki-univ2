package player.ai.simple;

import java.util.List;

import player.ai.EvalResult;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class NegaMaxSimpleAI extends SimpleAIPlayer {
    private final int maxDepth;

    public NegaMaxSimpleAI(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return eval(board, maxDepth, turn.stone(), 0).getPosition();
    }

    private EvalResult eval(Board board, int restDepth, Stone stone, int scoreSum) {
        if (restDepth == 0) {
            // この評価値を反転していることに注意
            return new EvalResult(-scoreSum, null);
        }

        // 置ける手を全て列挙
        List<Position> puttablePositions = findPuttableHands(board, stone);

        // 自分がおける場所がないならば、相手のターンになる。
        if (puttablePositions.isEmpty()) {
            double score = -eval(board, restDepth - 1, stone.flip(), scoreSum).getScore();
            return new EvalResult(score, null);
        }

        // 自分の番では、評価が最も大きくなるものを選ぶ。
        double maxScore = Double.NEGATIVE_INFINITY;
        Position p = null;
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, stone)) {
                    Board b = board.clone();
                    b.put(x, y, stone);
                    // この評価値が反転していることに注意
                    double score = -eval(b, restDepth - 1, stone.flip(), scoreSum + (stone == turn.stone() ? EVAL_VALUES[y-1][x-1] : -EVAL_VALUES[y-1][x-1])).getScore();
                    if (maxScore < score) {
                        maxScore = score;
                        p = new Position(x, y);
                    }
                }
            }
        }


        assert p != null;
        return new EvalResult(maxScore, p);
    }
}
