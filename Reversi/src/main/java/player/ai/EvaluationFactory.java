package player.ai;

import player.ai.eval.Evaluation;
import board.Turn;

public interface EvaluationFactory {
    public Evaluation makeEvaluation(Turn currentTurn);
}
