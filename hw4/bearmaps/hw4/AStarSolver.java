package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import javax.xml.transform.SourceLocator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome; // one of {SOLVED, TIMEOUT, UNSOLVABLE}
    private List<Vertex> solution; // A list of vertices that are in the shortest path from start to end.
    private double solutionWeight; // The sum of weights of edges of the shortest path.
    private int numStatesExplored; // The number of visited vertices (i.e. dequeued vertices)
    private double timeSpent;      // Time (in second) used for this constructor

    private ArrayHeapMinPQ<Vertex> fringe; // An extrinsicPQ that stores all the vertices that will be explored next.
    private HashMap<Vertex, Double> distTo; // Current shortest distance to the vertices.
    private HashMap<Vertex, Vertex> edgeTo; // a pair (v1, v2) edgeTo means the predecessor of v1 is v2 (i.e. v2 has an edge to v1).

    private final double INFINITY =  Double.MAX_VALUE;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        outcome = SolverOutcome.UNSOLVABLE;
        solution = new ArrayList<>();
        solutionWeight = 0.;
        numStatesExplored = 0;
        timeSpent = 0;

        fringe = new ArrayHeapMinPQ();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        Stopwatch sw = new Stopwatch();

        // Set start vertex's distTo (to 0) and insert start to PQ
        distTo.put(start, 0.);
        fringe.add(start, distTo(start) + input.estimatedDistanceToGoal(start, end));

        // Keep exploring nodes until PQ is empty
        while (fringe.size() != 0) {
            // Explore the best vertex and add it to our solution.
            Vertex current = fringe.removeSmallest();
            numStatesExplored += 1;

            if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution = new ArrayList<>(); // an empty solution
                solutionWeight = 0;
                timeSpent = sw.elapsedTime();
                return;
            } else if (current.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solution = findSolutionFromEdgeTo(edgeTo, start, end);
                solutionWeight = distTo(end);
                timeSpent = sw.elapsedTime();
                return;
            } else {
                for (WeightedEdge<Vertex> e : input.neighbors(current)) {
                    relax(input, end, e);
                }
            }
        }

        // Have visited every reachable vertex from start but still didn't find end
        outcome = SolverOutcome.UNSOLVABLE;
        solution = new ArrayList<>(); // an empty solution
        solutionWeight = 0;
        timeSpent = sw.elapsedTime();
    }

    // Our current shortest distance to vertex v. Infinity if v cannot be reached from start vertex so far.
    private double distTo(Vertex v) {
        return distTo.getOrDefault(v, INFINITY);
    }

    // Edge relaxation: when  we explore a new vertex p, we will iterate over all its out going edges.
    // For each one of them e and the vertex q it points to, if distTo[p] + w < distTo[q], then change
    // q's distTo, edgeTo, and q's priority in the PQ.
    private void relax(AStarGraph<Vertex> input, Vertex goal,  WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (distTo(p) + w < distTo(q)) {
            distTo.put(q, distTo(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo(q) + input.estimatedDistanceToGoal(q, goal));
            } else {
                fringe.add(q, distTo(q) + input.estimatedDistanceToGoal(q, goal));
            }
        }
    }

    // Find the shortest path from start to end by tracing edgeTo backwards.
    private List<Vertex> findSolutionFromEdgeTo(HashMap<Vertex, Vertex> edgeTo, Vertex start, Vertex end) {
        LinkedList<Vertex> result = new LinkedList<>();
        // Vertex start shouldn't be put into edgeTo.
        while (edgeTo.containsKey(end)) {
            result.addFirst(end);
            end = edgeTo.get(end);
        }
        // Add vertex start in the front.
        result.addFirst(start);
        return result;
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return timeSpent;
    }
}
