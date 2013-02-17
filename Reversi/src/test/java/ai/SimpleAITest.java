package ai;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import board.Board;
import board.Position;
import board.Turn;

public class SimpleAITest {

    @Test
    public void testEval1() {
        Board board = new Board(
                "........" + 
                "........" + 
                "........" + 
                "...WB..." + 
                "...BW..." + 
                "........" + 
                "........" + 
                "........");
        
        AI ai = new SimpleAI(Turn.BLACK);
        EvalResult r = ai.eval(board);
        
        assertThat(r.getPosition(), equalTo(new Position(4, 3)));
        
    }
}
