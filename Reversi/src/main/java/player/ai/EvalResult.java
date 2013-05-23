package player.ai;

import board.Position;

public class EvalResult {
    private double score;
    private Position position;

    public EvalResult(double score, Position position) {
        this.score = score;
        this.position = position;
    }

    public Position getPosition() { return position; }
    public double getScore() { return score; }
}
