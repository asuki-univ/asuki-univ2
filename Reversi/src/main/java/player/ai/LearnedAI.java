package player.ai;

import java.io.IOException;
import java.util.HashMap;

import player.ai.eval.Evaluation;
import player.ai.eval.LearnedEvaluation;
import player.ai.eval.NumStoneEvaluation;
import board.Board;
import board.Position;
import board.Turn;

public class LearnedAI extends TranpositionEvaluationSimpleAI {
    private Evaluation evaluation;
    private final int completeReadingThreshold;

    public LearnedAI(Turn turn, int maxDepth, int completeReadingThreshold) throws IOException {
        super(turn, maxDepth);
        this.completeReadingThreshold = completeReadingThreshold;

        evaluation = new LearnedEvaluation(turn, "params.dat");
    }

    @Override
    public Position play(Board board) {
        int restHand = 64 - board.countStones();
        System.out.println(restHand);
        if (restHand <= completeReadingThreshold) {
            long beginTime = System.currentTimeMillis();
            Position p = eval(board, restHand, 5, turn.stone(), new HashMap<TranpositionTableKey, TranpositionTableValue>(), new NumStoneEvaluation(turn), -100, 100).getPosition();
            long endTime = System.currentTimeMillis();
            System.out.println("reading time = " + (endTime - beginTime) + " [ms]");
            return p;
        }

        return eval(board, maxDepth, 5, turn.stone(),
                new HashMap<TranpositionTableKey, TranpositionTableValue>(),
                evaluation, Integer.MIN_VALUE, Integer.MAX_VALUE).getPosition();
    }
}
