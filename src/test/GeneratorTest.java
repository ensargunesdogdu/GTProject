package test;

import generator.DirectedWeightedGraphGenerator;
import model.Digraph;

/**
 * Created by ens on 5/1/16.
 */
public class GeneratorTest {
    public static void main(String[] args) {
        DirectedWeightedGraphGenerator generator = new DirectedWeightedGraphGenerator();
        Digraph digraph = generator.generate(10, 15, 100);
        System.out.println("digraph.toString() = " + digraph.toString());
    }
}
