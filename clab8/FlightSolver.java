import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private PriorityQueue<Flight> startQueue;
    private  PriorityQueue<Flight> endQueue;
    private Comparator<Flight> startComparator = (a, b) -> a.startTime - b.startTime;
    private Comparator<Flight> endComparator = (a, b) -> a.endTime - b.endTime;

    public FlightSolver(ArrayList<Flight> flights) {
        startQueue = new PriorityQueue<>(startComparator);
        endQueue = new PriorityQueue<>(endComparator);
        for (Flight flight : flights) {
            startQueue.add(flight);
            endQueue.add(flight);
        }
    }

    public int solve() {
        int max = 0;
        int sum = 0;
        int end = 0;
        Flight flight;
        while (!startQueue.isEmpty()) {
            flight = startQueue.poll();
            if (flight.startTime > end) {
                max = Math.max(max, sum);
                while (endQueue.peek().endTime < flight.startTime) {
                    sum -= endQueue.poll().passengers;
                }
                end = endQueue.peek().endTime;
            } else if (flight.endTime <= end) {
                end = flight.endTime;
            }
            sum += flight.passengers;
        }
        return Math.max(max, sum);
    }
}
