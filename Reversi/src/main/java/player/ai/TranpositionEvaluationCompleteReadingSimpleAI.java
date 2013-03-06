package player.ai;

import java.util.HashMap;

import player.ai.eval.NumStoneEvaluation;

import board.Board;
import board.Position;
import board.Turn;

public class TranpositionEvaluationCompleteReadingSimpleAI extends TranpositionEvaluationSimpleAI {
    private final int completeReadingThreshold;

    public TranpositionEvaluationCompleteReadingSimpleAI(Turn turn, int maxDepth, int completeReadingThreshold) {
        super(turn, maxDepth);
        this.completeReadingThreshold = completeReadingThreshold;
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
        return super.play(board);
    }
}
