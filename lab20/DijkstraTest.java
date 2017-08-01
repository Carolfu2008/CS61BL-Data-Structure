import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lifesaver on 31/07/2017.
 */
public class DijkstraTest {
    @Test
    public void test1() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 3, 30);
        g.addEdge(0, 4, 100);
        g.addEdge(1, 2, 50);
        g.addEdge(2, 4, 10);
        g.addEdge(3, 4, 60);
        g.addEdge(3, 2, 20);

        ArrayList<Integer> path0 = g.shortestPath(0, 1);
        ArrayList<Integer> path1 = g.shortestPath(0, 2);
        ArrayList<Integer> path2 = g.shortestPath(0, 3);
        ArrayList<Integer> path3 = g.shortestPath(0, 4);

        List<Integer> expected0 = Arrays.asList(0, 1);
        List<Integer> expected1 = Arrays.asList(0, 3, 2);
        List<Integer> expected2 = Arrays.asList(0, 3);
        List<Integer> expected3 = Arrays.asList(0, 3, 2, 4);

        assertEquals(expected0, path0);
        assertEquals(expected1, path1);
        assertEquals(expected2, path2);
        assertEquals(expected3, path3);
    }

}

