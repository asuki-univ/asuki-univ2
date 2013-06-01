package wthor;

import java.util.HashMap;
import java.util.Map;

import board.Board;
import board.Stone;

public class WThorFeature {
    Map<Integer, Integer> features = new HashMap<>();

    public double calculateScore(WThorParams params) {
        double score = 0;
        for (Map.Entry<Integer, Integer> entry : features.entrySet())
            score += params.get(entry.getKey()) * entry.getValue();

        return score;
    }

    public int get(int index) {
        Integer v = features.get(index);
        return v != null ? v : 0;
    }

    public void collect(Board board) {
        addLength8(board);
        addDiags(board);
        addCorner33(board);
        addCorner25(board);
        addEdge2x(board);
    }

    private void add(int index) {
        if (features.containsKey(index)) {
            features.put(index, features.get(index) + 1);
        } else {
            features.put(index, 1);
        }
    }

    private void addLength8(Board board) {
        for (int x = 1; x <= Board.WIDTH; ++x) {
            Stone[] s = board.getVertical(x);
            add(index(s) + WThorParams.LENGTH8_OFFSET);
            add(reverseIndex(s) + WThorParams.LENGTH8_OFFSET);
        }
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            Stone[] s = board.getHorizontal(y);
            add(index(s) + WThorParams.LENGTH8_OFFSET);
            add(reverseIndex(s) + WThorParams.LENGTH8_OFFSET);
        }
    }

    private void addDiags(Board board) {
        for (int diagSize = 4; diagSize <= 7; ++diagSize) {
            Stone[][] diags = new Stone[4][diagSize];
            for (int i = 0; i < diagSize; ++i) {
                diags[0][i] = board.get(diagSize - i,     1 + i);
                diags[1][i] = board.get(9 - diagSize + i, 1 + i);
                diags[2][i] = board.get(diagSize - i,     8 - i);
                diags[3][i] = board.get(9 - diagSize + i, 8 - i);
            }

            // TODO: We should have better solution here.
            final int offset;
            switch (diagSize) {
            case 4:
                offset = WThorParams.DIAG4_OFFSET;
                break;
            case 5:
                offset = WThorParams.DIAG5_OFFSET;
                break;
            case 6:
                offset = WThorParams.DIAG6_OFFSET;
                break;
            case 7:
                offset = WThorParams.DIAG7_OFFSET;
                break;
            default:
                assert(false);
                throw new RuntimeException("Shouldn't happen");
            }

            for (int i = 0; i < 4; ++i) {
                add(index(diags[i]) + offset);
                add(reverseIndex(diags[i]) + offset);
            }
        }

        Stone[] blackLine = new Stone[8];
        Stone[] whiteLine = new Stone[8];
        for (int i = 0; i < 8; ++i) {
            blackLine[i] = board.get(8 - i, i + 1);
            whiteLine[i] = board.get(i + 1, i + 1);
        }

        add(index(blackLine) + WThorParams.DIAG8_OFFSET);
        add(reverseIndex(blackLine) + WThorParams.DIAG8_OFFSET);
        add(index(whiteLine) + WThorParams.DIAG8_OFFSET);
        add(reverseIndex(whiteLine) + WThorParams.DIAG8_OFFSET);
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
            add(index(corners[i]) + WThorParams.CORNER33_OFFSET);
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
            add(index(corners[i]) + WThorParams.CORNER25_OFFSET);
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
            add(index(ss[i]) + WThorParams.EDGE2X_OFFSET);
            add(reverseIndex(ss[i]) + WThorParams.EDGE2X_OFFSET);
        }
    }

    public static int index(Stone[] stones) {
        int result = 0;
        for (int i = 0; i < stones.length; ++i)
            result = result * 3 + stones[i].ordinal();

        return result;
    }

    public static int reverseIndex(Stone[] stones) {
        int result = 0;
        for (int i = stones.length - 1; i >= 0; --i)
            result = result * 3 + stones[i].ordinal();

        return result;
    }
}