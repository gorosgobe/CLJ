package kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KMeans {

    private final int k;
    private final List<Cluster> clusters;
    private final List<DataPoint> points;

    public KMeans(int k, List<DataPoint> points, Initialiser initialiser) {

        if (k <= 0) {
            throw new IllegalArgumentException("K should be >= 0");
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

    private void recalculateCentroids() {
        for (Cluster c : clusters) {
            c.recalculateCentroid(points.get(0).getComponents().size());
        }
    }

    //assign each observation to cluster with nearest distance
    private void assignObservationsToCluster() {

        for (Cluster c : clusters) {
            c.getDataPoints().clear();
        }

        for (int i = 0; i < points.size(); i++) {
            assignObservationToCluster(points.get(i));
        }

    }

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

    private List<DataPoint> createInitialCentroids(Initialiser initialiser) {
        return initialiser.createInitialCentroids(k, points);
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public int getK() {
        return k;
    }

    public List<DataPoint> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return clusters.toString();
    }
}

