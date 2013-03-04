package ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public class NumStoneEvaluation implements Evaluation {
    private final Turn turn;
    
    public NumStoneEvaluation(Turn turn) {
        this.turn = turn;
    }
    
    @Override
    public Evaluation clone() {
        return new NumStoneEvaluation(turn);
    }
    
    @Override
    public int score(Board board, Stone stone) {
        if (turn.stone() == stone)
            return board.countStone(turn.stone()) - board.countStone(turn.stone().flip());
        else
            return board.countStone(turn.stone().flip()) - board.countStone(turn.stone());
    }
    
    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        // Do nothing.
    }
}
