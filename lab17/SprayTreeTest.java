import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by lifesaver on 24/07/2017.
 */
public class SprayTreeTest {

    @Test
    public void rotate() throws Exception {
        SplayTree<Integer, Integer> tree = new SplayTree<>();
        assertEquals(0, tree.size());
        tree.put(0, 0);
        assertEquals(1, tree.size());
        tree.put(0, 0);
        assertEquals(2, tree.size());
    }
}
