package player.ai.eval;

import board.Board;
import board.Stone;

public class FixedStones {
    // 黒石の確定石を保持
    private static final int[] fixedStone = calculate();

    public static int getNumFixedStones(Board board, Stone stone) {
        assert (stone == Stone.BLACK || stone == Stone.WHITE);

        Stone[] upper = new Stone[8];
        Stone[] lower = new Stone[8];
        Stone[] left = new Stone[8];
        Stone[] right = new Stone[8];

        if (Stone.BLACK.equals(stone)) {
            for (int i = 1; i <= 8; ++i) {
                upper[i-1] = board.get(i, 1);
                lower[i-1] = board.get(i, Board.HEIGHT);
                left[i-1] = board.get(1, i);
                right[i-1] = board.get(Board.WIDTH, i);
            }
        } else {
            for (int i = 1; i <= 8; ++i) {
                upper[i-1] = board.get(i, 1).flip();
                lower[i-1] = board.get(i, Board.HEIGHT).flip();
                left[i-1] = board.get(1, i).flip();
                right[i-1] = board.get(Board.WIDTH, i).flip();
            }
        }

        int count = 0;
        count += fixedStone[toInt(upper)];
        count += fixedStone[toInt(lower)];
        count += fixedStone[toInt(left)];
        count += fixedStone[toInt(right)];

        // 角は重複して数えているので、除去する
        if (board.get(1, 1) == stone)
            --count;
        if (board.get(1, Board.HEIGHT) == stone)
            --count;
        if (board.get(Board.WIDTH, 1) == stone)
            --count;
        if (board.get(Board.WIDTH, Board.HEIGHT) == stone)
            --count;

        return count;
    }

    private static int[] calculate() {
        int[] result = new int[6561]; // (3 ** 8) = 6561
        for (int i = 0; i < 6561; ++i)
            result[i] = -1;

        for (int i = 0; i < 6561; ++i)
            result[i] = calculateIter(i, result);

        return result;
    }

    private static int calculateIter(int ith, int[] visited) {
        if (visited[ith] >= 0)
            return visited[ith];

        // 盤の１行目を利用して探索する。
        Board board = fromInt(ith);

        boolean foundEmpty = false;
        int result = 100;
        for (int x = 1; x <= Board.WIDTH; ++x) {
            if (!Stone.EMPTY.equals(board.get(x, 1)))
                continue;

            foundEmpty = true;

            // 黒石をおいた場合と白石を置いた場合のうち、少ないほうが採用される。
            Board b = board.clone();
            b.put(x, 1, Stone.BLACK);
            result = Math.min(result, calculateIter(toInt(b.getHorizontal(1)), visited));

            b = board.clone();
            b.put(x, 1, Stone.WHITE);
            result = Math.min(result, calculateIter(toInt(b.getHorizontal(1)), visited));
        }

        if (foundEmpty) {
            visited[ith] = result;
            return result;
        }

        int numBlackStone = 0;
        for (int i = 1; i <= Board.WIDTH; ++i) {
            if (Stone.BLACK.equals(board.get(i, 1)))
                ++numBlackStone;
        }

        visited[ith] = numBlackStone;
        return numBlackStone;
    }

    static Board fromInt(int idx) {
        Board board = new Board();
        for (int x = 8; x >= 1; --x) {
            board.set(x, 1, Stone.values()[idx % 3]);
            idx /= 3;
        }

        return board;
    }

    static int toInt(Stone[] stones) {
        int result = 0;
        for (int i = 0; i < stones.length; ++i)
            result = result * 3 + stones[i].ordinal();
        return result;
    }
}
