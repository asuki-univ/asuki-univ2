package ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public class BoardScoreEvaluation implements Evaluation {
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
    private int currentScore;
    
    public BoardScoreEvaluation(Turn turn) {
        this(turn, 0);
    }
    
    private BoardScoreEvaluation(Turn turn, int score) {
        this.turn = turn;
        this.currentScore = score;
    }
    
    @Override
    public Evaluation clone() {
        return new BoardScoreEvaluation(turn, currentScore);
    }
    
    @Override
    public int score(Board board, Stone stone) {
        if (stone == turn.stone())
            return currentScore;
        else
            return -currentScore;
    }
    
    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        if (stone == turn.stone())
            currentScore += EVAL_VALUES[y-1][x-1];
        else
            currentScore -= EVAL_VALUES[y-1][x-1];
    }
}
