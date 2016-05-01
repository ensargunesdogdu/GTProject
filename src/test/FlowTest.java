package test;

import flowalgorithms.Dinic;
import flowalgorithms.EdmondsKarp;
import flowalgorithms.FordFulkerson;
import generator.DirectedWeightedGraphGenerator;
import model.Digraph;
import model.Edge;

/**
 * Created by ens on 5/1/16.
 */
public class FlowTest {
    public static void main(String[] args) {
        preDefinedTest1();
        preDefinedTest2();
        preDefinedTest3();
        generatedTest();
    }

    private static void testAll(Digraph digraph, int source, int destination) {
        int ford = FordFulkerson.getMaxFlow(digraph, source, destination);
        int edmondsKarp = EdmondsKarp.getMaxFlow(digraph, source, destination);
        int dinic = Dinic.getMaxFlow(digraph, source, destination);
        System.out.println("Ford Max Flow = " + ford);
        System.out.println("Edmonds Karp Max Flow= " + edmondsKarp);
        System.out.println("Dinic Max Flow = " + dinic);
    }

    private static void preDefinedTest1() {
        Digraph digraph = new Digraph(3);
        digraph.addEdge(new Edge(0, 1, 3));
        digraph.addEdge(new Edge(0, 2, 2));
        digraph.addEdge(new Edge(1, 2, 2));
        testAll(digraph, 0, 2);
    }


    private static void preDefinedTest2() {
        Digraph digraph = new Digraph(6);
        digraph.addEdge(new Edge(0, 1, 16));
        digraph.addEdge(new Edge(0, 2, 13));
        digraph.addEdge(new Edge(1, 3, 12));
        digraph.addEdge(new Edge(1, 2, 10));
        digraph.addEdge(new Edge(2, 1, 4));
        digraph.addEdge(new Edge(3, 5, 20));
        digraph.addEdge(new Edge(3, 2, 9));
        digraph.addEdge(new Edge(2, 4, 14));
        digraph.addEdge(new Edge(4, 3, 7));
        digraph.addEdge(new Edge(4, 5, 4));
        testAll(digraph, 0, 5);
    }

    private static void preDefinedTest3() {
        Digraph digraph = new Digraph(7);
        digraph.addEdge(new Edge(0, 1, 2));
        digraph.addEdge(new Edge(0, 2, 3));
        digraph.addEdge(new Edge(1, 3, 2));
        digraph.addEdge(new Edge(1, 4, 1));
        digraph.addEdge(new Edge(2, 3, 2));
        digraph.addEdge(new Edge(2, 5, 2));
        digraph.addEdge(new Edge(3, 4, 2));
        digraph.addEdge(new Edge(3, 5, 4));
        digraph.addEdge(new Edge(3, 6, 2));
        digraph.addEdge(new Edge(4, 6, 2));
        digraph.addEdge(new Edge(5, 6, 1));
        testAll(digraph, 0, 6);
    }

    private static void generatedTest() {
        DirectedWeightedGraphGenerator generator = new DirectedWeightedGraphGenerator();
        Digraph digraph = generator.generate(5, 7, 5);
        testAll(digraph, 0, 4);
    }
}
