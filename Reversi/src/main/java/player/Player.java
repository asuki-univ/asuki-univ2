package player;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public abstract class Player {
    protected final Turn turn;

    protected Player(Turn turn) {
        this.turn = turn;
    }
    
    abstract public Position play(Board board);
    
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
