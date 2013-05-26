package board;

public class BoardParser {
    public Board parse(String str) {
        Board board = new Board();

        for (int i = 0; i < 64; ++i) {
            int x = i % 8 + 1;
            int y = i / 8 + 1;

            if (str.charAt(i) == 'B')
                board.set(x, y, Stone.BLACK);
            else if (str.charAt(i) == 'W')
                board.set(x, y, Stone.WHITE);
        }

        return board;
    }
}
