import java.util.*;
import java.io.*;

/**
 * Driver program that reads in a graph and prompts user for shortests paths in the graph.
 * (Intentionally without comments.  Read through the code to understand what it does.)
 */

public class FindPaths {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
			System.exit(1);
		}

		MyGraph g = readGraph(args[0],args[1]);

		Scanner console = new Scanner(System.in);
		Collection<Vertex> v = g.vertices();
        Collection<Edge> e = g.edges();
		System.out.println("Vertices are " + v);
		System.out.println("Edges are " + e);
		System.out.println("There are " + e.size() + " edges.");
		while(true) {
			/*
			System.out.print("Start vertex? ");
			Vertex a = new Vertex(console.nextLine());
			if(!v.contains(a)) {
				System.out.println("no such vertex");
				System.exit(0);
			}
			
			System.out.print("Destination vertex? ");
			Vertex b = new Vertex(console.nextLine());
			if(!v.contains(b)) {
				System.out.println("no such vertex");
				System.exit(1);
			}

			// YOUR CODE HERE: call shortestPath and print
			// out the result

			//Used to test adjacentVertices and edgeCost
			
			System.out.print("Adjacent vertices to a vertex? ");
			Vertex c = new Vertex(console.nextLine());
			if(!v.contains(c)) {
				System.out.println("no such vertex");
				System.exit(1);
			} else {
				System.out.println("Adjacent vertices are: " + g.adjacentVertices(c));
			}

			System.out.println("Check cost of edge. ");
			System.out.print("Start of edge: ");
			Vertex d = new Vertex(console.nextLine());
			System.out.print("End of edge: ");
			Vertex f = new Vertex(console.nextLine());
			if(!v.contains(d)) {
				System.out.println("no such vertex");
				System.exit(1);
			} else {
				System.out.println("Cost of edge is: " + g.edgeCost(d, f));
			}
			*/
			System.out.println("Lets find the shortest path...");
			System.out.print("Start of path: ");
			Vertex a = new Vertex(console.nextLine());
			if(!v.contains(a)) {
				System.out.println("no such vertex");
				System.exit(0);
			}
			System.out.print("End of path: ");
			Vertex b = new Vertex(console.nextLine());
			if(!v.contains(b)) {
				System.out.println("no such vertex");
				System.exit(0);
			}

			Path aToB = g.shortestPath(a, b);
			System.out.println();
			System.out.println("Shortest path is: " + aToB.vertices);
			System.out.println("Cost is: " + aToB.cost);
			System.out.println();

		}
	}

	public static MyGraph readGraph(String f1, String f2) {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f1);
			System.exit(2);
		}

		Collection<Vertex> v = new ArrayList<Vertex>();
		while(s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		while(s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a,b,w));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(v,e);
	}
}
