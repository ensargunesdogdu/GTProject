package flowalgorithms;

import model.Digraph;
import model.Edge;
import model.Node;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ens on 5/1/16.
 */
public class FordFulkerson {
    public static int getMaxFlow(Digraph g, Object source, Object sink) {
        HashMap<Edge, Integer> flowMap = getMaxFlowMap(g, source, sink);
        int total = 0;
        for (Edge edge : flowMap.keySet()) {
            if (edge.getTarget().equals(sink))
                total += flowMap.get(edge);
        }
        return total;
    }

    public static HashMap<Edge, Integer> getMaxFlowMap(Digraph digraph, Object source, Object sink) {
        LinkedList<Edge> path;
        HashMap<Edge, Integer> flow = new HashMap<>();
        for (Edge e : digraph.getEdges()) {
            flow.put(e, 0);
        }
        while ((path = breadFirstSearch(digraph, source, sink, flow)) != null) {
            int minCapacity = Integer.MAX_VALUE;
            Object lastNode = source;
            for (Edge edge : path) {
                int c;
                if (edge.getSource().equals(lastNode)) {
                    c = edge.getCapacity() - flow.get(edge);
                    lastNode = edge.getTarget();
                } else {
                    c = flow.get(edge);
                    lastNode = edge.getSource();
                }
                if (c < minCapacity) {
                    minCapacity = c;
                }
            }

            // Change flow of all edges of the path by the value calculated
            // above.
            lastNode = source;
            for (Edge edge : path) {
                if (edge.getSource().equals(lastNode)) {
                    flow.put(edge, flow.get(edge) + minCapacity);
                    lastNode = edge.getTarget();
                } else {
                    flow.put(edge, flow.get(edge) - minCapacity);
                    lastNode = edge.getSource();
                }
            }
        }
        return flow;
    }

    static LinkedList<Edge> breadFirstSearch(Digraph digraph, Object start, Object target,
                                             HashMap<Edge, Integer> flow) {
        HashMap<Object, Edge> parent = new HashMap<>();
        LinkedList<Object> fringe = new LinkedList<>();
        parent.put(start, null);
        fringe.add(start);
        all:
        while (!fringe.isEmpty()) {
            LinkedList<Object> newFringe = new LinkedList<>();
            // Iterate through all nodes in the fringe.
            for (Object nodeID : fringe) {
                Node node = digraph.getNode(nodeID);
                // Iterate through all the edges of the node.
                for (int i = 0; i < node.getOutLeadingOrder(); i++) {
                    Edge e = node.getEdge(i);
                    if (e.getSource().equals(nodeID)
                            && !parent.containsKey(e.getTarget())
                            && flow.get(e) < e.getCapacity()) {
                        parent.put(e.getTarget(), e);
                        if (e.getTarget().equals(target)) {
                            break all;
                        }
                        newFringe.add(e.getTarget());
                    } else if (e.getTarget().equals(nodeID)
                            && !parent.containsKey(e.getSource())
                            && flow.get(e) > 0) {
                        parent.put(e.getSource(), e);
                        if (e.getSource().equals(target)) {
                            break all;
                        }
                        newFringe.add(e.getSource());
                    }
                }
            }
            fringe = newFringe;
        }

        // No Result :(
        if (fringe.isEmpty()) {
            return null;
        }
        // Path found return it
        Object node = target;
        LinkedList<Edge> path = new LinkedList<>();
        while (!node.equals(start)) {
            Edge e = parent.get(node);
            path.addFirst(e);
            if (e.getSource().equals(node)) {
                node = e.getTarget();
            } else {
                node = e.getSource();
            }
        }
        return path;
    }
}
