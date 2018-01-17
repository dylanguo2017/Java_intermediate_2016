
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The program will calculate and report the values of the exponents a, b, c,
 * and d that bring the de Jager formula as close as possible to μ, as well as
 * the value of the formula and the relative error of the approximation to the
 * nearest hundredth of one percent.
 *
 * @author Chuanjing Guo
 *
 */
public final class ABCDGuesser1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser1() {
    }

    /**
     * @param mu
     *            the universal number provided by the users.
     * @param w
     *            the first random positive number provided by the user
     * @param x
     *            the second random positive number provided by the user
     * @param y
     *            the third random positive number provided by the user
     * @param z
     *            the fourth random positive number provided by the user
     *
     *            the method is intended to find out the nearest value the de
     *            jager formula can compute out to the given universal number.
     */
    private static double estimate(double mu, double w, double x, double y,
            double z) {

        double[] powers = { -5, -4, -3, -2, -1, (-1.0) / 2, (-1.0) / 3,
                (-1.0) / 4, 0, (1.0) / 4, (1.0) / 3, (1.0) / 2, 1, 2, 3, 4, 5 };
        int i = 0;
        double bestEstimate = 0;
        double currentEstimate = 0;
        double firstNo = 0, secondNo = 0, thirdNo = 0, fourthNo = 0;
        while (i < powers.length) {
            firstNo = Math.pow(w, powers[i]);
            int j = 0;
            while (j < powers.length) {
                secondNo = Math.pow(x, powers[j]);
                int k = 0;
                while (k < powers.length) {
                    thirdNo = Math.pow(y, powers[k]);
                    int l = 0;
                    while (l < powers.length) {
                        fourthNo = Math.pow(z, powers[l]);
                        currentEstimate = firstNo * secondNo * thirdNo
                                * fourthNo;
                        if (Math.abs(currentEstimate - mu) < Math
                                .abs(bestEstimate - mu)) {
                            bestEstimate = currentEstimate;
                        }
                        l++;
                    }
                    k++;
                }

                j++;
            }
            i++;

        }
        return bestEstimate;
    }

    /**
     * Main method.
     *
     * @param args
     *            the main method prompt the user for the universal number and
     *            the 4 random number, then use the de jager formula to compute
     *            the nearest number to the given universal number and display
     *            the relative error percentage.
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.print("Please enter a positive universal number: ");
        double mu = in.nextDouble();
        out.println();
        out.print("Please enter the first positive random number(not 1) : ");
        double w = in.nextDouble();
        out.println();
        out.print("Please enter the second positive random number(not 1) : ");
        double x = in.nextDouble();
        out.println();
        out.print("Please enter the third positive random number(not 1) : ");
        double y = in.nextDouble();
        out.println();
        out.print("Please enter the fourth positive random number(not 1) : ");
        double z = in.nextDouble();
        out.println();

        double result = estimate(mu, w, x, y, z);

        out.print("The nearest number to the universal number is: ");
        out.print(result, 2, false);
        out.println();

        double percent = (Math.abs(result - mu) / mu) * 100;
        out.print("The relative error is within: ");
        out.print(percent, 2, false);
        out.print(" %");
        /*
         * Put your main program code here; it may call myMethod as shown
         */

        in.close();
        out.close();
    }

}
