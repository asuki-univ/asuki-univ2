package ai;

import board.Position;

public class EvalResult {
    private int score;
    private Position position;
    
    EvalResult(int score, Position position) {
        this.score = score;
        this.position = position;
    }
    
    public Position getPosition() { return position; }
    public int getScore() { return score; }
}
