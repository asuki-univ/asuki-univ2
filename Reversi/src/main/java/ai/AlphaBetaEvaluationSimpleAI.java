package ai;

import player.Player;
import ai.eval.BoardScoreEvaluation;
import ai.eval.Evaluation;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class AlphaBetaEvaluationSimpleAI implements Player {
    private static final int[][] EVAL_VALUES = {
        { 100, -50, 35, 30, 30, 35, -50, 100 },
        { -50, -70, 10, 15, 15, 10, -70, -50 },
        {  35,  10, 20, 25, 25, 20,  10,  35 },
        {  30,  15, 25, 50, 50, 25,  15,  30 },
        {  30,  15, 25, 50, 50, 25,  15,  30 },
        {  35,  10, 20, 25, 25, 20,  10,  35 },
        { -50, -70, 10, 15, 15, 10, -70, -50 },
        { 100, -50, 35, 30, 30, 35, -50, 100 },
    };
    
    private final Turn turn;
    private final int maxDepth;
    
    public AlphaBetaEvaluationSimpleAI(Turn turn, int maxDepth) {
        this.turn = turn;
        this.maxDepth = maxDepth;
    }
    
    @Override
    public Position play(Board board) {
        return eval(board, maxDepth, turn.stone(), new BoardScoreEvaluation(turn), Integer.MIN_VALUE, Integer.MAX_VALUE).getPosition();
    }
    
    private EvalResult eval(Board board, int restDepth, Stone stone, Evaluation evaluation, int alpha, int beta) {
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
                    e.willPut(board, x, y, stone);
                    b.put(x, y, stone);                    
                    int score = -eval(b, restDepth - 1, stone.flip(), e, -beta, -Math.max(alpha, maxScore)).getScore();
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
