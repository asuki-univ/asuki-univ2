package board;

public class BoardBase {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 10;

    protected Stone[][] board = new Stone[MAP_HEIGHT][MAP_WIDTH];

    public BoardBase() {
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

    public Stone get(int x, int y) {
        return board[y][x];
    }

    public void set(int x, int y, Stone stone) {
        board[y][x] = stone;
    }

    public void setup() {
        for (int y = 1; y <= HEIGHT; ++y)
            for (int x = 1; x <= WIDTH; ++x)
                board[y][x] = Stone.EMPTY;

        board[4][4] = board[5][5] = Stone.WHITE;
        board[4][5] = board[5][4] = Stone.BLACK;
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
}
