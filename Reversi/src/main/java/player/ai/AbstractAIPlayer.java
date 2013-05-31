package player.ai;

import player.Player;
import player.ai.eval.Evaluator;
import player.ai.searcher.Searcher;
import board.Board;
import board.Position;
import board.Turn;

public abstract class AbstractAIPlayer extends Player {
    private final int maxDepth;

    public AbstractAIPlayer(Turn turn, int maxDepth) {
        super(turn);
        this.maxDepth = maxDepth;
    }

    @Override
    public Position play(Board board) {
        return makeSearcher().eval(board, maxDepth, turn, makeEvaluator()).getPosition();
    }

    protected abstract Evaluator makeEvaluator();
    protected abstract Searcher makeSearcher();
}
