import edu.princeton.cs.algs4.LSD;

import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    List<Bear> solvedBears;
    List<Bed> solvedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        Pair<List<Bear>, List<Bed>> sorted = quickSort(bears, beds);
        solvedBears = sorted.first();
        solvedBeds = sorted.second();
    }

    private Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears.size() <= 1) {
            return new Pair<>(bears, beds);
        }
        // partition bears
        Bed bedPivot = beds.get(0);
        List<Bear> lessBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();
        Bear bearPivot = partitionBears(bears, bedPivot, lessBear, greaterBear);

        // partition beds
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();
        partitionBeds(beds, bearPivot, lessBed, greaterBed);

        // sort and catenate
        Pair<List<Bear>, List<Bed>> less = quickSort(lessBear, lessBed);
        Pair<List<Bear>, List<Bed>> greater = quickSort(greaterBear, greaterBed);
        List<Bear> sortedBear = catenate(less.first(), bearPivot, greater.first());
        List<Bed> sortedBed = catenate(less.second(), bedPivot, greater.second());
        return new Pair<>(sortedBear, sortedBed);
    }

    private Bear partitionBears(List<Bear> bears, Bed pivot, List<Bear> less, List<Bear> greater) {
        Bear equal = null;
        for (Bear bear : bears) {
            int compare = bear.compareTo(pivot);
            if (compare < 0) {
                less.add(bear);
            } else if (compare > 0) {
                greater.add(bear);
            } else {
                equal = bear;
            }
        }
        return equal;
    }

    private Bed partitionBeds(List<Bed> beds, Bear pivot, List<Bed> less, List<Bed> greater) {
        Bed equal = null;
        for (Bed bed : beds) {
            int compare = bed.compareTo(pivot);
            if (compare < 0) {
                less.add(bed);
            } else if (compare > 0) {
                greater.add(bed);
            } else {
                equal = bed;
            }
        }
        return equal;
    }

    private <Item> List<Item> catenate(List<Item> less, Item equal, List<Item> greater) {
        List<Item> result = new ArrayList<>(less.size() + 1 + greater.size());
        for (Item i : less) {
            result.add(i);
        }
        result.add(equal);
        for (Item i : greater) {
            result.add(i);
        }
        return result;
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }
}
