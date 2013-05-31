package player.ai.impl;

import player.ai.AbstractAIPlayer;
import player.ai.eval.BoardScoreEvaluator;
import player.ai.eval.Evaluator;
import player.ai.searcher.AlphaBetaSearcher;
import player.ai.searcher.Searcher;
import board.Turn;

public class AlphaBetaBoardScoreAIPlayer extends AbstractAIPlayer {
    public AlphaBetaBoardScoreAIPlayer(Turn turn, int maxDepth) {
        super(turn, maxDepth);
    }

    @Override
    protected Evaluator makeEvaluator() {
        return new BoardScoreEvaluator(turn);
    }

    @Override
    protected Searcher makeSearcher() {
        return new AlphaBetaSearcher();
    }
}
