package player.ai;

import java.util.Random;

import player.Player;
import board.Board;
import board.Position;
import board.Turn;

/**
 * 
 * @author tomohiko
 *
 */
public class MonteCarloPlayer extends Player {

    private Random random = new Random();
    
    public MonteCarloPlayer(Turn turn) {
        super(turn);
    }
    
    @Override
    public Position play(Board board) {
        while (true) {
            double maxRate = 0;
            Position maxPosition = selectPosition(board);
            for (int x = 1; x <= 8; ++x) {
                for (int y = 1; y <= 8; ++y) {
                    double rate = playout(x, y, new Board(board)); // TODO board.copy
                    if (rate > maxRate) {
                        maxRate = rate;
                        maxPosition = new Position(x, y);
                    }
                }
            }
            
            return maxPosition;
        }
    }
    
    private Position selectPosition(Board board) {
        while (true) {
            int x = random.nextInt(8) + 1;
            int y = random.nextInt(8) + 1;
            if (board.isPuttable(x, y, turn.stone()))
                return new Position(x, y);
        }
    }

    private double playout(int x, int y, Board board) {
        // TODO playout
        return 0;
    }
    

}
