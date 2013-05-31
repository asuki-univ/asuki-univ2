package player.ai.impl;

import player.ai.AbstractAIPlayer;
import player.ai.eval.AdvancedEvaluator;
import player.ai.eval.Evaluator;
import player.ai.searcher.Searcher;
import player.ai.searcher.TranpositionTableSearcher;
import board.Turn;

public class TranpositionAdvancedAIPlayer extends AbstractAIPlayer {
    public TranpositionAdvancedAIPlayer(Turn turn, int maxDepth) {
        super(turn, maxDepth);
    }

    @Override
    protected Evaluator makeEvaluator() {
        return new AdvancedEvaluator(turn);
    }

    @Override
    protected Searcher makeSearcher() {
        return new TranpositionTableSearcher();
    }
}
