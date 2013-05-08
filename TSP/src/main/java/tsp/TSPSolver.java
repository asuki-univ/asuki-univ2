package tsp;

public interface TSPSolver {
    // 現在の状態集合の中で最もよいものを得る。１つしかなければそれを返す。
    public TSPState getBestState();

    // 計算を 1step 進める
    public abstract boolean step();
}
