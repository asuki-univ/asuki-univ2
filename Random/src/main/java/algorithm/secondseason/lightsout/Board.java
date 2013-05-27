package algorithm.secondseason.lightsout;

import java.io.PrintStream;

public class Board {

    private LightState[][] board;
    
    public Board(int m, int n) {
        this.board = new LightState[m][n];
    }
    
    public void println() {
        println(System.out);
    }
    
    public void println(PrintStream out) {
        out.println(toString());
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                result.append(board[i][j]);
            }
            result.append("\n");
        }
        
        return result.toString();
    }
    
    public static enum LightState {
        OFF, ON;
        
        public LightState push() {
            if (this == OFF)
                return ON;
            else
                return OFF;
        }
        
        @Override
        public String toString() {
            if (this == OFF)
                return "■";
            else
                return "□";
        }
    }
    
    
    
}
