package wthor;

public class WThorParams {
    private static final int[] BEKI3 = new int[] {
            1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323,
    };

    public double[] length8 = new double[BEKI3[8]];
    public double[] diag4 = new double[BEKI3[4]];
    public double[] diag5 = new double[BEKI3[5]];
    public double[] diag6 = new double[BEKI3[6]];
    public double[] diag7 = new double[BEKI3[7]];
    public double[] diag8 = new double[BEKI3[8]];
    public double[] corner33 = new double[BEKI3[9]];
    public double[] corner25 = new double[BEKI3[10]];
    public double[] edge2x = new double[BEKI3[10]];

    public void add(WThorParams params) {
        for (int i = 0; i < length8.length; ++i)
            length8[i] += params.length8[i];
        for (int i = 0; i < diag4.length; ++i)
            diag4[i] += params.diag4[i];
        for (int i = 0; i < diag5.length; ++i)
            diag5[i] += params.diag5[i];
        for (int i = 0; i < diag6.length; ++i)
            diag6[i] += params.diag6[i];
        for (int i = 0; i < diag7.length; ++i)
            diag7[i] += params.diag7[i];
        for (int i = 0; i < diag8.length; ++i)
            diag8[i] += params.diag8[i];
        for (int i = 0; i < corner33.length; ++i)
            corner33[i] += params.corner33[i];
        for (int i = 0; i < corner25.length; ++i)
            corner25[i] += params.corner25[i];
        for (int i = 0; i < edge2x.length; ++i)
            edge2x[i] += params.edge2x[i];
    }
}
