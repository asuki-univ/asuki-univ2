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
        double maxRate = -1;
        Position maxPosition = new Position(0, 0);
        // 全部の打てる手で同じ回数だけプレイアウトを行う
        for (int x = 1; x <= 8; ++x) {
            for (int y = 1; y <= 8; ++y) {
                if (!board.isPuttable(x, y, turn.stone())) {
                    continue;
                }
                Board nextBoard = new Board(board);
                nextBoard.put(x, y, turn.stone());
                double rate = playout(x, y, nextBoard);
                System.out.println("x: " + x + ", y: " + y + ", rate: " + rate);
                if (rate > maxRate) {
                    maxRate = rate;
                    maxPosition = new Position(x, y);
                }
            }
        }
        return maxPosition;
    }

    private double playout(int x, int y, Board board) {
        int win = 0;
        Game game = new Game();
        Player blackPlayer = new RandomPlayer(Turn.BLACK);
        Player whitePlayer = new RandomPlayer(Turn.WHITE);

        for (int count = 0; count < playoutCount; ++count) {
            Winner winner = game.play(blackPlayer, whitePlayer, new Board(board), turn.flip(), false);
            if (turn == Turn.BLACK && winner == Winner.BLACK || turn == Turn.WHITE && winner == Winner.WHITE)
                ++win;
        }
        return (double) win / playoutCount;
    }

}
