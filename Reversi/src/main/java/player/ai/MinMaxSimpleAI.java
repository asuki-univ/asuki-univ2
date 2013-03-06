package player.ai;

import player.Player;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class MinMaxSimpleAI extends Player {
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
    
    private final int maxDepth;
    
    public MinMaxSimpleAI(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }
    
    @Override
    public Position play(Board board) {
        return evalMyTurn(board, maxDepth, turn.stone(), 0).getPosition();
    }
    
    private EvalResult evalMyTurn(Board board, int restDepth, Stone stone, int scoreSum) {
        if (restDepth == 0)
            return new EvalResult(scoreSum, null);
        
        // 自分の番では、評価が最も大きくなるものを選ぶ。
        boolean didPlayed = false;
        int maxScore = -10000;
        Position p = null;
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, stone)) {
                    didPlayed = true;
                    Board b = new Board(board);
                    b.put(x, y, stone);
                    int score = evalEnemyTurn(b, restDepth - 1, stone.flip(), scoreSum + EVAL_VALUES[y-1][x-1]).getScore();
                    if (maxScore < score) {
                        maxScore = score;
                        p = new Position(x, y);
                    }
                }
            }
        }
        
        if (didPlayed) {
            assert (p != null);
            return new EvalResult(maxScore, p);
        } else {
            int score = evalEnemyTurn(board, restDepth - 1, stone.flip(), scoreSum).getScore();
            return new EvalResult(score, null);
        }
    }
    
    private EvalResult evalEnemyTurn(Board board, int restDepth, Stone stone, int scoreSum) {
        if (restDepth == 0)
            return new EvalResult(scoreSum, null);
        
        // 相手の番では、評価が最も小さくなるものを選ぶ。EVAL_VALUES はひっくり返して使う。
        boolean didPlayed = false;
        int minScore = 10000;
        Position p = null;
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, stone)) {
                    didPlayed = true;
                    Board b = new Board(board);
                    b.put(x, y, stone);
                    int score = evalMyTurn(b, restDepth - 1, stone.flip(), scoreSum - EVAL_VALUES[y-1][x-1]).getScore();
                    if (score < minScore) {
                        minScore = score;
                        p = new Position(x, y);
                    }
                }
            }
        }
        
        if (didPlayed) {
            assert (p != null);
            return new EvalResult(minScore, p);
        } else {
            int score = evalMyTurn(board, restDepth - 1, stone.flip(), scoreSum).getScore();
            return new EvalResult(score, null);
        }        
    }
}
