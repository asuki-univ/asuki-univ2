package player.ai;

import player.Player;
import player.ai.eval.NumStoneEvaluator;
import board.Board;
import board.Position;

public class PerfectPlayWrapperPlayer extends Player {
    private final AbstractAIPlayer originalPlayer;
    private final int completeReadingThreshold;

    public PerfectPlayWrapperPlayer(AbstractAIPlayer originalPlayer, int completeReadingThreshold) {
        super(originalPlayer.getTurn());
        this.originalPlayer = originalPlayer;
        this.completeReadingThreshold = completeReadingThreshold;
    }

    @Override
    public Position play(Board board) {
        int restHand = 64 - board.countStones();
        System.out.println(restHand);

        if (restHand <= completeReadingThreshold) {
            long beginTime = System.currentTimeMillis();
            Position p = originalPlayer.makeSearcher().eval(board, restHand, turn, new NumStoneEvaluator(turn)).getPosition();
            long endTime = System.currentTimeMillis();
            System.out.println("reading time = " + (endTime - beginTime) + " [ms]");
            return p;
        }

        return originalPlayer.play(board);
    }
}
