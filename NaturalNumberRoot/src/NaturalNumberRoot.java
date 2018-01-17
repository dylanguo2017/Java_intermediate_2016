import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method. This program is to implement the
 * root static method for NaturalNumber using the interval halving root
 * algorithm.
 *
 * @author Chuanjing Guo
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        NaturalNumber lowEnough = new NaturalNumber2(0);
        NaturalNumber tooHigh = new NaturalNumber2(0);
        tooHigh.copyFrom(n);
        tooHigh.increment();
        NaturalNumber One = new NaturalNumber2(1);
        NaturalNumber Two = new NaturalNumber2(2);
        boolean valid = true;
        while (valid) {
            NaturalNumber tem = new NaturalNumber2(0);
            tem.copyFrom(tooHigh);
            tem.subtract(lowEnough);
            if (tem.compareTo(One) > 0) {
                NaturalNumber guess = new NaturalNumber2(0);
                guess.copyFrom(tooHigh);
                guess.add(lowEnough);
                guess.divide(Two);
                NaturalNumber tem2 = new NaturalNumber2(0);
                tem2.copyFrom(guess);

                tem2.power(r);
                if (tem2.compareTo(n) > 0) {
                    tooHigh.copyFrom(guess);

                } else {
                    lowEnough.copyFrom(guess);

                }

            } else {
                valid = false;
            }
        }
        n.copyFrom(lowEnough);

    }

    /**
     * Main method.
     *
     * @param args
     *            the main method will call the root method written before and
     *            use the method to calculate a certain r-th root of a certain
     *            naturalnumber and compare it with the right answer to see if
     *            the method is correct.
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final int[] numbers = { 0, 1, 13, 1024, 189943527, 0, 1, 13, 4096,
                189943527, 0, 1, 13, 1024, 189943527, 82, 82, 82, 82, 82, 9, 27,
                81, 243, 143489073 };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15 };
        final int[] results = { 0, 1, 3, 32, 13782, 0, 1, 2, 16, 574, 0, 1, 1,
                1, 3, 9, 4, 3, 2, 1, 3, 3, 3, 3, 3 };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
