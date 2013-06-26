package player.ai.eval;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import board.Board;
import board.BoardParser;
import board.Stone;


public class FixedStonesTest {
    @Test
    public void testBoard1() {
        Board board = new BoardParser().parse(
                "BBBBBBBB" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "BBBBBBBB");

        assertThat(FixedStones.getNumFixedStones(board, Stone.BLACK), is(16));
        assertThat(FixedStones.getNumFixedStones(board, Stone.WHITE), is(12));

    }

    @Test
    public void testBoard2() {
        Board board = new BoardParser().parse(
                "WBBBBBBB" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "W......W" +
                "BBBBBBBW");

        assertThat(FixedStones.getNumFixedStones(board, Stone.BLACK), is(14));
        assertThat(FixedStones.getNumFixedStones(board, Stone.WHITE), is(14));
    }

    @Test
    public void testBoard3() {
        Board board = new BoardParser().parse(
                "BBB..BBB" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "WW.BB..W");

        assertThat(FixedStones.getNumFixedStones(board, Stone.BLACK), is(6));
        assertThat(FixedStones.getNumFixedStones(board, Stone.WHITE), is(3));
    }

    @Test
    public void testBoard4() {
        Board board = new BoardParser().parse(
                "BWB..BWB" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........");

        assertThat(FixedStones.getNumFixedStones(board, Stone.BLACK), is(2));
        assertThat(FixedStones.getNumFixedStones(board, Stone.WHITE), is(1));
    }

    @Test
    public void testBoard5() {
        Board board = new BoardParser().parse(
                "W..WWWBW" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........" +
                "........");

        assertThat(FixedStones.getNumFixedStones(board, Stone.BLACK), is(0));
        assertThat(FixedStones.getNumFixedStones(board, Stone.WHITE), is(2));
    }
}
