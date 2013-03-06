package player.ai;

import player.Player;
import player.ai.eval.BoardScoreEvaluation;
import player.ai.eval.Evaluation;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class NegaScoutWithMoveOrderingSimpleAI extends Player {
    private final int maxDepth;

    public NegaScoutWithMoveOrderingSimpleAI(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return eval(board, maxDepth, turn.stone(), new BoardScoreEvaluation(turn), Integer.MIN_VALUE, Integer.MAX_VALUE).getPosition();
    }

    protected EvalResult eval(Board board, int restDepth, Stone stone, Evaluation evaluation, int alpha, int beta) {
        if (restDepth == 0)
            return new EvalResult(evaluation.score(board, stone), null);

        // 自分の番では、評価が最も大きくなるものを選ぶ。
        boolean didPlayed = false;
        int maxScore = -10000;
        Position p = null;
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, stone)) {
                    didPlayed = true;
                    Board b = new Board(board);
                    Evaluation e = evaluation.clone();
                    e.willPut(b, x, y, stone);
                    b.put(x, y, stone);
                    
                    int a = Math.max(alpha, maxScore);
                    int score = -eval(b, restDepth - 1, stone.flip(), e, -(a+1), -a).getScore();
                    if (a < score && score < beta) {
                        e = evaluation.clone();
                        e.willPut(board, x, y, stone);
                        score = -eval(b, restDepth - 1, stone.flip(), e, -beta, -score).getScore();
                    }
                    
                    if (maxScore < score) {
                        maxScore = score;
                        p = new Position(x, y);
                    }

                    // beta cut
                    if (maxScore >= beta)
                        return new EvalResult(score, p);
                }
            }
        }

        if (didPlayed) {
            assert (p != null);
            return new EvalResult(maxScore, p);
        } else {
            int score = -eval(board, restDepth - 1, stone.flip(), evaluation, -beta, -alpha).getScore();
            return new EvalResult(score, null);
        }
    }
}
