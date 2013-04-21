package player.ai;

import game.Game;
import game.Game.Winner;

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
public class UCBMonteCarloPlayer extends Player {

    private int playoutCount;

    public UCBMonteCarloPlayer(Turn turn, int playoutCount) {
        super(turn);
        this.playoutCount = playoutCount;
    }

    @Override
    public Position play(Board board) {
        double maxRate = -1;
        Position maxPosition = selectDefaultPosition(board);
        // 全部の打てる手を平等に探索する
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

    private Position selectDefaultPosition(Board board) {
        Random random = new Random();
        while (true) {
            int x = random.nextInt(8) + 1;
            int y = random.nextInt(8) + 1;
            if (board.isPuttable(x, y, turn.stone()))
                return new Position(x, y);
        }
    }

    private double playout(int x, int y, Board board) {
        int win = 0;
        Game game = new Game();
        Player blackPlayer = new RandomPlayer(Turn.BLACK);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
        Player whitePlayer = new RandomPlayer(Turn.WHITE);

        for (int count = 0; count < playoutCount; ++count) {
            Winner winner = game.play(blackPlayer, whitePlayer, new Board(board), turn.flip(), false);
            if (turn == Turn.BLACK && winner == Winner.BLACK || turn == Turn.WHITE && winner == Winner.WHITE)
                ++win;
        }
        return (double) win / playoutCount;
    }

}
