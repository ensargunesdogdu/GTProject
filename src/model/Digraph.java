package model;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ens on 4/29/16.
 */
public class Digraph {
    private HashMap<Object, Node> nodes = new HashMap<Object, Node>();
    private LinkedList<Edge> edges = new LinkedList<Edge>();

    public Digraph(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            nodes.put(i, new Node());
        }
    }

    public Node getNode(Object nodeID) {
        return this.nodes.get(nodeID);
    }

    public LinkedList<Edge> getEdges() {
        return this.edges;
    }

    @Override
    public String toString() {
        return "Digraph{" +
                "Number of Nodes=" + nodes.size() +
                ", Number of Edges=" + edges.size() +
                '}';
    }

    public void addEdge(Edge edge) {
        Node startNode;
        Node endNode;
        Object sourceId = edge.getSource();
        Object targetId = edge.getTarget();
        if (!this.nodes.containsKey(sourceId)) {
            startNode = new Node();
            this.nodes.put(sourceId, startNode);
        } else {
            startNode = this.nodes.get(sourceId);
        }
        if (!this.nodes.containsKey(targetId)) {
            endNode = new Node();
            this.nodes.put(targetId, endNode);
        } else {
            endNode = this.nodes.get(targetId);
        }
        startNode.addEdge(edge);
//        endNode.addEdge(edge);
        this.edges.add(edge);
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public int[][] getCapacityMatrix() {
        int[][] capacityMatrix = new int[getNodeCount()][getNodeCount()];
        for (int i = 0; i < nodes.size(); i++) {
            Node source = nodes.get(i);
            for (Edge edge : source.getEdges()) {
                capacityMatrix[i][edge.getTargetInt()] = edge.getCapacity();
            }
        }
        return capacityMatrix;
    }

    public HashMap<Object, Node> getNodes() {
        return nodes;
    }
}
