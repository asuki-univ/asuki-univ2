package ai.eval;

import board.Board;
import board.Stone;

public interface Evaluation extends Cloneable {
    // コピーを返す。
    public Evaluation clone();
    
    // (x, y) に stone を置く直前に呼ばれる。この手を evaluation に追加したい場合に
    // このコールバックで処理すると良い。
    public void willPut(Board board, int x, int y, Stone stone);
    
    // 評価値を返す。現在は turn の順番である。
    public int score(Board board, Stone stone);
}
