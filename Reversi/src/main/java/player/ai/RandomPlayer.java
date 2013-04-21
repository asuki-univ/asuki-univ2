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
public class RandomPlayer extends Player {

    private Random random = new Random();
    
    public RandomPlayer(Turn turn) {
        super(turn);
    }
    
    @Override
    public Position play(Board board) {
        // 打てるところにランダムに打つ
        while (true) {
            int x = random.nextInt(8) + 1;
            int y = random.nextInt(8) + 1;
            if (!board.isPuttable(x, y, turn.stone())) {
                //System.out.println("Invalid.");
                continue;
            }
            
            return new Position(x, y);
        }
    }

}
