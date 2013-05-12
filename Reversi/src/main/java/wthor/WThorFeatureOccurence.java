package wthor;


public class WThorFeatureOccurence {
    private int[] numOccurence = new int[WThorParams.ALL_PARAMS_SIZE];

    public void ensureMinValue(int minValue) {
        for (int i = 0; i < WThorParams.ALL_PARAMS_SIZE; ++i) {
            if (numOccurence[i] < minValue)
                numOccurence[i] = minValue;
        }
    }

    public void incr(int index) {
        numOccurence[index] += 1;
    }

    public int numOccurence(int index) {
        return numOccurence[index];
    }
}
