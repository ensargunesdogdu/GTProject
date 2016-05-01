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

    public static Digraph generate(int numberOfVertices, int numberOfEdges, int maxCapacity) {
        if (numberOfEdges > (long) numberOfVertices * (numberOfVertices - 1) / 2)
            throw new IllegalArgumentException("Too many edges");
        if (numberOfEdges < 0) throw new IllegalArgumentException("Too few edges");
        Digraph G = new Digraph(numberOfVertices);
        Set<Edge> set = new TreeSet<Edge>();
        int[] vertices = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++)
            vertices[i] = i;
        shuffle(vertices);
        while (G.getEdges().size() < numberOfEdges) {
            int v = uniform(numberOfVertices);
            int w = uniform(numberOfVertices);
            int randomCapacity = random.nextInt(maxCapacity);
            Edge e = new Edge(v, w, randomCapacity);
            if ((v < w) && !set.contains(e)) {
                set.add(e);
                G.addEdge(e);
            }
        }
        return G;
    }

    private static void shuffle(int[] vertices) {
        if (vertices == null) throw new NullPointerException("argument array is null");
        int N = vertices.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            int temp = vertices[i];
            vertices[i] = vertices[r];
            vertices[r] = temp;
        }
    }

    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("Parameter N must be positive");
        return random.nextInt(n);
    }
}
