package player.ai.impl;

import player.ai.AbstractAIPlayer;
import player.ai.eval.Evaluator;
import player.ai.eval.LearnedEvaluator;
import player.ai.searcher.AlphaBetaSearcher;
import player.ai.searcher.Searcher;
import board.Turn;

public class AlphaBetaLearnedAIPlayer extends AbstractAIPlayer {
    public AlphaBetaLearnedAIPlayer(Turn turn, int maxDepth) {
        super(turn, maxDepth);
    }

    @Override
    protected Evaluator makeEvaluator() {
        return new LearnedEvaluator(turn, "params.dat");
    }

    @Override
    protected Searcher makeSearcher() {
        return new AlphaBetaSearcher();
    }
}
