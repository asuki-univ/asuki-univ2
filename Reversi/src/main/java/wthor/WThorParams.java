package wthor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WThorParams {
    private static final int[] BEKI3 = new int[] {
            1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323,
    };

    public static final int LENGTH8_SIZE = BEKI3[8];
    public static final int DIAG4_SIZE = BEKI3[4];
    public static final int DIAG5_SIZE = BEKI3[5];
    public static final int DIAG6_SIZE = BEKI3[6];
    public static final int DIAG7_SIZE = BEKI3[7];
    public static final int DIAG8_SIZE = BEKI3[8];
    public static final int CORNER33_SIZE = BEKI3[9];
    public static final int CORNER25_SIZE = BEKI3[10];
    public static final int EDGE2X_SIZE = BEKI3[10];

    public static final int LENGTH8_OFFSET  = 0;
    public static final int DIAG4_OFFSET    = LENGTH8_OFFSET  + LENGTH8_SIZE;
    public static final int DIAG5_OFFSET    = DIAG4_OFFSET    + DIAG4_SIZE;
    public static final int DIAG6_OFFSET    = DIAG5_OFFSET    + DIAG5_SIZE;
    public static final int DIAG7_OFFSET    = DIAG6_OFFSET    + DIAG6_SIZE;
    public static final int DIAG8_OFFSET    = DIAG7_OFFSET    + DIAG7_SIZE;
    public static final int CORNER33_OFFSET = DIAG8_OFFSET    + DIAG8_SIZE;
    public static final int CORNER25_OFFSET = CORNER33_OFFSET + CORNER33_SIZE;
    public static final int EDGE2X_OFFSET   = CORNER25_OFFSET + CORNER25_SIZE;

    public static final int ALL_PARAMS_SIZE = EDGE2X_OFFSET + EDGE2X_SIZE;
    public static final double LAMBDA = 0.04;

    private double[] values = new double[ALL_PARAMS_SIZE];

    private double squareSum = 0;

    public WThorParams() {
    }

    public WThorParams(String filename) throws IOException {
        load(filename);
        for (int i = 0; i < ALL_PARAMS_SIZE; ++i)
            squareSum += values[i] * values[i];
    }

    public double getSquareSumLambda() {
        return squareSum * LAMBDA;
    }

    public double get(int index) {
        return values[index];
    }

    public void add(int index, double value) {
        set(index, get(index) + value);
    }

    public void set(int index, double value) {
        squareSum -= values[index] * values[index];
        values[index] = value;
        squareSum += values[index] * values[index];
    }

    public void save(String filename) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        for (int i = 0; i < ALL_PARAMS_SIZE; ++i)
            dos.writeDouble(values[i]);
        dos.close();
    }

    public void load(String filename) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
        for (int i = 0; i < ALL_PARAMS_SIZE; ++i)
            values[i] = dis.readDouble();
        dis.close();
    }
}
