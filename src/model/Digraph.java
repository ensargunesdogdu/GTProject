package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ens on 4/29/16.
 */
public class Digraph {
    private HashMap<Object, Node> nodes = new HashMap<>();
    private LinkedList<Edge> edges = new LinkedList<>();

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
        Object sourceId = edge.getSource();
        if (!this.nodes.containsKey(sourceId)) {
            startNode = new Node();
            this.nodes.put(sourceId, startNode);
        } else {
            startNode = this.nodes.get(sourceId);
        }
        startNode.addEdge(edge);
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

    public List<RevEdge>[] getGraphAsBasicArrayWithRevEdges() {
        List<RevEdge>[] graph = new List[getNodeCount()];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < getNodes().size(); i++) {
            Node node = getNodes().get(i);
            for (model.Edge edge : node.getEdges()) {
                addBasicRevEdge(graph, edge.getSourceInt(), edge.getTargetInt(), edge.getCapacity());
            }
        }
        return graph;
    }

    public static void addBasicRevEdge(List<RevEdge>[] graph, int s, int t, int cap) {
        graph[s].add(new RevEdge(t, graph[t].size(), cap));
        graph[t].add(new RevEdge(s, graph[s].size() - 1, 0));
    }
}
