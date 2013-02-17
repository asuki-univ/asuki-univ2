package player;

import board.Board;
import board.Position;

public interface Player {
    public Position play(Board board);
}
