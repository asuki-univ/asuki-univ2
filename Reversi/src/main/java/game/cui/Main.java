package game.cui;
import player.Player;
import player.ai.LearnedAI;
import player.ai.TranpositionEvaluationCompleteReadingSimpleAI;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.setup();

        Turn turn = Turn.BLACK;
        boolean hasPassed = false;

        //Player blackPlayer = new HumanPlayer(Turn.BLACK);
        //Player blackPlayer = new SimpleAI(Turn.BLACK);
        //Player blackPlayer = new MinMaxSimpleAI(Turn.BLACK, 5);
        // Player blackPlayer = new AlphaBetaSimpleAI(Turn.BLACK, 5);
        Player blackPlayer = new LearnedAI(turn, 9, 15);
        //Player whitePlayer = new SimpleAI(Turn.WHITE);
        //Player whitePlayer = new MinMaxSimpleAI(Turn.WHITE);
        //Player whitePlayer = new NegaMaxSimpleAI(Turn.WHITE);
        //Player whitePlayer = new AlphaBetaSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new AlphaBetaEvaluationSimpleWithCompleteReadingAI(Turn.WHITE, 5, 15);
        //Player whitePlayer = new NegaScoutEvaluationSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new NegaScoutEvaluationSimpleWithCompleteReadingAI(Turn.WHITE, 5, 15);
        //Player whitePlayer = new TranpositionEvaluationSimpleAI(Turn.WHITE, 5);
        Player whitePlayer = new TranpositionEvaluationCompleteReadingSimpleAI(Turn.WHITE, 5, 15);

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

        System.out.printf("BLACK = %d\n", board.countStone(Stone.BLACK));
        System.out.printf("WHITE = %d\n", board.countStone(Stone.WHITE));
    }
}
