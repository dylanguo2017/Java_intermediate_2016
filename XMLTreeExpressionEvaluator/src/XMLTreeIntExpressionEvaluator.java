import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Chuanjing Guo
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
     *           </pre>
     *
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        int result = 0;

        String operator = exp.label();
        if (!exp.hasAttribute("value")) {
            int left = evaluate(exp.child(0));
            int right = evaluate(exp.child(1));
            if (operator.equals("plus")) {
                result = left + right;
            }
            if (operator.equals("minus")) {
                result = left - right;
            }
            if (operator.equals("divide")) {
                if (right == 0) {
                    Reporter.fatalErrorToConsole(
                            "The denominator should not be zero ");
                } else {
                    result = left / right;
                }

            }
            if (operator.equals("times")) {
                result = left * right;
            }

        } else {
            result = Integer.parseInt(exp.attributeValue("value"));
        }

        return result;

    }

    /**
     * Main method.
     *
     * @param args
     *            The program prompt the user to type in a name of a well-formed
     *            xml expression and will return an integer number which is the
     *            result of the expression.
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