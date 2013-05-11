package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;

import board.Board;
import board.Stone;
import board.Turn;

class WThorFeature {
    ArrayList<Integer> length8 = new ArrayList<Integer>();
    ArrayList<Integer> diag4 = new ArrayList<Integer>();
    ArrayList<Integer> diag5 = new ArrayList<Integer>();
    ArrayList<Integer> diag6 = new ArrayList<Integer>();
    ArrayList<Integer> diag7 = new ArrayList<Integer>();
    ArrayList<Integer> diag8 = new ArrayList<Integer>();
    ArrayList<Integer> corner33 = new ArrayList<Integer>();
    ArrayList<Integer> corner25 = new ArrayList<Integer>();
    ArrayList<Integer> edge2x = new ArrayList<Integer>();

    public double calculateScore(WThorParams params) {
        double score = 0;

        for (Integer i : length8)
            score += params.length8[i];
        for (Integer i : diag4)
            score += params.diag4[i];
        for (Integer i : diag5)
            score += params.diag5[i];
        for (Integer i : diag6)
            score += params.diag6[i];
        for (Integer i : diag7)
            score += params.diag7[i];
        for (Integer i : diag8)
            score += params.diag8[i];
        for (Integer i : corner33)
            score += params.corner33[i];
        for (Integer i : corner25)
            score += params.corner25[i];
        for (Integer i : edge2x)
            score += params.edge2x[i];

        return score;
    }

    // double estimatedScore = params.calcualteScore(feature);


    public void add(Board board) {
        addLength8(board);
        addDiags(board);
        addCorner33(board);
        addCorner25(board);
        addEdge2x(board);
    }

    private void addLength8(Board board) {
        for (int x = 1; x <= Board.WIDTH; ++x) {
            Stone[] s = board.getVertical(x);
            length8.add(index(s));
            length8.add(reverseIndex(s));
        }
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            Stone[] s = board.getHorizontal(y);
            length8.add(index(s));
            length8.add(reverseIndex(s));
        }
    }

    private void addDiags(Board board) {
        for (int diagSize = 4; diagSize <= 7; ++diagSize) {
            Stone[][] diags = new Stone[4][diagSize];
            for (int i = 0; i < diagSize; ++i) {
                diags[0][i] = board.get(diagSize - i,     1 + i);
                diags[1][i] = board.get(8 - diagSize + i, 1 + i);
                diags[2][i] = board.get(diagSize - i,     8 - i);
                diags[3][i] = board.get(8 - diagSize + i, 8 - i);
            }

            // TODO: We should have better solution here.
            ArrayList<Integer> diag;
            switch (diagSize) {
            case 4:
                diag = diag4; break;
            case 5:
                diag = diag5; break;
            case 6:
                diag = diag6; break;
            case 7:
                diag = diag7; break;
            default:
                assert(false);
                throw new RuntimeException("Shouldn't happen");
            }

            for (int i = 0; i < 4; ++i) {
                diag.add(index(diags[i]));
                diag.add(reverseIndex(diags[i]));
            }
        }

        Stone[] blackLine = new Stone[8];
        Stone[] whiteLine = new Stone[8];
        for (int i = 0; i < 8; ++i) {
            blackLine[i] = board.get(8 - i, i + 1);
            whiteLine[i] = board.get(i + 1, i + 1);
        }

        diag8.add(index(blackLine));
        diag8.add(reverseIndex(blackLine));
        diag8.add(index(whiteLine));
        diag8.add(reverseIndex(whiteLine));
    }

    private void addCorner33(Board board) {
        Stone[][] corners = new Stone[8][9];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                corners[0][i * 3 + j] = board.get(i + 1, j + 1);
                corners[1][i * 3 + j] = board.get(i + 1, 8 - j);
                corners[2][i * 3 + j] = board.get(8 - i, j + 1);
                corners[3][i * 3 + j] = board.get(8 - i, 8 - j);
                corners[4][i * 3 + j] = board.get(j + 1, i + 1);
                corners[5][i * 3 + j] = board.get(8 - j, i + 1);
                corners[6][i * 3 + j] = board.get(j + 1, 8 - i);
                corners[7][i * 3 + j] = board.get(8 - j, 8 - i);
            }
        }

        for (int i = 0; i < 8; ++i)
            corner33.add(index(corners[i]));
    }

    private void addCorner25(Board board) {
        Stone[][] corners = new Stone[8][10];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 5; ++j) {
                // upper left
                corners[0][i * 5 + j] = board.get(i + 1, j + 1);
                corners[1][i * 5 + j] = board.get(i + 1, 8 - j);
                corners[2][i * 5 + j] = board.get(8 - i, j + 1);
                corners[3][i * 5 + j] = board.get(8 - i, 8 - j);

                corners[4][i * 5 + j] = board.get(j + 1, i + 1);
                corners[5][i * 5 + j] = board.get(8 - j, i + 1);
                corners[6][i * 5 + j] = board.get(j + 1, 8 - i);
                corners[7][i * 5 + j] = board.get(8 - j, 8 - i);
            }
        }

        for (int i = 0; i < 8; ++i)
            corner25.add(index(corners[i]));
    }

    // 1 2 3 4 7 8 9 10
    // x 5 x x x x 6 x
    private void addEdge2x(Board board) {
        Stone[][] ss = new Stone[4][];
        ss[0] = new Stone[] {
                board.get(1, 1), board.get(2, 1), board.get(3, 1), board.get(4, 1), board.get(2, 2),
                board.get(7, 2), board.get(5, 1), board.get(6, 1), board.get(7, 1), board.get(8, 1),
        };
        ss[1] = new Stone[] {
                board.get(1, 8), board.get(2, 8), board.get(3, 8), board.get(4, 8), board.get(2, 7),
                board.get(7, 7), board.get(5, 8), board.get(6, 8), board.get(7, 8), board.get(8, 8),
        };
        ss[2] = new Stone[] {
                board.get(1, 1), board.get(1, 2), board.get(1, 3), board.get(1, 4), board.get(2, 2),
                board.get(2, 7), board.get(1, 5), board.get(1, 6), board.get(1, 7), board.get(1, 8),
        };
        ss[3] = new Stone[] {
                board.get(8, 1), board.get(8, 2), board.get(8, 3), board.get(8, 4), board.get(2, 2),
                board.get(2, 7), board.get(8, 5), board.get(8, 6), board.get(8, 7), board.get(8, 8),
        };

        for (int i = 0; i < 4; ++i) {
            edge2x.add(index(ss[i]));
            edge2x.add(reverseIndex(ss[i]));
        }
    }

    private int index(Stone[] stones) {
        int result = 0;
        for (int i = 0; i < stones.length; ++i)
            result = result * 3 + stones[i].ordinal();

        return result;
    }

    private int reverseIndex(Stone[] stones) {
        int result = 0;
        for (int i = stones.length - 1; i >= 0; --i)
            result = result * 3 + stones[i].ordinal();

        return result;
    }
}

