package ai;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import player.Player;
import player.ai.AlphaBetaEvaluationSimpleAI;
import player.ai.AlphaBetaSimpleAI;
import player.ai.MinMaxSimpleAI;
import player.ai.NegaMaxSimpleAI;
import player.ai.NegaScoutEvaluationSimpleAI;
import player.ai.TranpositionEvaluationSimpleAI;
import board.Board;
import board.BoardParser;
import board.Position;
import board.Turn;

public class AITest {
    @Test
    public void testEvalEquality() {
        Board board = new BoardParser().parse(
                "........" +
                "..WBB..." +
                "..WWBW.." +
                "..WBBB.." +
                "..WBB..." +
                "...B...." +
                "........" +
                "........");

        // MinMax, NegaMax, AlphaBeta should return the same evaluation.
        // Move Ordering might return the same evaluation hand, but it might be the different one.
        Player minmax = new MinMaxSimpleAI(Turn.WHITE, 5);
        Player negamax = new NegaMaxSimpleAI(Turn.WHITE, 5);
        Player alphabeta = new AlphaBetaSimpleAI(Turn.WHITE, 5);
        Player alphabeta2 = new AlphaBetaEvaluationSimpleAI(Turn.WHITE, 5);
        Player negascout = new NegaScoutEvaluationSimpleAI(Turn.WHITE, 5);
        Player tranposition = new TranpositionEvaluationSimpleAI(Turn.WHITE, 5);

        Position p1 = minmax.play(board);
        Position p2 = negamax.play(board);
        Position p3 = alphabeta.play(board);
        Position p4 = alphabeta2.play(board);
        Position p5 = negascout.play(board);
        Position p6 = tranposition.play(board);

        assertThat(p1, equalTo(p2));
        assertThat(p1, equalTo(p3));
        assertThat(p1, equalTo(p4));
        assertThat(p1, equalTo(p5));
        assertThat(p1, equalTo(p6));
    }
}
