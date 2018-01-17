import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Chuanjing Guo
 *
 *
 *         Your customer, Cy Burnett, heads a major publisher of textbooks who
 *         is migrating toward on-line access. Cy wants a relatively
 *         easy-to-maintain glossary facility. the program below will satisfy
 *         the requirement.
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * The method readFromFileStoreInMap will return a map variable that all the
     * information in the map will come from the input file. the key of the map
     * would be String type and the associated value would be queue variables.
     * the terms from the input file would be saved in the map as the key, while
     * all the associated descriptions of the key term would be saved in the
     * queue variable. the method will read the description in the input file
     * each line per time and every line of the description will be enqueue into
     * the Queue variable by order.
     */
    static Map<String, Queue<String>> readFromFileStoreInMap(String fileName) {

        Map<String, Queue<String>> result = new Map1L<>();
        SimpleReader in = new SimpleReader1L(fileName);

        Queue<String> key = new Queue1L<>();
        Queue<String> valueAndSpace = new Queue1L<>();
        Sequence<Queue<String>> value = new Sequence1L<>();

        while (!in.atEOS()) {
            String incomingElement = in.nextLine();
            if (incomingElement.indexOf(" ") < 0// the terms in the input file
                    //will not have any whitespace that how the program recognize if the
                    //line of the string is term or description.
                    && incomingElement.length() != 0) {
                key.enqueue(incomingElement);
            } else {
                valueAndSpace.enqueue(incomingElement);
            } // all the descriptions for all the terms now are store in the
              //queue called valueAndSpace
              // but they will be separate later by the space between
              //different description.
        }
        int count = 0;
//the nested while loop below will add lines before a white space
        //occurring to a queue called valueTemporary
        //and when a white space occur the queue will be store to sequence called value.
        while (valueAndSpace.length() > 0) {
            boolean ifSpace = true;
            Queue<String> valueTemporary = new Queue1L<>();

            while (ifSpace && valueAndSpace.length() > 0) {
                String str = valueAndSpace.dequeue();

                if (str.length() != 0) {
                    valueTemporary.enqueue(str);

                } else {

                    ifSpace = false;
                }
            }
            value.add(count, valueTemporary);
            count++;
        }
        int count2 = 0;
// the while loop below pair the terms in the queue key and the description queue in the
        //sequence and put them into the desired map.
        while (count2 < value.length()) {
            if (!result.hasKey(key.front())) {
                result.add(key.dequeue(), value.entry(count2));
            }
            count2++;
        }
        in.close();
        return result;

    }

    // the method outputTheIndexPage will generate a html file called index.html and
    //store it in the folder that input by the user. the queue variable order here
    // is used to sort the terms in the index page in the alphabet order.
    public static void outputTheIndexPage(Queue<String> order,
            String folderName) {
        Queue<String> temp = order.newInstance();
        temp.transferFrom(order);
        SimpleWriter out = new SimpleWriter1L(folderName + "/index.html");
        out.println("<html> <head> <title>");
        out.println("Sample Glossary");
        out.println("</title> </head> <body>");
        out.println("<h2>Sample Glossary</h2>");
        out.println("<hr/>");
        out.println("<h3>Index</h3>");
        out.println("<ul>");
        while (temp.length() > 0) {
            String term = temp.dequeue();
            order.enqueue(term);
            out.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
        out.close();

    }

    // the method outputTheTermPage will generate the html files for all the terms
    // the within the input file and the page for each term would be named "term.html".
    //where the term is the actual term.
    //the method would use the queue variable called order to sort the terms in the
    // index page by the alphabet order.
    //the method will store all the files in the folder that input by the user.
    public static void outputTheTermPage(Queue<String> order,
            Map<String, Queue<String>> mi, String folderName) {

        Set<Character> separatorSet = new Set1L<>();
        // the Set separatorSet would contain all the separator marks below
        separatorSet.add(',');
        separatorSet.add(' ');
        separatorSet.add('.');
        separatorSet.add('?');
        separatorSet.add('!');
        separatorSet.add('\t');

        Queue<String> temp1 = order.newInstance();
        Queue<String> temp2 = order.newInstance();
        while (order.length() > 0) {
            String cutOut = order.dequeue();
            temp1.enqueue(cutOut);
            temp2.enqueue(cutOut);
        }
        while (temp1.length() > 0) {
            String keyTerm = temp1.dequeue();
            SimpleWriter out = new SimpleWriter1L(
                    folderName + "/" + keyTerm + ".html");
            out.println("<html> <head> <title>");
            out.println(keyTerm);
            out.println("</title> </head> <body>");
            out.println("<h2><b><i>");
            out.println("<font color=\"red\">");
            out.println(keyTerm);
            out.println("</font></i></b></h2>");
            out.println("<blockquote>");
            Queue<String> description = mi.value(keyTerm);
            while (description.length() > 0) {
                String des = description.dequeue();
                int position = 0;
                while (position < des.length()) {
                    String token = nextWordOrSeparator(des, position,
                            separatorSet);
                    if (separatorSet.contains(token.charAt(0))) {
                        out.print(token);
                    } else {
                        int count = 0;
                        for (int i = 0; i < temp2.length(); i++) {
                            String str = temp2.front();
                            if (token.equals(str)) {
                                out.print(" <a href=\"" + token + ".html\">"
                                        + token + "</a>");
                            } else {
                                count++;
                            }
                            temp2.rotate(1);
                        }
                        if (count == temp2.length()) {
                            out.print(token);
                        }
                    }
                    position += token.length();
                }
            }
            out.println("</blockquote>");
            out.println("<hr/>");
            out.println("<p>");
            out.println("Return to <a href=\"index.html\">index</a>.");
            out.println("</p>");
            out.println("</body>");
            out.println("</html>");
            out.close();

        }

    }

// the method below is used to determine if a string of character within a
    //String is a word or some separators. the return value would be a string of
    // characters that doesn't contains any characters in the given Set called
    // separators, or it will return a string of characters that all the characters
    //in this string are also in the Set separators.
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        String word = "", separator = "", result = "";
        boolean Nstop = true;
        while (Nstop) {
            char c = text.charAt(position);
            if (separators.contains(c)) {
                separator = separator + c;
                result = separator;
                if (position == text.length() - 1
                        || !separators.contains(text.charAt(position + 1))) {
                    Nstop = false;
                }
            } else {
                word = word + c;
                result = word;
                if (position == text.length() - 1
                        || separators.contains(text.charAt(position + 1))) {
                    Nstop = false;
                }
            }
            position++;
        }
        return result;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.println("Please enter the name of the file: ");
        String fileName = in.nextLine();
        out.println(
                "Please enter the name of the folder(the folder should already exist): ");
        String folder = in.nextLine();
        Map<String, Queue<String>> outputMap = new Map1L<>();

        outputMap = readFromFileStoreInMap(fileName);
        // use the method readFromFileStoreInMap to generate a map where
        //all the information from the input file would be stored as the way that
        //all the term in the file would be saved as
        //key of the map, all the associate definition of a particular term
        //would be saved as the value of the term.

        // declare a queue variable called order here would serve as a to-be-compared
        //object to sort the term in the index page in a alphabet order.
        //first use the sequence getKeyTerm to get all the terms of the input file
        //second construct a string comparator called "cs" to be the argument when
        // the sort method applied to the queue "order".
        Queue<String> order = new Queue1L<>();
        Sequence<String> getKeyTerm = new Sequence1L<>();
        SimpleReader fileIn = new SimpleReader1L(fileName);
        int count = 0;
        while (!fileIn.atEOS()) {
            String str = fileIn.nextLine();
            getKeyTerm.add(count, str);
            count++;
        }
        order.enqueue(getKeyTerm.remove(0));
        int count2 = 0;
        while (count2 < getKeyTerm.length() - 1) {
            if (getKeyTerm.entry(count2).length() == 0) {
                order.enqueue(getKeyTerm.entry(count2 + 1));
            }
            count2++;
        }
        Comparator<String> cs = new StringLT();

        order.sort(cs);

        outputTheIndexPage(order, folder);
        outputTheTermPage(order, outputMap, folder);

        in.close();
        out.close();
        fileIn.close();
    }

}