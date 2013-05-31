package player.ai.searcher;

import java.util.Comparator;

import player.ai.EvalResult;

class HandComparator implements Comparator<EvalResult> {
    @Override
    public int compare(EvalResult lhs, EvalResult rhs) {
        if (lhs.getScore() != rhs.getScore())
            return lhs.getScore() > rhs.getScore() ? -1 : 1;
        return 0;
    }
}
