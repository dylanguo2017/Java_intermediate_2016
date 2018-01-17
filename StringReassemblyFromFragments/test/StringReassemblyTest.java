import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class StringReassemblyTest {

    /*
     * text addToSetAvoidingSubstrings
     */
    @Test
    public void testaddToSetAvoidingSubstrings_case1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("What ar");
        strSet.add("are");
        strSet.add("u doing?");
        String str = "What are";
        Set<String> result = new Set1L<>();
        result.add("What are");
        result.add("u doing?");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(result, strSet);

    }

    /*
     * text addToSetAvoidingSubstrings
     */
    @Test
    public void testaddToSetAvoidingSubstrings_case2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hello world");
        strSet.add(" good morning");

        strSet.add("1go");
        String str = "1goood";
        Set<String> result = new Set1L<>();
        result.add("Hello world");
        result.add("1goood");
        result.add(" good morning");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(result, strSet);

    }

    /*
     * text combination
     */
    @Test
    public void testcombination() {
        String result = "";
        String str1 = "ABCDEF";
        String str2 = "DEFghj";
        int overlap = 3;
        String expect = "ABCDEFghj";
        result = StringReassembly.combination(str1, str2, overlap);
        assertEquals(result, expect);

    }

    /*
     * text linesFromInput
     */
    @Test
    public void testlinesFromInput() {
        Set<String> result = new Set1L<>();
        SimpleReader inFile = new SimpleReader1L("data/cheer-8-2.txt");
        Set<String> fragments = StringReassembly.linesFromInput(inFile);
        result.add("Bucks -- Beat");
        result.add("Go Bucks");
        result.add("o Bucks -- B");
        result.add("Beat Mich");
        result.add("Michigan~");
        assertEquals(result, fragments);

    }

}
