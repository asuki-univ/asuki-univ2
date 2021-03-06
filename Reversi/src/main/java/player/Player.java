package player;

import board.Board;
import board.Position;
import board.Turn;

public abstract class Player {
    protected final Turn turn;

    protected Player(Turn turn) {
        this.turn = turn;
    }

    public Turn getTurn() {
        return turn;
    }

    abstract public Position play(Board board);
}
