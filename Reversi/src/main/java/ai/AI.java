package ai;

import board.Board;

public interface AI {
    public abstract EvalResult eval(Board board);
}
