package wthor;

import java.util.List;
import java.util.Map.Entry;

import board.Board;
import board.Turn;

public class WThorLearner {
    private List<MatchData> matchData;
    private int N;
    private int[] featureOccurence;             // 各特徴が現れた回数

    private WThorParams currentWThorParams;     // 現在の重みベクトル
    private WThorParams currentWThorDiff;       // 現在の二乗誤差の和
    private double currentFunctionValue;        // 目的関数の値
    private double currentBeta;                 // 現在の beta 値

    public WThorLearner(List<MatchData> matchData, WThorParams initialWThorParams) {
        this.matchData = matchData;
        this.N = matchData.size() * 40;

        currentWThorParams = initialWThorParams;
        currentWThorDiff = new WThorParams();
        currentBeta = 0.00002;

        // 目的関数の値を計算
        currentFunctionValue = 0;
        for (MatchData data : matchData)
            currentFunctionValue += calculateDiff(currentWThorParams, currentWThorDiff, data) / N;
        currentFunctionValue += currentWThorParams.getSquareSumLambda();

        // 特徴量の出現数を数えておく
        featureOccurence = countFeatureOccurence();
    }

    private int[] countFeatureOccurence() {
        int[] featureOccurrence = new int[WThorParams.ALL_PARAMS_SIZE];
        WThorFeature feature = new WThorFeature();
        // 各特徴量の出現数を数えておく。
        for (MatchData data : matchData)
            calculateOccurence(feature, data);

        // 出現数が 50 以下のものは、50 とする。
        for (int i = 0; i < WThorParams.ALL_PARAMS_SIZE; ++i) {
            featureOccurence[i] = feature.get(i);
            if (featureOccurence[i] < 50)
                featureOccurence[i] = 50;
        }

        return featureOccurrence;
    }

    // times回最急降下法を適用する
    public void run(int times) {
        for (int i = 0; i < times; ++i) {
            System.out.println("Iteration : " + i + " : beta = " + currentBeta);
            step();
        }
    }

    private void step() {
        // 新しい重みベクトルを作成し、その値で目的関数の値を計算s
        WThorParams nextWThorParams = makeWThorParamByAdding(currentWThorParams, currentWThorDiff);
        WThorParams nextWThorDiff = new WThorParams();
        double nextFunctionValue = 0;
        for (MatchData data : matchData)
            nextFunctionValue += calculateDiff(nextWThorParams, nextWThorDiff, data) / N;
        nextFunctionValue += nextWThorParams.getSquareSumLambda();

        if (nextFunctionValue < currentFunctionValue) {
            // より良い方向にアップデートされたので、これを使う。beta も少し大きくしてみる。
            currentWThorParams = nextWThorParams;
            currentWThorDiff = nextWThorDiff;
            currentFunctionValue = nextFunctionValue;
            currentBeta *= 1.1;
        } else {
            // アップデートに失敗した。より小さな beta で試す。
            currentBeta *= 0.9;
        }
    }

    private WThorParams makeWThorParamByAdding(WThorParams params, WThorParams diff) {
        WThorParams result = new WThorParams();
        for (int i = 0; i < WThorParams.ALL_PARAMS_SIZE; ++i) {
            double alpha = currentBeta / featureOccurence[i];
            double value = params.get(i) + 2 * alpha * diff.get(i) - 2 * params.get(i) * alpha * WThorParams.LAMBDA;
            result.set(i, value);
        }

        return result;
    }

    // 現在の重みベクトル params,
    private static double calculateDiff(WThorParams params, WThorParams diff, MatchData matchData) {
        // matchData は、４０手目までを用いる。
        Board board = new Board();
        board.setup();

        Turn currentTurn = Turn.BLACK;

        double sumDiff2 = 0;

        for (int i = 0; i < 40; ++i) {
            if (matchData.hands[i] == 0)
                break;

            int x = matchData.hands[i] % 10;
            int y = matchData.hands[i] / 10;
            assert (1 <= x && x <= 8 && 1 <= y && y <= 8);

            // もしかすると、棋譜にはパスが含まれている可能性があるので、パスを処理しておく。
            if (!board.isPuttable(x, y, currentTurn.stone())) {
                currentTurn = currentTurn.flip();
            }

            board.put(x, y, currentTurn.stone());
            if (currentTurn.equals(Turn.BLACK)) {
                currentTurn = currentTurn.flip();
                continue;
            }

            WThorFeature feature = new WThorFeature();
            feature.collect(board);

            // ここまでで、特徴量が集まった。誤差を足していく。
            double estimatedScore = feature.calculateScore(params);
            double e = matchData.theoreticalScore - estimatedScore;
            sumDiff2 += e * e;
            for (Entry<Integer, Integer> entry : feature.features.entrySet())
                diff.add(entry.getKey(), e * entry.getValue());

            currentTurn = currentTurn.flip();
        }

        return sumDiff2;
    }

    private void calculateOccurence(WThorFeature feature, MatchData matchData) {
        // matchData は、４０手目までを用いる。
        Board board = new Board();
        Turn currentTurn = Turn.BLACK;

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
            feature.collect(board);

            currentTurn = currentTurn.flip();
        }
    }

    public WThorParams getParams() {
        return currentWThorParams;
    }
}
