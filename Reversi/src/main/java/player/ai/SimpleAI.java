package player.ai;

import player.Player;
import board.Board;
import board.Position;
import board.Turn;

public class SimpleAI extends Player {
    private static final int[][] EVAL_VALUES = {
        { 100, -50, 35, 30, 30, 35, -50, 100 },
        { -50, -70, 10, 15, 15, 10, -70, -50 },
        {  35,  10, 20, 25, 25, 20,  10,  35 },
        {  30,  15, 25, 50, 50, 25,  15,  30 },
        {  30,  15, 25, 50, 50, 25,  15,  30 },
        {  35,  10, 20, 25, 25, 20,  10,  35 },
        { -50, -70, 10, 15, 15, 10, -70, -50 },
        { 100, -50, 35, 30, 30, 35, -50, 100 },
    };
    
    public SimpleAI(Turn turn) {
        super(turn);
    }
    
    @Override
    public Position play(Board board) {
        return eval(board).getPosition();
    }
    
    private EvalResult eval(Board board) {
        Position p = null;
        int maxValue = -200;

        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, turn.stone())) {
                    if (maxValue < EVAL_VALUES[y-1][x-1]) {
                        maxValue = EVAL_VALUES[y-1][x-1];
                        p = new Position(x, y);
                    }
                }
            }
        }

        assert(p != null);
        return new EvalResult(maxValue, p);
    }
}
