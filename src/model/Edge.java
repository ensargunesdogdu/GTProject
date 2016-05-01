package model;

/**
 * Created by ens on 4/29/16.
 */
public class Edge implements Comparable<Edge> {

    private final Object target;
    private final Object source;
    private final int capacity;

    public Edge(int source, int target, int capacity) {
        this.capacity = capacity;
        this.target = target;
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public Object getSource() {
        return source;
    }

    public int getTargetInt() {
        return ((Integer) target);
    }
    public int getSourceInt() {
        return ((Integer) source);
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return this.source + "->" + this.target + "(" + this.capacity + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (target != edge.target) return false;
        if (source != edge.source) return false;
        return capacity == edge.capacity;

    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + capacity;
        return result;
    }

    @Override
    public int compareTo(Edge o) {
        return equals(o) ? 0 : -1;
    }
}