public class WThorLearner {
    public static void main(String[] args) throws Exception {
        WThorParams currentWTHorParams = new WThorParams();

        InputStream in = new BufferedInputStream(new FileInputStream(new File("wth/WTH_2013.wtb")));
        List<MatchData> results = WThorParser.parse(in);

        System.out.println("results.size() = " + results.size());

        for (int i = 0; i < 1000; ++i) {
            WThorParams diff = new WThorParams();

            double sumDiff2 = 0;
            for (MatchData matchData : results)
                sumDiff2 += learn(results.size() * 40, currentWTHorParams, diff, matchData);

            currentWTHorParams.add(diff);

            System.out.println("Done : " + i + " : diff2 = " + sumDiff2);
        }
    }

    private static double learn(int N, WThorParams params, WThorParams diff, MatchData matchData) {
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
            feature.add(board);

            // ここまでで、特徴量が集まった。
            // 実際の黒石数は matchData.theoreticalScore
            double estimatedScore = feature.calculateScore(params);
            double e = matchData.theoreticalScore - estimatedScore;
            sumDiff2 += e * e;
            double e2 = 0.002 * 2.0 * e / N;
            updateDiff(e2, diff, feature);

            currentTurn = currentTurn.flip();
        }

        return sumDiff2;
    }

    private static void updateDiff(double e, WThorParams diff, WThorFeature feature) {
        for (Integer i : feature.length8)
            diff.length8[i] += e;
        for (Integer i : feature.diag4)
            diff.diag4[i] += e;
        for (Integer i : feature.diag5)
            diff.diag5[i] += e;
        for (Integer i : feature.diag6)
            diff.diag6[i] += e;
        for (Integer i : feature.diag7)
            diff.diag7[i] += e;
        for (Integer i : feature.diag8)
            diff.diag8[i] += e;
        for (Integer i : feature.corner33)
            diff.corner33[i] += e;
        for (Integer i : feature.corner25)
            diff.corner25[i] += e;
        for (Integer i : feature.edge2x)
            diff.edge2x[i] += e;
    }
}
