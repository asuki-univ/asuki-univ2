package player;

import ai.AI;
import board.Board;
import board.Position;

public class AIPlayer implements Player {
    private AI ai;
    
    public AIPlayer(AI ai) {
        this.ai = ai;
    }
    
    @Override
    public Position play(Board board) {
        return ai.eval(board).getPosition();
    }
}
