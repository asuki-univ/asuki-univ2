package player.ai;

import game.Game;
import game.Game.Winner;
import player.Player;
import board.Board;
import board.Position;
import board.Turn;

/**
 * 
 * @author tomohiko
 * 
 */
public class SimpleMonteCarloPlayer extends Player {

    private int playoutCount;

    public SimpleMonteCarloPlayer(Turn turn, int playoutCount) {
        super(turn);
        this.playoutCount = playoutCount;
    }

    @Override
    public Position play(Board board) {
        long start = System.currentTimeMillis();
        double maxRate = -1;
        Position maxPosition = null;
        // 全部の打てる手で同じ回数だけプレイアウトを行う
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (!board.isPuttable(x, y, turn.stone())) {
                    continue;
                }
                double rate = playout(x, y, board);
                // System.out.println("x: " + x + ", y: " + y + ", rate: " + rate);
                if (rate > maxRate) {
                    maxRate = rate;
                    maxPosition = new Position(x, y);
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("play duration: " + (end - start));
        return maxPosition;
    }

    private double playout(int x, int y, Board board) {
        // ランダムに打ち合うプレーヤーを準備
        Player blackPlayer = new RandomPlayer(Turn.BLACK);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
        }
        Player whitePlayer = new RandomPlayer(Turn.WHITE);

        // 1手進める
        Board nextBoard = new Board(board);
        nextBoard.put(x, y, turn.stone());
        
        // 勝敗を記録
        int win = 0;
        Game game = new Game();
        for (int count = 0; count < playoutCount; ++count) {
            Winner winner = game.play(blackPlayer, whitePlayer, new Board(nextBoard), turn.flip(), false);
            if (turn == Turn.BLACK && winner == Winner.BLACK || turn == Turn.WHITE && winner == Winner.WHITE) {
                ++win;
            }
        }
        return (double) win / playoutCount;
    }

}
