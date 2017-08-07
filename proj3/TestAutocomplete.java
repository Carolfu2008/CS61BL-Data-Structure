import ucb.junit.textui;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * The suite of all JUnit tests for the Autocomplete class.
 *
 * @author
 */
public class TestAutocomplete {

    /**
     * A dummy test to avoid complaint.
     */
    @Test
    public void placeholderTest() {
        String[] terms = new String[5];
        terms[0] = "hello";
        terms[1] = "hi";
        terms[2] = "hell";
        terms[3] = "hello666";
        terms[4] = "hi!!!";
        double[] weights = new double[5];
        weights[0] = 100;
        weights[1] = 200;
        weights[2] = 300;
        weights[3] = 250;
        weights[4] = 150;
        Autocomplete test1 = new Autocomplete(terms, weights);
        assertTrue(test1.tt.find("hello"));
        assertTrue(test1.tt.find("hi"));
        assertTrue(test1.tt.find("hell"));
        assertTrue(test1.tt.find("hello666"));
        assertTrue(test1.tt.find("hi!!!"));
        assertFalse(test1.tt.find("hi!!!！"));
        assertFalse(test1.tt.find("hi!!"));
        assertFalse(test1.tt.find("better"));
        assertFalse(test1.tt.find("Hi!!!"));
        assertFalse(test1.tt.find("hello!"));
        assertEquals(100, test1.weightOf("hello"), 0);
        assertEquals(200, test1.weightOf("hi"), 0);
        assertEquals(300, test1.weightOf("hell"), 0);
        assertEquals(250, test1.weightOf("hello666"), 0);
        assertEquals(150, test1.weightOf("hi!!!"), 0);
        assertEquals("hell", test1.topMatch("h"));
        assertEquals("hello666", test1.topMatch("hello"));
        assertEquals("hi", test1.topMatch("hi"));
        ArrayList<String> answer1 = new ArrayList<String>();
        assertEquals(answer1, test1.topMatches("gg", 3));
        ArrayList<String> answer2 = new ArrayList<String>();
        answer2.add("hi!!!");
        assertEquals(answer2, test1.topMatches("hi!!!", 3));
        ArrayList<String> answer3 = new ArrayList<String>();
        answer3.add("hell");
        answer3.add("hello666");
        answer3.add("hi");
        assertEquals(answer3, test1.topMatches("h", 3));
    }

    @Test
    public void placeholderTest2() {
        String[] terms = new String[6];
        terms[0] = "smog";
        terms[1] = "buck";
        terms[2] = "sad";
        terms[3] = "spite";
        terms[4] = "spit";
        terms[5] = "spy";
        double[] weights = new double[6];
        weights[0] = 5;
        weights[1] = 10;
        weights[2] = 12;
        weights[3] = 20;
        weights[4] = 15;
        weights[5] = 7;
        Autocomplete test2 = new Autocomplete(terms, weights);
        assertEquals("spite", test2.topMatch("s"));
    }

    @Test
    public void placeholderTest3() {
        String[] terms = new String[5];
        terms[0] = "Shanghai, China";
        terms[1] = "Buenos Aires, Argentina";
        terms[2] = "Mumbai, India";
        terms[3] = "Mexico City, Distrito Federal, Mexico";
        terms[4] = "Karachi, Pakistan";
        double[] weights = new double[5];
        weights[0] = 14608512;
        weights[1] = 13076300;
        weights[2] = 12691836;
        weights[3] = 12294193;
        weights[4] = 11624219;
        Autocomplete test2 = new Autocomplete(terms, weights);
        assertEquals("Karachi, Pakistan", test2.topMatch("K"));
        assertEquals("Shanghai, China", test2.topMatch(""));
        ArrayList<String> answer4 = new ArrayList<String>();
        answer4.add("Shanghai, China");
        answer4.add("Buenos Aires, Argentina");
        answer4.add("Mumbai, India");
        answer4.add("Mexico City, Distrito Federal, Mexico");
        answer4.add("Karachi, Pakistan");
        assertEquals(answer4, test2.topMatches("", 5));
    }


    /**
     * Run the JUnit tests above.
     */
    public static void main(String[] ignored) {
        textui.runClasses(TestAutocomplete.class);
    }
}
