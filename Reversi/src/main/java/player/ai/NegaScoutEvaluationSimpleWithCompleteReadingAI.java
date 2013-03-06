package player.ai;

import player.ai.eval.NumStoneEvaluation;
import board.Board;
import board.Position;
import board.Turn;

public class NegaScoutEvaluationSimpleWithCompleteReadingAI extends NegaScoutEvaluationSimpleAI {
    private final int completeReadingThreshold;
    
    public NegaScoutEvaluationSimpleWithCompleteReadingAI(Turn turn, int maxDepth, int completeReadingThreshold) {
        super(turn, maxDepth);
        this.completeReadingThreshold = completeReadingThreshold;
    }
    
    @Override
    public Position play(Board board) {
        int restHand = 64 - board.countStones();
        System.out.println(restHand);
        if (restHand <= completeReadingThreshold) {
            long beginTime = System.currentTimeMillis();
            Position p = eval(board, restHand, turn.stone(), new NumStoneEvaluation(turn), -100, 100).getPosition();
            long endTime = System.currentTimeMillis();
            System.out.println("reading time = " + (endTime - beginTime) + " [ms]");
            return p;
        }
        return super.play(board);
    }
}
