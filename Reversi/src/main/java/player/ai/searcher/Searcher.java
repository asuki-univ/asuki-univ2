package player.ai.searcher;

import player.ai.EvalResult;
import player.ai.eval.Evaluator;
import board.Board;
import board.Turn;

public interface Searcher {
    public EvalResult eval(Board board, int restDepth, Turn currentTurn, Evaluator evaluator);
}
