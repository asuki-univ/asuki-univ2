import player.Player;
import ai.AlphaBetaSimpleAI;
import ai.MinMaxSimpleAI;
import ai.SimpleAI;
import board.Board;
import board.Position;
import board.Turn;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.setup();

        Turn turn = Turn.BLACK;
        boolean hasPassed = false;

        //Player blackPlayer = new HumanPlayer(Turn.BLACK);
        //Player blackPlayer = new SimpleAI(Turn.BLACK);
        Player blackPlayer = new MinMaxSimpleAI(Turn.BLACK, 5);
        Player whitePlayer = new SimpleAI(Turn.WHITE);
        //Player whitePlayer = new MinMaxSimpleAI(Turn.WHITE);
        //Player whitePlayer = new NegaMaxSimpleAI(Turn.WHITE);
        // Player whitePlayer = new AlphaBetaSimpleAI(Turn.WHITE);
        
        while (true) {
            // 盤を見やすいように表示
            board.show();
            
            // どこかに置けないならばパスをしなければならない。
            if (!board.isPuttableSomewhere(turn.stone())) {
                if (hasPassed) // ２連続パスすると終了
                    break;

                hasPassed = true;
                turn = turn.flip();
                continue;
            }

            hasPassed = false;
            Position p; 
            if (turn == Turn.BLACK)
                p = blackPlayer.play(board);
            else
                p = whitePlayer.play(board);

            board.put(p.x, p.y, turn.stone());
            turn = turn.flip();
        }
    }
}
