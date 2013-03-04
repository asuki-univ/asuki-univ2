package ai;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import player.Player;
import board.Board;
import board.Position;
import board.Turn;

public class AITest {
    @Test
    public void testEvalEquality() {
        Board board = new Board(
                "........" + 
                "..WBB..." + 
                "..WWBW.." + 
                "..WBBB.." + 
                "..WBB..." + 
                "...B...." + 
                "........" + 
                "........");

        // MinMax, NegaMax, AlphaBeta should return the same evaluation. 
        Player minmax = new MinMaxSimpleAI(Turn.WHITE, 5);
        Player negamax = new NegaMaxSimpleAI(Turn.WHITE, 5);
        Player alphabeta = new AlphaBetaSimpleAI(Turn.WHITE, 5);

        Position p1 = minmax.play(board);
        Position p2 = negamax.play(board);
        Position p3 = alphabeta.play(board);

        assertThat(p1, equalTo(p2));
        assertThat(p1, equalTo(p3));
    }
}
