package flowalgorithms;

import model.Digraph;
import model.Edge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by ens on 5/1/16.
 */


public class EdmondsKarp {
    public static int getMaxFlow(Digraph g, int s, int t) {
        int n = g.getNodeCount();
        int[][] F = new int[n][n];// Residual capacity from u to v is C[u][v] - F[u][v]
        int[][] C = g.getCapacityMatrix();
        while (true) {
            int[] P = new int[n]; // Parent table
            Arrays.fill(P, -1);
            P[s] = s;
            int[] M = new int[n]; // Capacity of path to node
            M[s] = Integer.MAX_VALUE;
            // BFS queue
            Queue<Integer> Q = new LinkedList<Integer>();
            Q.offer(s);
            LOOP:
            while (!Q.isEmpty()) {
                int u = Q.poll();
                for (Edge vEdge : g.getNode(u).getEdges()) {
                    int v = vEdge.getTargetInt();
                    // There is available capacity,
                    // and v is not seen before in search
                    if (C[u][v] - F[u][v] > 0 && P[v] == -1) {
                        P[v] = u;
                        M[v] = Math.min(M[u], C[u][v] - F[u][v]);
                        if (v != t)
                            Q.offer(v);
                        else {
                            // Backtrack search, and write flow
                            while (P[v] != v) {
                                u = P[v];
                                F[u][v] += M[t];
                                F[v][u] -= M[t];
                                v = u;
                            }
                            break LOOP;
                        }
                    }
                }
            }
            if (P[t] == -1) { // We did not find a path to t
                int sum = 0;
                for (int x : F[s])
                    sum += x;
                return sum;
            }
        }
    }
}