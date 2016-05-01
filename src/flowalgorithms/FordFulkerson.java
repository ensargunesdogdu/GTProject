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
            if(edge.getTarget().equals(sink))
                total+=flowMap.get(edge);
        }
        return total;
    }

    public static HashMap<Edge, Integer> getMaxFlowMap(Digraph g, Object source, Object sink) {
        // The path from source to sink that is found in each iteration
        LinkedList<Edge> path;
        // The flow, i.e. the capacity of each edge that is actually used
        HashMap<Edge, Integer> flow = new HashMap<Edge, Integer>();
        // Create initial empty flow.
        for (Edge e : g.getEdges()) {
            flow.put(e, 0);
        }

        // The Algorithm itself
        while ((path = bfs(g, source, sink, flow)) != null) {
            // Activating this output will illustrate how the algorithm works
            // System.out.println(path);
            // Find out the flow that can be sent on the found path.
            int minCapacity = Integer.MAX_VALUE;
            Object lastNode = source;
            for (Edge edge : path) {
                int c;
                // Although the edges are directed they can be used in both
                // directions if the capacity is partially used, so this if
                // statement is necessary to find out the edge's actual
                // direction.
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
                // If statement like above
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

    static LinkedList<Edge> bfs(Digraph g, Object start, Object target,
                                HashMap<Edge, Integer> flow) {
        // The edge by which a node was reached.
        HashMap<Object, Edge> parent = new HashMap<Object, Edge>();
        // All outer nodes of the current search iteration.
        LinkedList<Object> fringe = new LinkedList<Object>();
        // We need to put the start node into those two.
        parent.put(start, null);
        fringe.add(start);
        // The actual algorithm
        all:
        while (!fringe.isEmpty()) {
            // This variable is needed to prevent the JVM from having a
            // concurrent modification
            LinkedList<Object> newFringe = new LinkedList<Object>();
            // Iterate through all nodes in the fringe.
            for (Object nodeID : fringe) {
                Node node = g.getNode(nodeID);
                // Iterate through all the edges of the node.
                for (int i = 0; i < node.getOutLeadingOrder(); i++) {
                    Edge e = node.getEdge(i);
                    // Only add the node if the flow can be changed in an out
                    // leading direction. Also break, if the target is reached.
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
            // Replace the fringe by the new one.
            fringe = newFringe;
        }

        // Return null, if no path was found.
        if (fringe.isEmpty()) {
            return null;
        }
        // If a path was found, reconstruct it.
        Object node = target;
        LinkedList<Edge> path = new LinkedList<Edge>();
        while (!node.equals(start)) {
            Edge e = parent.get(node);
            path.addFirst(e);
            if (e.getSource().equals(node)) {
                node = e.getTarget();
            } else {
                node = e.getSource();
            }
        }

        // Return the path.
        return path;
    }
}
