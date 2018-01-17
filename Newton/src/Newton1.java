import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program is aiming at using Newton iteration to calculate the square root
 * of a positive float point number.
 *
 * @author Chuanjing Guo
 *
 */
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double guess = x;
        boolean valid = true;
        final double WITHIN_ERROR = 0.0001;
        while (valid) {//using a while loop to determine if the current guess meet
                       //the requirement of the tolerated error range/
            guess = (guess + x / guess) / 2;
            if (Math.abs((guess * guess - x)) / x < WITHIN_ERROR
                    * WITHIN_ERROR) {
                valid = false;
            }
        }
        return guess;
    }

    /**
     * Main method.
     *
     * @param args
     *
     *            The main method will keep prompt the user to input a positive
     *            number,and show the square root of the input number to the
     *            user until the user want to stop this program.
     */

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        boolean IfCont = false;
        char decision = 'y';
        while (!IfCont) {
            out.print("Please enter a positive floatpoint number: ");
            double input = in.nextDouble();
            out.println(
                    "The square root of your input number is: " + sqrt(input));
            out.print(
                    "Do you want to calculate another suqare root(y for yes): ");
            decision = in.nextLine().charAt(0);
            if (decision != 'y') {
                IfCont = true;
                out.println("Goodbye.");
            }
        }
        in.close();
        out.close();
    }

}
