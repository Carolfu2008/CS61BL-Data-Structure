import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;
    private int startVertex;

    // Initialize a graph with the given number of vertices and no edges.
    public Graph(int numVertices) {
        adjLists = new LinkedList[numVertices];
        startVertex = 0;
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    // Change the vertex the iterator will start DFS from
    public void setStartVertex(int v) {
        if (v < vertexCount && v >= 0) {
            startVertex = v;
        } else {
            throw new IllegalArgumentException("Cannot set iteration start vertex to " + v + ".");
        }
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2.
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, null);
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2.
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, null);
    }

    // Add to the graph a directed edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addEdge(int v1, int v2, Object edgeInfo) {
        adjLists[v1].add(new Edge(v1, v2, edgeInfo));
    }

    // Add to the graph an undirected edge from vertex v1 to vertex v2,
    // with the given edge information. If the edge already exists,
    // replaces the current edge with a new edge with edgeInfo.
    public void addUndirectedEdge(int v1, int v2, Object edgeInfo) {
        adjLists[v1].add(new Edge(v1, v2, edgeInfo));
        adjLists[v2].add(new Edge(v2, v1, edgeInfo));
    }

    // Return true if there is an edge from vertex "from" to vertex "to";
    // return false otherwise.
    public boolean isAdjacent(int from, int to) {
        Iterator<Edge> vertexIter = adjLists[from].iterator();
        while (vertexIter.hasNext()) {
            Edge curEdge = vertexIter.next();
            if (curEdge.to.equals(to)) {
                return true;
            }
        }
        return false;
    }

    // Returns a list of all the neighboring  vertices 'u'
    // such that the edge (VERTEX, 'u') exists in this graph.
    public List<Integer> neighbors(int vertex) {
        List allNeighbors = new LinkedList<>();
        Iterator<Edge> vertexIter = adjLists[vertex].iterator();
        while (vertexIter.hasNext()) {
            Edge curEdge = vertexIter.next();
            allNeighbors.add(curEdge.to);
        }
        return allNeighbors;
    }

    // Return the number of incoming vertices for the given vertex,
    // i.e. the number of vertices v such that (v, vertex) is an edge.
    public int inDegree(int vertex) {
        int count = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (isAdjacent(i, vertex)) {
                count++;
            }
        }
        return count;
    }

    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    // A class that iterates through the vertices of this graph, starting with a given vertex.
    // Does not necessarily iterate through all vertices in the graph: if the iteration starts
    // at a vertex v, and there is no path from v to a vertex w, then the iteration will not
    // include w
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            fringe.push(start);
            visited = new HashSet();
        }

        public boolean hasNext() {
            if (fringe.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        public Integer next() {
            int returnValue = fringe.pop();
            if (!visited.contains(returnValue)) {
                for (Edge neighbor : adjLists[returnValue]) {
                    if (!visited.contains(neighbor.to()) && !fringe.contains(neighbor.to()))
                        fringe.push(neighbor.to());
                }
                visited.add(returnValue);
            }
            return returnValue;
        }
        //ignore this method

        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    // Return the collected result of iterating through this graph's
    // vertices as an ArrayList, starting from STARTVERTEX.
    public ArrayList<Integer> visitAll(int startVertex) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(startVertex);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    // Returns true iff there exists a path from STARVETEX to
    // STOPVERTEX. Assumes both STARTVERTEX and STOPVERTEX are
    // in this graph. If STARVERTEX == STOPVERTEX, returns true.
    public boolean pathExists(int startVertex, int stopVertex) {
        if (startVertex == stopVertex) {
            return true;
        }
        ArrayList<Integer> allPath = visitAll(startVertex);
        for (int i = 0; i < allPath.size(); i++) {
            if (allPath.get(i) == stopVertex) {
                return true;
            }
        }
        return false;
    }


    // Returns the path from startVertex to stopVertex.
    // If no path exists, returns an empty arrayList.
    // If startVertex == stopVertex, returns a one element arrayList.
    public ArrayList<Integer> path(int startVertex, int stopVertex) {
        ArrayList<Integer> paths = new ArrayList<Integer>();
        if (startVertex == stopVertex) {
            paths.add(startVertex);
            return paths;
        }
        if (!pathExists(startVertex, stopVertex)) {
            return paths;
        }
//        Iterator<Integer> iter = new DFSIterator(startVertex);
//        while (iter.hasNext()) {
//            int currVertex = iter.next();
//            if (currVertex == stopVertex) {
//                paths.add(currVertex);
//                break;
//            }
//            if (pathExists(currVertex, stopVertex)) {
//                paths.add(currVertex);
//            }
//        }
//        return paths;
        ArrayList<Integer> visited = new ArrayList<>();
        Iterator<Integer> iter = new DFSIterator(startVertex);
        while (iter.hasNext()) {
            int currVertex = iter.next();
            if (currVertex == stopVertex) {
                break;
            }
            visited.add(currVertex);
        }
        int tmpEnding = stopVertex;
        paths.add(tmpEnding);
        for(int i = visited.size() - 1; i >= 0;i--) {
            if(visited.get(i) == startVertex && isAdjacent(startVertex, tmpEnding)) {
                paths.add(visited.get(i));
                break;
            } else if(isAdjacent(visited.get(i), tmpEnding)) {
                paths.add(visited.get(i));
                tmpEnding = visited.get(i);
            }
        }
        Collections.reverse(paths);
        return paths;
        // you supply the body of this method
    }

    public ArrayList<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        // more instance variables go here
        private Integer[] inDegree;
        private HashSet<Integer> visited;

        public TopologicalIterator() {
            fringe = new Stack<Integer>();
            // more statements go here
            visited = new HashSet<Integer>();
            inDegree = new Integer[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                inDegree[i] = inDegree(i);
                if (inDegree(i) == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Integer next() {
            Integer poped = fringe.pop();
            for (Edge e : adjLists[poped]) {
                inDegree[e.to]--;
            }
            visited.add(poped);
            for (int i = 0; i < vertexCount; i++) {
                if (!visited.contains(i) && !fringe.contains(i) && inDegree[i] == 0) {
                    fringe.push(i);
                }
            }
            return poped;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    private class Edge {

        private Integer from;
        private Integer to;
        private Object edgeInfo;

        public Edge(int from, int to, Object info) {
            this.from = new Integer(from);
            this.to = new Integer(to);
            this.edgeInfo = info;
        }

        public Integer to() {
            return to;
        }

        public Object info() {
            return edgeInfo;
        }

        public String toString() {
            return "(" + from + "," + to + ",dist=" + edgeInfo + ")";
        }

    }

    public static void main(String[] args) {
        ArrayList<Integer> result;

        Graph g1 = new Graph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(0, 4);
        g1.addEdge(1, 2);
        g1.addEdge(2, 0);
        g1.addEdge(2, 3);
        g1.addEdge(4, 3);
        System.out.println("Traversal starting at 0");
        result = g1.visitAll(0);
        Iterator<Integer> iter;
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 2");
        result = g1.visitAll(2);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 3");
        result = g1.visitAll(3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Traversal starting at 4");
        result = g1.visitAll(4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 3");
        result = g1.path(0, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 0 to 4");
        result = g1.path(0, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 3");
        result = g1.path(1, 3);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 1 to 4");
        result = g1.path(1, 4);
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Path from 4 to 0");
        result = g1.path(4, 0);
        if (result.size() != 0) {
            System.out.println("*** should be no path!");
        }

        Graph g2 = new Graph(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(0, 4);
        g2.addEdge(1, 2);
        g2.addEdge(2, 3);
        g2.addEdge(4, 3);
        System.out.println();
        System.out.println();
        System.out.println("Topological sort");
        result = g2.topologicalSort();
        iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

}
