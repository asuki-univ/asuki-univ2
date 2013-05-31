package player.ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public class BoardScoreEvaluator implements Evaluator {
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

    public BoardScoreEvaluator(Turn turn) {
        this(turn, 0);
    }

    private BoardScoreEvaluator(Turn turn, int score) {
        this.turn = turn;
        this.currentScore = score;
    }

    @Override
    public Evaluator clone() {
        return new BoardScoreEvaluator(turn, currentScore);
    }

    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        if (stone.equals(turn.stone()))
            currentScore += EVAL_VALUES[y-1][x-1];
        else
            currentScore -= EVAL_VALUES[y-1][x-1];
    }

    @Override
    public double score(Board board, Turn currentTurn) {
        // 順番が自分でなければ、負の得点を返す。
        if (currentTurn.equals(turn.stone()))
            return currentScore;
        else
            return -currentScore;
    }
}
