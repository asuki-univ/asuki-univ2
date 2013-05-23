package player.ai.simple;

import player.ai.eval.Evaluation;
import board.Turn;

public interface EvaluationFactory {
    public Evaluation makeEvaluation(Turn currentTurn);
}
