import ucb.junit.textui;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The suite of all JUnit tests for the Trie class.
 *
 * @author
 */
public class TestTrie {

    /**
     * A dummy test to avoid complaint.
     */
    @Test
    public void placeholderTest() {
        Trie t = new Trie();
        t.insert("Berkeley");
        assertEquals(true, t.find("Berke", false));
        assertEquals(false, t.find("Berke", true));
        assertEquals(false, t.find("berke", false));
        assertEquals(false, t.find("berke", false));
        assertEquals(true, t.find("Berkeley", false));
        assertEquals(true, t.find("Berkeley", true));
        assertEquals(false, t.find("berkeley", false));
        assertEquals(false, t.find("berkeley", true));
        assertEquals(false, t.find("Berkeleyy", false));
        assertEquals(false, t.find("Berkeleyy", true));
        assertEquals(false, t.find("berkeleyy", true));
        assertEquals(false, t.find("berkeleyy", false));
    }

    /**
     * Run the JUnit tests above.
     */
    public static void main(String[] ignored) {
        textui.runClasses(TestTrie.class);
    }
}
