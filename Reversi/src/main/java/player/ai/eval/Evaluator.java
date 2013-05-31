package player.ai.eval;

import board.Board;
import board.Stone;
import board.Turn;

public interface Evaluator extends Cloneable {
    // コピーを返す。
    public Evaluator clone();

    // (x, y) に stone を置く直前に呼ばれる。この手を evaluation に追加したい場合に
    // このコールバックで処理すると良い。
    public void willPut(Board board, int x, int y, Stone stone);

    // 評価値を返す。現在は stone の順番である。
    public double score(Board board, Turn currentTurn);
}
