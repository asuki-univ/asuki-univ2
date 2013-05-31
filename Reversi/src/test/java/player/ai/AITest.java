package player.ai;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import player.Player;
import player.ai.impl.AlphaBetaBoardScoreAIPlayer;
import player.ai.impl.NegaMaxBoardScoreAIPlayer;
import player.ai.impl.NegaScoutBoardScoreAIPlayer;
import player.ai.impl.TranpositionBoardScoreAIPlayer;
import player.ai.simple.MinMaxSimpleAIPlayer;
import player.ai.simple.NegaMaxSimpleAIPlayer;
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
        Player minmax = new MinMaxSimpleAIPlayer(Turn.WHITE, 5);
        Player negamax = new NegaMaxSimpleAIPlayer(Turn.WHITE, 5);

        Player negamax2 = new NegaMaxBoardScoreAIPlayer(Turn.WHITE, 5);
        Player alphabeta = new AlphaBetaBoardScoreAIPlayer(Turn.WHITE, 5);

        Player negascout = new NegaScoutBoardScoreAIPlayer(Turn.WHITE, 5);
        Player tranposition = new TranpositionEvaluationSimpleAI(Turn.WHITE, 5);

        Position p1 = minmax.play(board);
        Position p2 = negamax.play(board);
        Position p3 = negamax2.play(board);
        Position p4 = alphabeta.play(board);
        Position p5 = negascout.play(board);
        Position p6 = tranposition.play(board);

        assertThat(p1, equalTo(p2));
        assertThat(p1, equalTo(p3));
        assertThat(p1, equalTo(p4));
        assertThat(p1, equalTo(p5));
        assertThat(p1, equalTo(p6));
    }
}
