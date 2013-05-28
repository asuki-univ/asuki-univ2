package random;

import java.util.Random;

public class RandomCorrelation {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Random random = new Random(0);
        int previousInt = random.nextInt();
        for (int i = 0; i < 1000; ++i) {
            int currentInt = random.nextInt();
            System.out.println(previousInt + " " + currentInt);
            previousInt = currentInt;
        }
    }

}
