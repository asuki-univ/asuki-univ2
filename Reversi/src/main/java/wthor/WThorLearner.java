package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import board.Board;
import board.Turn;

public class WThorLearner {
    protected static final String WTHOR_PARAM_FILENAME = "params.dat";

    protected List<MatchData> matchData;
    protected int N;

    protected WThorParams currentWThorParams;
    protected WThorParams currentWThorDiff;
    protected double currentFunctionValue;
    protected double currentBeta;

    public WThorLearner(List<MatchData> matchData, WThorParams loadedWThorParamsIfAny) {
        this.matchData = matchData;
        this.N = matchData.size() * 40;

        if (loadedWThorParamsIfAny != null)
            currentWThorParams = loadedWThorParamsIfAny;
        else
            currentWThorParams = new WThorParams();
        currentWThorDiff = new WThorParams();
        currentBeta = 0.00002;
        currentFunctionValue = 0;
        for (MatchData data : matchData)
            currentFunctionValue += calculateDiff(currentWThorParams, currentWThorDiff, data) / N;
        currentFunctionValue += currentWThorParams.getSquareSumLambda();
    }

    public WThorParams getParams() {
        return currentWThorParams;
    }

    public void run(int times) {
        for (int i = 0; i < times; ++i) {
            System.out.println("Iteration : " + i + " : beta = " + currentBeta);
            step();
        }
    }

    private void step() {
        WThorAlpha alpha = makeAlpha();

        WThorParams nextWThorParams = currentWThorParams.makeWThorParamByAdding(currentWThorDiff, alpha);
        WThorParams nextWThorDiff = new WThorParams();
        double nextFunctionValue = 0;
        for (MatchData data : matchData)
            nextFunctionValue += calculateDiff(nextWThorParams, nextWThorDiff, data) / N;
        nextFunctionValue += nextWThorParams.getSquareSumLambda();

        System.out.printf("(curr) f(w) : %.3f + %.3f = %.3f\n",
                currentFunctionValue - currentWThorParams.getSquareSumLambda(), currentWThorParams.getSquareSumLambda(), currentFunctionValue);
        System.out.printf("(next) f(w) : %.3f + %.3f = %.3f\n",
                nextFunctionValue - nextWThorParams.getSquareSumLambda(), nextWThorParams.getSquareSumLambda(), nextFunctionValue);

        if (nextFunctionValue < currentFunctionValue) {
            // より良い方向にアップデートされたので、これを使おう。beta も少し大きくしてみる。
            currentWThorParams = nextWThorParams;
            currentWThorDiff = nextWThorDiff;
            currentFunctionValue = nextFunctionValue;
            currentBeta *= 1.1;
        } else {
            // アップデートに失敗した。より小さな beta で試す。
            currentBeta *= 0.9;
        }
    }

    protected WThorAlpha makeAlpha() {
        return new WThorAlpha() {
            @Override
            public double alpha(int index) {
                return currentBeta / (40 * matchData.size());
            }
        };
    }

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

            assert (1 <= x && x <= 8);
            assert (1 <= y && y <= 8);

            // もしかすると、棋譜にはパスが含まれている可能性があるので、パスを処理しておく。
            if (!board.isPuttable(x, y, currentTurn.stone())) {
                //System.out.printf("!!! : x, y = %d, %d\n", x, y);
                //System.out.println(board.toString());
                currentTurn = currentTurn.flip();
            }

            board.put(x, y, currentTurn.stone());
//            if (currentTurn.equals(Turn.WHITE)) {
//                currentTurn = currentTurn.flip();
//                continue;
//            }

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

    public static void main(String[] args) throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream(new File("wth/WTH_2004.wtb")));
        List<MatchData> matchData = WThorParser.parse(in);
        System.out.println("results.size() = " + matchData.size());

        WThorParams loadedParams = new File(WTHOR_PARAM_FILENAME).exists() ? new WThorParams(WTHOR_PARAM_FILENAME) : null;

        WThorLearner learner = new WThorLearner(matchData, loadedParams);
        learner.run(100);
        learner.getParams().save(WTHOR_PARAM_FILENAME);
    }
}
