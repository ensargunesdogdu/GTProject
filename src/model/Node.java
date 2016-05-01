package model;

import java.util.ArrayList;

/**
 * Created by ens on 4/29/16.
 */
public class Node {

	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public Edge getEdge(int number) {
		if (this.edges.size() <= number) {
			return null;
		} else {
			return this.edges.get(number);
		}
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	public int getOutLeadingOrder() {
		return this.edges.size();
	}

}
