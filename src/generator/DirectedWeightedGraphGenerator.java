package generator;

import model.Digraph;
import model.Edge;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ens on 4/30/16.
 */
public class DirectedWeightedGraphGenerator {
    private static Random random;

    public DirectedWeightedGraphGenerator() {
        random = new Random();
    }

    //Generate a directed weighted graph(used capacity as a weight)
    public static Digraph generate(int numberOfVertices, int numberOfEdges, int maxCapacity) {
        if (numberOfEdges > (long) numberOfVertices * (numberOfVertices - 1) / 2) // Guards for creating meaningfull graph
            throw new IllegalArgumentException("Too many edges");
        if (numberOfEdges < 0) throw new IllegalArgumentException("Too few edges");
        Digraph G = new Digraph(numberOfVertices);
        Set<Edge> set = new TreeSet<>(); //
        int[] vertices = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            while (true) {
                int random = getRandom(numberOfVertices);
                int randomCapacity = getRandom(maxCapacity);
                Edge e = new Edge(i, random, randomCapacity);
                if (random != i && !set.contains(e)) {
                    G.addEdge(e);
                    break;
                }
            }
            vertices[i] = i;
        }
        shuffle(vertices); // to increase randomness shuffle vertices.
        while (G.getEdges().size() < numberOfEdges) {
            int v = getRandom(numberOfVertices);
            int w = getRandom(numberOfVertices);
            int s = vertices[v];
            int t = vertices[w];
            int randomCapacity = getRandom(maxCapacity);
            Edge e = new Edge(s, t, randomCapacity);
            Double thresh = new Double(numberOfEdges / (double) numberOfVertices) * 1.5;
            if ((s < t) && !set.contains(e) && G.getNode(s).getEdges().size() < thresh.intValue()) {
                set.add(e);
                G.addEdge(e);
            }
        }
        return G;
    }

    private static void shuffle(int[] vertices) {
        int N = vertices.length;
        for (int i = 0; i < N; i++) {
            int r = i + getRandom(N - i);
            int temp = vertices[i];
            vertices[i] = vertices[r];
            vertices[r] = temp;
        }
    }

    public static int getRandom(int n) {
        return random.nextInt(n);
    }
}
