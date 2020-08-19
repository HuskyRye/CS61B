package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq.add(start, 0.0);
        distTo.put(start, 0.0);
        numStatesExplored = 0;
        while (pq.size() != 0) {
            Vertex p = pq.removeSmallest();
            if (p.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(end);
                solution = new ArrayList<Vertex>();
                while (p != start) {
                    solution.add(p);
                    p = edgeTo.get(p);
                }
                solution.add(p);
                Collections.reverse(solution);
                timeSpent = sw.elapsedTime();
                return;
            }
            if (sw.elapsedTime() > timeout) {
                solution = new ArrayList<Vertex>();
                solutionWeight = 0.0;
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = sw.elapsedTime();
                return;
            }
            numStatesExplored++;
            for (WeightedEdge<Vertex> edge : input.neighbors(p)) {
                Vertex q = edge.to();
                double weight = edge.weight();
                if (!distTo.containsKey(q) || distTo.get(p) + weight < distTo.get(q)) {
                    distTo.put(q, distTo.get(p) + weight);
                    edgeTo.put(q, p);
                    if (!pq.contains(q)) {
                        pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
        }
        solution = new ArrayList<Vertex>();
        solutionWeight = 0.0;
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
        return;
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
