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
            // Random は合同法なので乱数の取り方として不適
            int x = random.nextInt(Board.WIDTH) + 1;
            int y = random.nextInt(Board.HEIGHT) + 1;
            if (!board.isPuttable(x, y, turn.stone())) {
                //System.out.println("Invalid.");
                continue;
            }
            
            return new Position(x, y);
        }
    }

}
