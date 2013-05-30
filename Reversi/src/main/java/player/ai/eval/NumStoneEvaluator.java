package player.ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public class NumStoneEvaluator implements Evaluator {
    private final Turn turn;

    public NumStoneEvaluator(Turn turn) {
        this.turn = turn;
    }

    @Override
    public Evaluator clone() {
        return new NumStoneEvaluator(turn);
    }

    @Override
    public double score(Board board, Turn currentTurn) {
        if (turn == currentTurn)
            return board.countStone(turn.stone()) - board.countStone(turn.stone().flip());
        else
            return board.countStone(turn.stone().flip()) - board.countStone(turn.stone());
    }

    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        // Do nothing.
    }
}
