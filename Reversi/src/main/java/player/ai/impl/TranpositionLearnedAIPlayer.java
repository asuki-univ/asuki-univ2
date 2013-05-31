package player.ai.impl;

import player.ai.AbstractAIPlayer;
import player.ai.eval.Evaluator;
import player.ai.eval.LearnedEvaluator;
import player.ai.searcher.Searcher;
import player.ai.searcher.TranpositionTableSearcher;
import board.Turn;

public class TranpositionLearnedAIPlayer extends AbstractAIPlayer {
    public TranpositionLearnedAIPlayer(Turn turn, int maxDepth) {
        super(turn, maxDepth);
    }

    @Override
    protected Evaluator makeEvaluator() {
        return new LearnedEvaluator(turn, "params.dat");
    }

    @Override
    protected Searcher makeSearcher() {
        return new TranpositionTableSearcher();
    }
}
