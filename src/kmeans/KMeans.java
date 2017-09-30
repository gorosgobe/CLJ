package kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing k-means clustering
 *
 * @author gorosgobe
 */
public class KMeans {

    /** The number of clusters*/
    private final int k;
    /** The list of clusters*/
    private final List<Cluster> clusters;
    /** The list of initial points*/
    private final List<DataPoint> points;

    /**
     * Creates a K-means object and runs the k-means algorithm.
     * @param k the number of clusters
     * @param points the points to cluster
     * @param initialiser the initialiser of the centroids
     */
    public KMeans(int k, List<DataPoint> points, Initialiser initialiser) {

        if (k < 2) {
            throw new IllegalArgumentException("K should be >= 2. Actual value: " + k);
        }

        this.k = k;
        this.clusters = new ArrayList<>();
        this.points = points;

        List<DataPoint> initialCentroids = createInitialCentroids(initialiser);

        //create clusters
        for (int i = 0; i < k; i++) {
            Cluster c = new Cluster(initialCentroids.get(i));
            clusters.add(c);
        }

        assignObservationsToCluster();

        boolean hasNotConverged = true;

        while (hasNotConverged) {

            List<Set<DataPoint>> datapointsPerClusterBefore = getCopyOfDatapointsPerCluster();

            recalculateCentroids();
            assignObservationsToCluster();

            List<Set<DataPoint>> datapointsPerClusterAfter = getCopyOfDatapointsPerCluster();

            if (datapointsPerClusterBefore.equals(datapointsPerClusterAfter)) {
                hasNotConverged = false;
            }

        }
    }

    /**
     * Returns a copy of all the data points held by the clusters, given as a list of sets of points
     * @return a copy of all the data points held by the clusters, given as a list of sets of points.
     */
    private List<Set<DataPoint>> getCopyOfDatapointsPerCluster() {

        List<Set<DataPoint>> result = new ArrayList<>();

        for (int i = 0; i < clusters.size(); i++) {
            Set<DataPoint> dataPoints = new HashSet<>();
            for (DataPoint p : clusters.get(i).getDataPoints()) {
                dataPoints.add(p.copy());
            }
            result.add(dataPoints);
        }

        return result;
    }

    /**
     * Recalculates the centroids
     */
    private void recalculateCentroids() {
        for (Cluster c : clusters) {
            c.recalculateCentroid(points.get(0).getComponents().size());
        }
    }

    /**
     * Assigns each point to a cluster
     */
    //assign each observation to cluster with nearest distance
    private void assignObservationsToCluster() {

        for (Cluster c : clusters) {
            c.getDataPoints().clear();
        }

        for (int i = 0; i < points.size(); i++) {
            assignObservationToCluster(points.get(i));
        }

    }

    /**
     * Assigns a point to a cluster
     * @param point the point to be assigned to a cluster
     */
    private void assignObservationToCluster(DataPoint point) {

        Cluster c = clusters.get(0);
        double distance = point.distanceTo(c.getCentroid());

        for (int j = 0; j < clusters.size(); j++) {
            double newDistance = point.distanceTo(clusters.get(j).getCentroid());
            if (newDistance < distance) {
                distance = newDistance;
                c = clusters.get(j);
            }
        }

        c.addDataPointToCluster(point);
    }

    /**
     * Creates the initial centroids using the supplied initialiser
     * @param initialiser the initialiser of the centroids
     * @return the list of initial centroids
     */
    private List<DataPoint> createInitialCentroids(Initialiser initialiser) {
        return initialiser.createInitialCentroids(k, points);
    }

    /**
     * Gets the clusters
     * @return the list of clusters
     */
    public List<Cluster> getClusters() {
        return clusters;
    }

    /**
     * Gets the number of clusters
     * @return the number of clusters
     */
    public int getK() {
        return k;
    }

    /**
     * Gets the points
     * @return the list of points
     */
    public List<DataPoint> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return clusters.toString();
    }
}

