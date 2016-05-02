package flowalgorithms;

import model.Digraph;
import model.RevEdge;

import java.util.Arrays;
import java.util.List;

public class Dinic {

    public static int getMaxFlow(Digraph digraph, int source, int sink) {
        int flow = 0;
        int[] dist = new int[digraph.getEdges().size()];
        List<RevEdge>[] graph = digraph.getGraphAsBasicArrayWithRevEdges();
        while (dinicBreathFirstSearch(graph, source, sink, dist)) {
            int[] ptr = new int[graph.length];
            while (true) {
                int deepFirst = dinicDeepFirstSearch(graph, ptr, dist, sink, source, Integer.MAX_VALUE);
                if (deepFirst == 0)
                    break;
                flow += deepFirst;
            }
        }
        return flow;
    }

    static boolean dinicBreathFirstSearch(List<RevEdge>[] digraph, int source, int sink, int[] dist) {
        Arrays.fill(dist, -1);
        dist[source] = 0;
        int[] Q = new int[digraph.length];
        int sizeQ = 0;
        Q[sizeQ++] = source;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (RevEdge e : digraph[u]) {
                if (dist[e.t] < 0 && e.f < e.cap) {
                    dist[e.t] = dist[u] + 1;
                    Q[sizeQ++] = e.t;
                }
            }
        }
        return dist[sink] >= 0;
    }

    static int dinicDeepFirstSearch(List<RevEdge>[] digraph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < digraph[u].size(); ++ptr[u]) {
            RevEdge e = digraph[u].get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDeepFirstSearch(digraph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    digraph[e.t].get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }
}