package player.ai.simple;

import player.Player;
import player.ai.EvalResult;
import player.ai.eval.Evaluation;
import board.Board;
import board.Position;
import board.Turn;

public abstract class EvaluationAI extends Player {
    EvaluationFactory evaluationFactory;

    public EvaluationAI(Turn turn, EvaluationFactory factory) {
        super(turn);
        this.evaluationFactory = factory;
    }

    @Override
    public Position play(Board board) {
        return eval(board).getPosition();
    }

    private EvalResult eval(Board board) {
        Position p = null;
        double maxScore = -100000; // TODO(mayah): この -100000 がどこから出てきたのか、全く気に食わない。

        for (int y = 1; y <= Board.HEIGHT; ++y) {
            for (int x = 1; x <= Board.WIDTH; ++x) {
                if (!board.isPuttable(x, y, turn.stone()))
                    continue;

                // この盤面を評価する
                Evaluation e = evaluationFactory.makeEvaluation(turn);
                Board b = board.clone();
                e.willPut(b, x, y, turn.stone());
                b.put(x, y, turn.stone());

                // 評価が、今までのものの中で最も良ければ、更新する。
                int score = e.score(board, turn.stone());
                if (maxScore < score) {
                    maxScore = score;
                    p = new Position(x, y);
                }
            }
        }

        // Since eval will be called only when a stone can be placed somewhere.
        assert (p != null);
        return new EvalResult(maxScore, p);
    }
}
