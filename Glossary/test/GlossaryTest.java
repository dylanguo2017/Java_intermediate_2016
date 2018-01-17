import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;

public class GlossaryTest {

    @Test
    //test for readFromFileStoreInMap
    public void testreadFromFileStoreInMap() {
        String fileName = "data/0003";
        Map<String, Queue<String>> result = new Map1L<>();
        Map<String, Queue<String>> outMap = new Map1L<>();
        Queue<String> q1 = new Queue1L<>();
        Queue<String> q2 = new Queue1L<>();
        Queue<String> q3 = new Queue1L<>();
        q1.enqueue("void enqueue(T x)");
        q2.enqueue("removes and returns the entry at the front");
        q3.enqueue("report the length of this");
        outMap.add("enqueue", q1);
        outMap.add("dequeue", q2);
        outMap.add("length", q3);

        result = Glossary.readFromFileStoreInMap(fileName);
        assertEquals(outMap, result);

    }

    @Test
    //test for nextWordOrSeparator
    public void testnextWordOrSeparator() {
        final int pos1 = 0, pos2 = 5, pos3 = 10;
        Set<Character> separatorSet = new Set1L<>();
        separatorSet.add(',');
        separatorSet.add(' ');
        separatorSet.add('.');
        separatorSet.add('?');
        separatorSet.add('!');
        separatorSet.add('\t');
        String result1 = " ", result2 = " ", result3 = " ";
        String sample1 = "??!!w,what";
        String sample2 = "what,isgoing on";
        String sample3 = "....w,what,.,! .t";
        result1 = Glossary.nextWordOrSeparator(sample1, pos1, separatorSet);
        result2 = Glossary.nextWordOrSeparator(sample2, pos2, separatorSet);
        result3 = Glossary.nextWordOrSeparator(sample3, pos3, separatorSet);
        String desireOutput1 = "??!!";
        String desireOutput2 = "isgoing";
        String desireOutput3 = ",.,! .";

        assertEquals(desireOutput1, result1);
        assertEquals(desireOutput2, result2);
        assertEquals(desireOutput3, result3);

    }

}
