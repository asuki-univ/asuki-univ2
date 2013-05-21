package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Turn;

public class WThorLearner2 extends WThorLearner {
    private WThorFeatureOccurence occurence;

    public WThorLearner2(List<MatchData> matchData, WThorParams loadedWThorParamsIfAny) {
        super(matchData, loadedWThorParamsIfAny);

        this.occurence = new WThorFeatureOccurence();
        // 各特徴量の出現数を数える。
        for (MatchData data : matchData) {
            WThorFeature feature = new WThorFeature();
            calculateOccurence(feature, data);

            for (Integer i : feature.featureIndices)
                occurence.incr(i);
        }

        // 出現数が 50 以下のものは、50 とする。
        this.occurence.ensureMinValue(50);
    }

    @Override
    protected WThorAlpha makeAlpha() {
        return new WThorAlpha() {
            @Override
            public double alpha(int index) {
                return currentBeta / occurence.numOccurence(index);
            }
        };
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

    public static void main(String[] args) throws Exception {
        String[] filenames = new String[] {
                "wth/WTH_2000.wtb", "wth/WTH_2001.wtb", "wth/WTH_2002.wtb", "wth/WTH_2003.wtb", "wth/WTH_2004.wtb",
                "wth/WTH_2005.wtb", "wth/WTH_2006.wtb", "wth/WTH_2007.wtb", "wth/WTH_2008.wtb", "wth/WTH_2009.wtb",
                "wth/WTH_2010.wtb", "wth/WTH_2011.wtb", "wth/WTH_2012.wtb",
        };

        List<MatchData> matchData = new ArrayList<MatchData>();
        for (String filename : filenames) {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(filename)));
            matchData.addAll(WThorParser.parse(in));
        }

        System.out.println("results.size() = " + matchData.size());

        WThorParams loadedParams = new File(WTHOR_PARAM_FILENAME).exists() ? new WThorParams(WTHOR_PARAM_FILENAME) : null;

        WThorLearner2 learner = new WThorLearner2(matchData, loadedParams);
        learner.run(100);
        learner.getParams().save(WTHOR_PARAM_FILENAME);
    }
}
