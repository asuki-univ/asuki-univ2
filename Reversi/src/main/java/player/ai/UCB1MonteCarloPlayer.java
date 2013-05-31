package player.ai;

import game.Game;
import game.Game.Winner;

import java.util.HashMap;
import java.util.Map;

import player.Player;
import board.Board;
import board.Position;
import board.Turn;

/**
 *
 * @author tomohiko
 *
 */
public class UCB1MonteCarloPlayer extends Player {

    private static final double UCB_COEFFICIENT = 0.2;
    private int maxUCBCount;

    public UCB1MonteCarloPlayer(Turn turn, int maxUCBCount) {
        super(turn);
        this.maxUCBCount = maxUCBCount;
    }

    @Override
    public Position play(Board board) {
        long start = System.currentTimeMillis();
        Map<Position, PlayoutResult> counter = new HashMap<Position, PlayoutResult>();
        int ucbCount = 0;

        // 1度は全ての可能な手で playout を行う
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (!board.isPuttable(x, y, turn.stone())) {
                    continue;
                }
                Position position = new Position(x, y);
                int win = playout(x, y, board);
                ++ucbCount;
                counter.put(position, new PlayoutResult(1, win));
            }
        }

        // UCB を計算し次の playout 対象を選ぶ
        Position maxPosition = null;
        for (; ucbCount < maxUCBCount; ++ucbCount) {
            double maxUCB = -1;
            for (Map.Entry<Position, PlayoutResult> entry : counter.entrySet()) {
                Position position = entry.getKey();
                PlayoutResult playoutResult = entry.getValue();
                double ucb = (double) playoutResult.winCount / playoutResult.playoutCount + UCB_COEFFICIENT * Math.sqrt(Math.log(ucbCount) / playoutResult.playoutCount);
                //System.out.println("x: " + position.x + ", y: " + position.y + ", ucb: " + ucb);
                if (ucb > maxUCB) {
                    maxUCB = ucb;
                    maxPosition = position;
                }
            }
            int win = playout(maxPosition.x, maxPosition.y, board);
            PlayoutResult updatingResult = counter.get(maxPosition);
            updatingResult.playoutCount += 1;
            updatingResult.winCount += win;
        }
        System.out.println(counter);
        Position result = null;
        double maxRate = -1;
        for (Map.Entry<Position, PlayoutResult> entry : counter.entrySet()) {
            Position position = entry.getKey();
            PlayoutResult playoutResult = entry.getValue();
            double winRate = (double) playoutResult.winCount / playoutResult.playoutCount;
            if (maxRate < winRate) {
                maxRate = winRate;
                result = position;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("play duration: " + (end - start));
        // 最終的な手を返す
        return result;
    }

    private int playout(int x, int y, Board board) {
        // 1手進める
        Board nextBoard = board.clone();
        nextBoard.put(x, y, turn.stone());

        // ランダムに打ち合うプレーヤーを準備
        Player blackPlayer = new RandomPlayer(Turn.BLACK);
        Player whitePlayer = new RandomPlayer(Turn.WHITE);

        // 1回だけ戦わせる
        Game game = new Game();
        Winner winner = game.play(blackPlayer, whitePlayer, nextBoard.clone(), turn.flip(), false);
        if (turn == Turn.BLACK && winner == Winner.BLACK || turn == Turn.WHITE && winner == Winner.WHITE) {
            return 1;
        } else {
            return 0;
        }
    }

    private static class PlayoutResult {
        private int playoutCount;
        private int winCount;

        public PlayoutResult(int playoutCount, int winCount) {
            this.playoutCount = playoutCount;
            this.winCount = winCount;
        }

        @Override
        public String toString() {
            return "playout: " + playoutCount + ", win: " + winCount;
        }
    }

}
