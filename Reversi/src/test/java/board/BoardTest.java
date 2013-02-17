package board;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BoardTest {

    @Test
    public void testPut1() {
        Board board = new Board(
                "W..W..W." +
                ".B.B.B.." +
                "..BBB..." +    
                "WBB.BBBW" +
                "..BBB..." +
                ".B.B.B.." +
                "W..B..W." +
                "...W....");

        board.put(4, 4, Stone.WHITE);

        Board after = new Board(
                "W..W..W." +
                ".W.W.W.." +
                "..WWW..." +
                "WWWWWWWW" +
                "..WWW..." +
                ".W.W.W.." +
                "W..W..W." +
                "...W....");

        assertThat(board, equalTo(after));
    }

    @Test
    public void testPut2() {
        Board board = new Board(
                "B..B..B." +
                ".B.B.B.." +
                "..BBB..." +
                "BBB.BBBW" +
                "..BBB..." +
                ".B.B.B.." +
                "B..B..B." +
                "...B....");

        board.put(4, 4, Stone.WHITE);

        Board after = new Board(
                "B..B..B." +
                ".B.B.B.." +
                "..BBB..." +
                "BBBWWWWW" +
                "..BBB..." +
                ".B.B.B.." +
                "B..B..B." +
                "...B....");

        assertThat(board, equalTo(after));
    }
}
