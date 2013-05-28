package lightsout;

public class GaussianElim {

    double[][] matrix = { { 3, 1, 2 }, { 2, 2, 1 }, { 6, 3, 3 }, };

    public GaussianElim() {};

    public GaussianElim(double[][] matrix) {
        this.matrix = matrix;
    }
    
    public static void main(String[] args) {
        GaussianElim ge = new GaussianElim();
        display(ge.matrix);
        System.out.println("↓\naugment\n↓");
        double[][] augmentedMatrix = ge.augmentMatrix(ge.matrix);
        ge.eliminate(augmentedMatrix);
        
        GaussianElim ge2 = new GaussianElim(new double[][] {
                {1, 1, 1},
                {1, 0, 0},
                {0, 1, 1}
        });
        ge2.eliminate(ge2.augmentMatrix(ge2.matrix));
    }

    double[][] augmentMatrix(double[][] matrix) {
        int size = matrix.length;
        double[][] result = new double[size][2 * size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < 2 * size; ++j) {
                if (j < size)
                    result[i][j] = matrix[i][j];
                else if (i == j - size)
                    result[i][j] = 1;
                else
                    result[i][j] = 0;
            }
        }

        return result;
    }

    void eliminate(double[][] matrix) {
        int rowIdx = 0;

        for (int colIdx = 0; colIdx < matrix.length; ++colIdx) {
            int pivotIdx = findPivotIdx(matrix, rowIdx, colIdx);
            if (pivotIdx < 0)
                continue;

            swapRows(matrix, pivotIdx, rowIdx);
            display(matrix);
            multiplyRow(matrix[rowIdx], (double) 1 / matrix[rowIdx][colIdx]);
            display(matrix);
            eliminateOtherRows(matrix, rowIdx, colIdx);
            display(matrix);
            ++rowIdx;
        }
    }

    int findPivotIdx(double[][] matrix, int rowIdx, int colIdx) {
        for (int i = rowIdx; i < matrix.length; ++i) {
            if (matrix[i][colIdx] != 0)
                return i;
        }

        return -1;
    }

    void swapRows(double[][] matrix, int pivotIdx, int rowIdx) {
        if (pivotIdx == rowIdx)
            return;
        double[] tmp = matrix[rowIdx];
        matrix[rowIdx] = matrix[pivotIdx];
        matrix[pivotIdx] = tmp;
    }

    void multiplyRow(double[] row, double mutiplier) {
        for (int i = 0; i < row.length; ++i) {
            row[i] *= mutiplier;
        }
    }

    void eliminateOtherRows(double[][] matrix, int rowIdx, int colIdx) {
        for (int i = 0; i < matrix.length; ++i) {
            if (i == rowIdx)
                continue;
            double coefficient = matrix[i][colIdx];
            for (int j = colIdx; j < matrix[i].length; ++j) {
                matrix[i][j] -= coefficient * matrix[rowIdx][j];
            }
        }
    }

    static void display(double[][] matrix) {
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                System.out.printf("%f", matrix[i][j]);
                if (j < matrix[i].length - 1)
                    System.out.printf(" ");
                else
                    System.out.println();
            }
        }
        System.out.println();
    }

}
