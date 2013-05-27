package algorithm.secondseason.random;

public class CongruentMethod {

    private static final int MULTIPLICAND = 11;
    private static final int ADDEND = 6;
    
    private int value;

    public CongruentMethod(int seed) {
        this.value = seed;
    }
    
    public int next() {
        // int cast は mod 2**32
        this.value = (int) ((long) value * MULTIPLICAND + ADDEND);
        return value;
    }

    /*
    public static void main(String[] args) {
        CongruentMethod cm = new CongruentMethod(0);
        int previous = cm.value;
        for (int i = 0; i < 1000; ++i) {
            int current = cm.next();
            System.out.println((previous & 0b1111) + " " + (current & 0b1111));
//            System.out.println(previous + " " + current);
            previous = current;
        }
    } */

    public static void main(String[] args) {
        CongruentMethod cm = new CongruentMethod(0);
        System.out.println(0);
        for (int i = 1; i < 1000; ++i) {
            System.out.println(cm.next());
        }
    }
}
