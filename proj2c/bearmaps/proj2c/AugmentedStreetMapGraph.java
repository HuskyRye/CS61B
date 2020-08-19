package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private HashMap<Point, Node> pointToNode;
    private PointSet pointSet;

    private TrieSet trie;
    private HashMap<String, Set<String>> cleanedNameToUncleanedNames;
    private HashMap<String, List<Node>> uncleanedNameToLocations;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();
        pointToNode = new HashMap<>();

        trie = new TrieSet();
        cleanedNameToUncleanedNames = new HashMap<>();
        uncleanedNameToLocations = new HashMap<>();

        for (Node node : nodes) {
            if (neighbors(node.id()).size() != 0) {
                Point p = new Point(node.lon(), node.lat());
                pointToNode.put(p, node);
                points.add(p);
            }

            String uncleanedName = name(node.id());
            if (uncleanedName != null) {
                String cleanedName = cleanString(uncleanedName);
                if (uncleanedName.equals("Montano Velo")) {
                    System.out.println(cleanedName);
                }
                trie.add(cleanedName);

                if (!cleanedNameToUncleanedNames.containsKey(cleanedName)) {
                    cleanedNameToUncleanedNames.put(cleanedName, new HashSet<>());
                }
                cleanedNameToUncleanedNames.get(cleanedName).add(uncleanedName);

                if (!uncleanedNameToLocations.containsKey(uncleanedName)) {
                    uncleanedNameToLocations.put(uncleanedName, new LinkedList<>());
                }
                uncleanedNameToLocations.get(uncleanedName).add(node);
            }
        }
        pointSet = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearest = pointSet.nearest(lon, lat);
        return pointToNode.get(nearest).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanedNamesWithPrefix = trie.keysWithPrefix(cleanString(prefix));
        List<String> locationsByPrefix = new LinkedList<>();
        for (String cleanedName : cleanedNamesWithPrefix) {
            Set<String> uncleanedNames = cleanedNameToUncleanedNames.get(cleanedName);
            for (String uncleanedName : uncleanedNames) {
                locationsByPrefix.add(uncleanedName);
            }
        }
        return locationsByPrefix;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        Set<String> uncleanedNames = cleanedNameToUncleanedNames.get(cleanString(locationName));
        List<Map<String, Object>> locations = new LinkedList<>();
        for (String uncleanedName : uncleanedNames) {
            List<Node> nodes = uncleanedNameToLocations.get(uncleanedName);
            for (Node node : nodes) {
                Map<String, Object> location = new HashMap<>();
                location.put("lat", node.lat());
                location.put("lon", node.lon());
                location.put("name", node.name());
                location.put("id", node.id());
                locations.add(location);
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
