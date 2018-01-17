import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NaturalNumber}.
 *
 * @author Chuanjing Guo
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires
     *
     *           <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     *  and [Throughout the whole calculation any part of the subtraction of 2 NaturalNumbers
     *  can never be negative] and [The Natural number should never be divided by zero]
     *           </pre>
     *
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        NaturalNumber result = new NaturalNumber1L(0);

        String operator = exp.label();
        if (!exp.hasAttribute("value")) {
            NaturalNumber left = new NaturalNumber1L(evaluate(exp.child(0)));
            NaturalNumber right = new NaturalNumber1L(evaluate(exp.child(1)));

            if (operator.equals("plus")) {
                left.add(right);
                result.copyFrom(left);
            }
            if (operator.equals("minus")) {
                if (left.compareTo(right) < 0) {
                    Reporter.fatalErrorToConsole(
                            "The result of the calculation is a negative value "
                                    + "the Natural Number should not be negative. ");
                } else {
                    left.subtract(right);
                    result.copyFrom(left);
                }

            }
            if (operator.equals("divide")) {
                if (right.isZero()) {
                    Reporter.fatalErrorToConsole(
                            "The denominator should not be zero ");

                } else {
                    left.divide(right);
                    result.copyFrom(left);
                }

            }
            if (operator.equals("times")) {
                left.multiply(right);
                result.copyFrom(left);
            }

        } else {
            NaturalNumber valueOfNumber = new NaturalNumber1L(
                    exp.attributeValue("value"));
            result.copyFrom(valueOfNumber);
        }

        return result;

    }

    /**
     * Main method.
     *
     * @param args
     *            The program prompt the user to type in a name of a well-formed
     *            xml expression and will return a NaturalNumber number which is
     *            the result of the expression.
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}