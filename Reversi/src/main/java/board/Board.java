package board;

import java.util.ArrayList;
import java.util.List;

public class Board extends BoardBase implements Cloneable {
    public static final int[] DX = new int[]{  0 , 1, 1, 1, 0, -1, -1, -1 };
    public static final int[] DY = new int[]{ -1, -1, 0, 1, 1,  1,  0, -1 };

    public Board() {
    }

    @Override
    public Board clone() {
        Board board = new Board();

        for (int y = 1; y <= HEIGHT; ++y) {
            for (int x = 1; x <= WIDTH; ++x) {
                board.board[y][x] = this.board[y][x];
            }
        }

        return board;
    }

    public void put(int x, int y, Stone stone) {
        assert(isPuttable(x, y, stone));

        board[y][x] = stone;
        for (int i = 0; i < 8; ++i) {
            int count = countFlippable(x, y, stone, DX[i], DY[i]);
            for (int j = 1; j <= count; ++j) {
                board[y + DY[i] * j][x + DX[i] * j] = stone;
            }
        }
    }

    public int countFlippable(int x, int y, Stone stone, int dx, int dy) {
        int count = 0;
        int yy = y + dy, xx = x + dx;
        Stone opponent = stone.flip();
        while (board[yy][xx] == opponent) {
            ++count;
            yy += dy;
            xx += dx;
        }

        if (board[yy][xx] == stone)
            return count;

        return 0;
    }

    // --------------------------------------------------

    public boolean isPuttable(int x, int y, Stone stone) {
        if (x < 1 || WIDTH < x || y < 1 || HEIGHT < y)
            return false;
        if (board[y][x] != Stone.EMPTY)
            return false;

        for (int i = 0; i < 8; ++i) {
            if (countFlippable(x, y, stone, DX[i], DY[i]) > 0)
                return true;
        }

        return false;
    }

    public boolean isPuttableSomewhere(Stone stone) {
        for (int y = 1; y <= HEIGHT; ++y) {
            for (int x = 1; x <= WIDTH; ++x) {
                if (isPuttable(x, y, stone))
                    return true;
            }
        }

        return false;
    }

    // --------------------------------------------------

    public List<Position> findPuttableHands(Stone stone) {
        List<Position> ps = new ArrayList<Position>();
        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (isPuttable(x, y, stone))
                    ps.add(new Position(x, y));
            }
        }

        return ps;
    }

    // --------------------------------------------------

    public int countStones() {
        int count = 0;
        for (int y = 1; y <= HEIGHT; ++y)
            for (int x = 1; x <= WIDTH; ++x)
                if (board[y][x] != Stone.EMPTY)
                    ++count;

        return count;
    }

    public int countStone(Stone stone) {
        int count = 0;
        for (int y = 1; y <= HEIGHT; ++y)
            for (int x = 1; x <= WIDTH; ++x)
                if (board[y][x] == stone)
                    ++count;

        return count;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board))
            return false;

        Board lhs = this;
        Board rhs = (Board) obj;

        for (int y = 0; y < MAP_HEIGHT; ++y) {
            for (int x = 0; x < MAP_WIDTH; ++x) {
                if (lhs.board[y][x] != rhs.board[y][x])
                    return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int y = 0; y < MAP_HEIGHT; ++y) {
            for (int x = 0; x < MAP_WIDTH; ++x) {
                hash += 37 * board[y][x].hashCode();
            }
        }
        return hash;
    }

    // ----------

    public Stone[] getHorizontal(int y) {
        Stone[] stones = new Stone[WIDTH];
        for (int i = 1; i <= WIDTH; ++i)
            stones[i - 1] = get(i, y);

        return stones;
    }

    public Stone[] getVertical(int x) {
        Stone[] stones = new Stone[HEIGHT];
        for (int i = 1; i <= HEIGHT; ++i)
            stones[i - 1] = get(x, i);

        return stones;
    }

}
