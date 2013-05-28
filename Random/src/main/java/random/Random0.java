package random;

import java.util.Random;

public class Random0 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Random random = new Random(0);
        for (int i = 0; i < 1000; ++i) {
            System.out.println(random.nextInt());
        }
    }

}
