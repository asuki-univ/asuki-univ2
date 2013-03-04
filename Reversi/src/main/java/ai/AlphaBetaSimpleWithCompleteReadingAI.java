package ai;

import ai.eval.NumStoneEvaluation;
import board.Board;
import board.Position;
import board.Turn;

public class AlphaBetaSimpleWithCompleteReadingAI extends AlphaBetaSimpleAI {
    private final int completeReadingThreshold;
    
    public AlphaBetaSimpleWithCompleteReadingAI(Turn turn, int maxDepth, int completeReadingThreshold) {
        super(turn, maxDepth);
        this.completeReadingThreshold = completeReadingThreshold;
    }
    
    @Override
    public Position play(Board board) {
        int restHand = 64 - board.countStones();
        System.out.println(restHand);
        if (restHand <= completeReadingThreshold)
            return eval(board, restHand, turn.stone(), new NumStoneEvaluation(turn), -100, 100).getPosition();
        return super.play(board);
    }
}
