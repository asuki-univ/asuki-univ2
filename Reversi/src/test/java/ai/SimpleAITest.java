package ai;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import player.Player;
import player.ai.simple.SimpleAIPlayer;

import board.Board;
import board.BoardParser;
import board.Position;
import board.Turn;

public class SimpleAITest {

    @Test
    public void testEval1() {
        Board board = new BoardParser().parse(
                "........" +
                "........" +
                "........" +
                "...WB..." +
                "...BW..." +
                "........" +
                "........" +
                "........");

        Player ai = new SimpleAIPlayer(Turn.BLACK);
        Position p = ai.play(board);

        assertThat(p, equalTo(new Position(4, 3)));
    }
}
