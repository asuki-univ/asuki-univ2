package player.ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public class AdvancedEvaluator implements Evaluator {
    private final Turn turn;
    private int currentOpenness;

    public AdvancedEvaluator(Turn turn) {
        this(turn, 0);
    }

    private AdvancedEvaluator(Turn turn, int currentOpenness) {
        this.turn = turn;
        this.currentOpenness = currentOpenness;
    }

    @Override
    public AdvancedEvaluator clone() {
        return new AdvancedEvaluator(this.turn, currentOpenness);
    }

    @Override
    public void willPut(Board board, int x, int y, Stone stone) {
        for (int i = 0; i < Board.DX.length; ++i) {
            int dx = Board.DX[i];
            int dy = Board.DY[i];
            int count = board.countFlippable(x, y, stone, dx, dy);
            for (int j = 1; j <= count; ++j) {
                if (turn.stone() == stone)
                    currentOpenness += countOpenness(board, x + dx * j, y + dy * j);
                else
                    currentOpenness -= countOpenness(board, x + dx * j, y + dy * j);
            }
        }
    }

    private int countOpenness(Board board, int x, int y) {
        int count = 0;
        for (int i = 0; i < Board.DX.length; ++i) {
            int dx = Board.DX[i];
            int dy = Board.DY[i];
            if (board.get(x + dx, y + dy) == Stone.EMPTY)
                ++count;
        }

        return count;
    }

    @Override
    public double score(Board board, Turn currentTurn) {
        int v = -currentOpenness * 3;
        v += FixedStones.getNumFixedStones(board, turn.stone()) * 20; // 自分の確定石は加点
        v -= FixedStones.getNumFixedStones(board, turn.flip().stone()) * 20;
        v += board.findPuttableHands(turn.stone()).size(); // 置ける箇所を加点
        v -= board.findPuttableHands(turn.flip().stone()).size(); //

        if (currentTurn == turn) {
            return v;
        } else {
            return -v;
        }
    }

}
