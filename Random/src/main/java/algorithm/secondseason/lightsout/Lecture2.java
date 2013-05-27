package algorithm.secondseason.lightsout;

public class Lecture2 {

    private int[][] board;

    public static void main(String[] args) {
        System.out.println("Lecture2");

        Lecture2 lec2 = new Lecture2();

        lec2.initBoard(3);
        System.out.println("board initialized: " + lec2.board);

        lec2.problem1();
        lec2.display();

        lec2.push(1, 1);
        lec2.display();

        lec2.push(2, 0);
        lec2.display();
    }

    private void initBoard(int size) {
        board = new int[size][size];
    }

    private void problem1() {
        board = new int[][] {{0, 1, 0},
                             {0, 1, 1},
                             {1, 0, 0}};
    }

    /**
     *   y →
     * x □□□□□
     * ↓□□□□□
     *   □□□□□
     *   □□□□□
     *   □□□□□
     *
     */
    private void push(int x, int y) {
        if (x > 0) board[x - 1][y] = 1 - board[x - 1][y];
        if (y > 0) board[x][y - 1] = 1 - board[x][y - 1];
        board[x][y] = 1 - board[x][y];
        if (x < board.length - 1) board[x + 1][y] = 1 - board[x + 1][y];
        if (y < board[x].length - 1) board[x][y + 1] = 1 - board[x][y + 1];
    }

    private void display() {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                System.out.printf("%d", board[i][j]);
                if (j < board[i].length - 1)
                    System.out.printf(" ");
                else
                    System.out.println();
            }
        }
        System.out.println();
    }

}

