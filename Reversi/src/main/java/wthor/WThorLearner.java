package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import board.Board;
import board.Turn;

public class WThorLearner {
    public static void main(String[] args) throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream(new File("wth/WTH_2013.wtb")));
        List<MatchData> results = WThorParser.parse(in);

        System.out.println("results.size() = " + results.size());

        WThorParams currentDiff = new WThorParams();
        WThorParams currentWThorParams = new WThorParams();
        double currentSumDiff2 = 0;
        for (MatchData matchData : results)
            currentSumDiff2 += learn(currentWThorParams, currentDiff, matchData);

        double beta = 0.0025;
        for (int i = 0; i < 1000; ++i) {
            System.out.println("Iteration : " + i + " : beta = " + beta);

            // diff を使って、param をアップデート。
            double alpha = 2 * beta / (40 * results.size());
            WThorParams nextWThorParams = currentWThorParams.makeWThorParamByAdding(currentDiff, alpha);

            WThorParams nextDiff = new WThorParams();
            double nextSumDiff2 = 0;
            for (MatchData matchData : results)
                nextSumDiff2 += learn(nextWThorParams, nextDiff, matchData);

            System.out.println("Σdiff^2 (curr) = " + currentSumDiff2);
            System.out.println("Σdiff^2 (next) = " + nextSumDiff2);

            if (nextSumDiff2 < currentSumDiff2) {
                // より良い方向にアップデートされたので、これを使おう。beta も少し大きくしてみる。
                currentWThorParams = nextWThorParams;
                currentDiff = nextDiff;
                currentSumDiff2 = nextSumDiff2;
                beta *= 1.1;
                continue;
            } else {
                // アップデートに失敗した。より小さな beta で試すべき。
                beta *= 0.9;
            }
        }

        currentWThorParams.save("params.dat");
    }

    private static double learn(WThorParams params, WThorParams diff, MatchData matchData) {
        // matchData は、４０手目までを用いる。
        Board board = new Board();
        Turn currentTurn = Turn.BLACK;

        double sumDiff2 = 0;

        for (int i = 0; i < 40; ++i) {
            if (matchData.hands[i] == 0)
                break;

            int x = matchData.hands[i] % 10;
            int y = matchData.hands[i] / 10;

            assert (1 <= x && x <= 8);
            assert (1 <= y && y <= 8);

            // もしかすると、棋譜にはパスが含まれている可能性があるので、パスを処理しておく。
            if (!board.isPuttable(x, y, currentTurn.stone()))
                currentTurn = currentTurn.flip();

            board.put(x, y, currentTurn.stone());

            WThorFeature feature = new WThorFeature();
            feature.collect(board);

            // ここまでで、特徴量が集まった。e を足していく。
            double estimatedScore = feature.calculateScore(params);
            double e = matchData.theoreticalScore - estimatedScore;
            sumDiff2 += e * e;
            for (Integer j : feature.featureIndices)
                diff.add(j, e);

            currentTurn = currentTurn.flip();
        }

        return sumDiff2;
    }
}
