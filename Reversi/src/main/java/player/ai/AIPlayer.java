package player.ai;

import java.util.ArrayList;
import java.util.List;

import player.Player;

import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public abstract class AIPlayer extends Player {
    public AIPlayer(Turn turn) {
        super(turn);
    }

    protected List<Position> findPuttableHands(Board board, Stone stone) {
        List<Position> ps = new ArrayList<Position>();
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (board.isPuttable(x, y, stone))
                    ps.add(new Position(x, y));
            }
        }

        return ps;
    }
}
