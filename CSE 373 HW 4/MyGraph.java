import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods
    // YOUR CODE HERE
    private static final Integer INFINITY = Integer.MAX_VALUE;
    private Map<Vertex, Collection<Edge>> myGraph;
    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
        myGraph = new HashMap<Vertex, Collection<Edge>>();
      
        Iterator<Vertex> vertices = v.iterator();
        while(vertices.hasNext()) {
            Vertex currV = vertices.next();
            if(!myGraph.containsKey(currV)){
                myGraph.put(currV, new ArrayList<Edge>());
            }
        }

        Iterator<Edge> edges = e.iterator();
        while(edges.hasNext()){
            Edge currE = edges.next();
            if(currE.getWeight() < 0){
                throw new IllegalArgumentException("Edge weight is negative");
            }
            Vertex currESrc = currE.getSource();
            Vertex currEDest = currE.getDestination();
            if(v.contains(currESrc) && v.contains(currEDest)){
                if(!myGraph.containsKey(currESrc)) {
                    throw new IllegalArgumentException("Vertex in edge is not valid");
                } else {
                Collection<Edge> outEdges = myGraph.get(currESrc);
                //System.out.println("The Source Vertex is " + currESrc.toString());

                    if(!outEdges.contains(currE)){
                        outEdges.add(currE);
                        //Debugging-print each outgoing edge of the given source node
                        /*Iterator<Edge> storedE = outEdges.iterator();
                        System.out.print("The stored edges at " + currESrc.toString() + " are: ");
                        while(storedE.hasNext()){
                            Edge edgeVal = storedE.next();

                            System.out.print(edgeVal.toString() + ", ");
                        }
                        System.out.println();*/
                    }
                }
            }
        }
    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    // WORKS
    public Collection<Vertex> vertices() {      
        return myGraph.keySet();
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
        Collection<Collection<Edge>> values = new ArrayList<Collection<Edge>>();
        values = myGraph.values();
        Collection<Edge> allValues = new ArrayList<Edge>();
        Iterator<Collection<Edge>> eachColl = values.iterator();
        while(eachColl.hasNext()){
            allValues.addAll(eachColl.next());
        }

        return allValues;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
        Collection<Vertex> adjVertices = new ArrayList<Vertex>();
        Iterator<Edge> edges = myGraph.get(v).iterator();
        while(edges.hasNext()) {
            adjVertices.add(edges.next().getDestination());
        }
        return adjVertices;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
        if (!myGraph.containsKey(b) || !myGraph.containsKey(a)) {
            throw new IllegalArgumentException("Edge weight is negative");
        }
        int cost = -1;
        if (adjacentVertices(a).contains(b)) {
            Iterator<Edge> edges = myGraph.get(a).iterator();
            while(edges.hasNext()){
                Edge currEdge = edges.next();
                if(currEdge.getDestination().equals(b)) {
                    cost =  currEdge.getWeight();
                }
            }
        }
        return cost;
    }

    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {
        // If a or b aren't present in the set of vertices throw an exception
        if (!myGraph.containsKey(b) || !myGraph.containsKey(a)) {
            throw new IllegalArgumentException("One of the vertices isn't valid");
        }
        /* Create a map of Vertices to VertexInfos. Fill it with VertexInfos for all
           vertices that have no previous vertex and and a cost of INFINITY */
        Map<Vertex, VertexInfo> vertInfos = new HashMap<Vertex, VertexInfo>();
        for (Vertex v : vertices()) {
            vertInfos.put(v, new VertexInfo(v, null, INFINITY));
        }
        /* Create a PriorityQueue for VertexInfos */
        PriorityQueue<VertexInfo> viQueue = new PriorityQueue<VertexInfo>();
        /* Create a VertexInfo for the start Vertex 'a' with a cost of 0 */
        VertexInfo vi_a = new VertexInfo(a, null, 0);
        /* Add VerxtexInfo for a to PQ and map it to it's VertexInfo */
        viQueue.add(vi_a);
        vertInfos.put(a, vi_a);
        while(!viQueue.isEmpty()) {
            /* Remove the VertexInfo with lowest cost */
            Vertex curr = viQueue.poll().getVertex();
            /* Check all adjacent Vertices of curr Vertex */
            for (Vertex v : adjacentVertices(curr)) {
                /* Calculate cost to get to v through curr */
                int cost = vertInfos.get(curr).getCost() + edgeCost(curr, v);
                /* If cost through curr is lower than previous */
                if (cost < vertInfos.get(v).getCost()) {
                    /* Remove v's VertexInfo from PQ */
                    viQueue.remove(vertInfos.get(v));
                    /* Overwrite previous value of v in map
                       Add updated VerexInfo to PQ */
                    VertexInfo vi = new VertexInfo(v, curr, cost);
                    vertInfos.put(v,vi);
                    viQueue.add(vi);
                }
            }
        }
        /* Create ArrayList for path */
        List<Vertex> path = new ArrayList<Vertex>();
        
        /* Add each vertex and it's previous vertex to path until a null vertex is reached */
        for (Vertex vert = b; vert != null; vert = vertInfos.get(vert).getPrev()) {
            path.add(vert);
        }

        /* Reverse order of path */ 
        Collections.reverse(path);
        /* Create new Path object with corresponding parameters */
        Path pathToB = new Path(path, vertInfos.get(b).getCost());
        return pathToB;
    }

    /* Class used to compare  Vertices in shortestPath which implements comparable */
    public class VertexInfo implements Comparable<VertexInfo> {
        /* Class contains current vertex it's previous vertex and a cost */
        private Vertex curr;
        private Vertex prev;
        private int cost;

        /* Constructor for VertexInfos */
        public VertexInfo(Vertex curr, Vertex prev, int cost) {
            this.curr = curr;
            this.prev = prev;
            this.cost = cost;
        }

        /* Comparing between two VertexInfos is done by computing the differnce
           in their costs*/
        public int compareTo(VertexInfo other) {
            return this.cost - other.getCost();
        }

        /* Returns current vertex */
        public Vertex getVertex() {
            return curr;
        }

        /* Returns previous vertex */
        public Vertex getPrev() {
            return prev;
        }

        /* Returns cost of vertex */
        public int getCost() {
            return cost;
        }
    }
}
