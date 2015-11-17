import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
    // you will need some private fields to represent the graph
    // you are also likely to want some private helper methods
    // YOUR CODE HERE
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
               if(!outEdges.contains(currE)){
                  Iterator<Edge> storedE = outEdges.iterator();
                  while(storedE.hasNext()){
                     Edge edgeVal = storedE.next();
                     if(!currE.equals(edgeVal)){
                        outEdges.add(currE);
                     }
                  }
               }
            }
         }
      }
    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
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
      Collection<Vertex> adjVerticesA = adjacentVertices(a);
      int cost = -1;
      if (adjVerticesA.contains(b)) {
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

	// YOUR CODE HERE (you might comment this out this method while doing Part 1)
      return null;
    }

}
