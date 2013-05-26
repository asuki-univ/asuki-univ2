package board;

public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 10;
    private static final int[] DX = new int[]{  0 , 1, 1, 1, 0, -1, -1, -1 };
    private static final int[] DY = new int[]{ -1, -1, 0, 1, 1,  1,  0, -1 };

    private Stone[][] board = new Stone[MAP_HEIGHT][MAP_WIDTH];

    public Board() {
        for (int y = 1; y <= HEIGHT; ++y) {
            for (int x = 1; x <= WIDTH; ++x) {
                board[y][x] = Stone.EMPTY;
            }
        }

        for (int y = 0; y < MAP_HEIGHT; ++y) {
            board[y][0] = Stone.WALL;
            board[y][MAP_WIDTH-1] = Stone.WALL;
        }

        for (int x = 0; x < MAP_WIDTH; ++x) {
            board[0][x] = Stone.WALL;
            board[MAP_HEIGHT-1][x] = Stone.WALL;
        }
    }

    // Copy Constructor (Maybe we should have clone() instead.)
    public Board(Board board) {
        for (int y = 0; y < MAP_HEIGHT; ++y)
            for (int x = 0; x < MAP_WIDTH; ++x)
                this.board[y][x] = board.board[y][x];
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

    public void setup() {
        for (int y = 1; y <= HEIGHT; ++y)
            for (int x = 1; x <= WIDTH; ++x)
                board[y][x] = Stone.EMPTY;

        board[4][4] = board[5][5] = Stone.WHITE;
        board[4][5] = board[5][4] = Stone.BLACK;
    }

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

    private int countFlippable(int x, int y, Stone stone, int dx, int dy) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(' ');
        for (int x = 1; x <= WIDTH; ++x) {
            builder.append((char)('0' + x));
        }
        builder.append('\n');
        for (int y = 1; y <= HEIGHT; ++y) {
            builder.append((char)('0' + y));
            for (int x = 1; x <= WIDTH; ++x) {
                switch (board[y][x]) {
                case EMPTY:
                    builder.append('.'); break;
                case BLACK:
                    builder.append('B'); break;
                case WHITE:
                    builder.append('W'); break;
                case WALL:
                    builder.append('*'); break;
                }
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    public void show() {
        System.out.println(toString());
    }

    // ----------

    public Stone get(Position p) {
        return board[p.y][p.x];
    }

    public Stone get(int x, int y) {
        return board[y][x];
    }

    public void set(int x, int y, Stone stone) {
        board[y][x] = stone;
    }

    public Stone[] getHorizontal(int y) {
        Stone[] stones = new Stone[WIDTH];
        for (int i = 0; i < WIDTH; ++i) {
            stones[i] = get(i + 1, y);
        }

        return stones;
    }

    public Stone[] getVertical(int x) {
        Stone[] stones = new Stone[HEIGHT];
        for (int i = 0; i < HEIGHT; ++i) {
            stones[i] = get(x, i + 1);
        }

        return stones;
    }

}
