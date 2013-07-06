import game.Game;
import game.Game.Winner;
import player.Player;
import player.ai.PerfectPlayWrapperPlayer;
import player.ai.RandomPlayer;
import player.ai.SimpleMonteCarloPlayer;
import player.ai.impl.AlphaBetaAdvancedAIPlayer;
import player.ai.impl.AlphaBetaBoardScoreAIPlayer;
import player.ai.simple.SimpleAIPlayer;
import board.Board;
import board.Turn;

public class EvalWinRate {
    private static final int GAME_COUNT = 100;

    public static void main(String[] args) {
        //Player blackPlayer = new RandomPlayer(Turn.BLACK);
        Player blackPlayer = new SimpleMonteCarloPlayer(Turn.BLACK, 100);
        //Player blackPlayer = new UCB1MonteCarloPlayer(Turn.BLACK, 1000);
        //Player blackPlayer = new HumanPlayer(Turn.BLACK);
        //Player blackPlayer = new SimpleAIPlayer(Turn.BLACK);
        //Player blackPlayer = new MinMaxSimpleAI(Turn.BLACK, 5);
        //Player blackPlayer = new AlphaBetaBoardScoreAIPlayer(Turn.BLACK, 5);
        //Player blackPlayer = new AlphaBetaAdvancedAIPlayer(Turn.BLACK, 5);
        //Player blackPlayer = new PerfectPlayWrapperPlayer(new AlphaBetaBoardScoreAIPlayer(Turn.BLACK, 5), 15);
        //Player blackPlayer = new PerfectPlayWrapperPlayer(new AlphaBetaAdvancedAIPlayer(Turn.BLACK, 5), 15);
        //Player blackPlayer = new TranpositionAdvancedAIPlayer(Turn.BLACK, 3);
        //Player blackPlayer = new AlphaBetaBoardScoreAIPlayer(Turn.BLACK, 3);

        //Player whitePlayer = new PerfectPlayWrapperPlayer(new AlphaBetaBoardScoreAIPlayer(Turn.WHITE, 5), 15);
        Player whitePlayer = new PerfectPlayWrapperPlayer(new AlphaBetaAdvancedAIPlayer(Turn.WHITE, 5), 15);
        //Player whitePlayer = new SimpleAIPlayer(Turn.WHITE);
        //Player whitePlayer = new SimpleMonteCarloPlayer(Turn.WHITE, 100);
        //Player whitePlayer = new UCB1MonteCarloPlayer(Turn.WHITE, 1000);
        //Player whitePlayer = new MinMaxSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new NegaMaxSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new AlphaBetaBoardScoreAIPlayer(Turn.WHITE, 5);
        //Player whitePlayer = new AlphaBetaEvaluationSimpleWithCompleteReadingAI(Turn.WHITE, 5, 15);
        //Player whitePlayer = new NegaScoutEvaluationSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new NegaScoutEvaluationSimpleWithCompleteReadingAI(Turn.WHITE, 5, 15);
        //Player whitePlayer = new TranpositionEvaluationSimpleAI(Turn.WHITE, 5);
        //Player whitePlayer = new TranpositionEvaluationCompleteReadingSimpleAI(Turn.WHITE, 5, 15);

        int black = 0;
        int white = 0;
        int tie = 0;

        Game game = new Game();
        for (int i = 0; i < GAME_COUNT; ++i) {
            Board board = new Board();
            board.setup();
            Winner winner = game.play(blackPlayer, whitePlayer, board, Turn.BLACK, false);
            switch (winner) {
            case BLACK:
                ++black;
                break;
            case WHITE:
                ++white;
                break;
            case TIE:
                ++tie;
                break;
            default:
                System.err.println("Unknown game result.");
            }
            System.out.println("winner: " + winner + " (at " + (i + 1) + ")");
        }

        System.out.println("BLACK: " + blackPlayer.getClass().getSimpleName());
        System.out.println("WHITE: " + whitePlayer.getClass().getSimpleName());
        System.out.println("black: " + (double) black / GAME_COUNT);
        System.out.println("white: " + (double) white / GAME_COUNT);
        System.out.println("tie: " + (double) tie / GAME_COUNT);
    }

}
