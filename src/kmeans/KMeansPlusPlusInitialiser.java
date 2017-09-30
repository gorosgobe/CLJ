package kmeans;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a centroid initialiser that uses the k-means++ algorithm.
 *
 * @author gorosgobe
 */
public class KMeansPlusPlusInitialiser implements Initialiser {
    /**
     * Creates the initial centroids using the k-means++ algorithm
     * @param k the number of clusters
     * @param points the points
     * @return the list of centroids
     */
    @Override
    public List<DataPoint> createInitialCentroids(int k, List<DataPoint> points) {
        long s1 = System.nanoTime();

        List<DataPoint> centroids = new ArrayList<>();

        Random generator = new Random();
        int randomInt = generator.nextInt(points.size());
        DataPoint point = points.get(randomInt);
        centroids.add(point);

        while (centroids.size() < k) {

            double random = Math.random();
            double cumulativeProbability = 0.0;

            double cost = points.parallelStream()
                    .map(p -> getMinimumDistanceSquared(p, centroids))
                    .reduce(0.0, (a, b) -> a + b);

            for (int i = 0; i < points.size(); i++) {

                double probability = getProbability(points.get(i), centroids, cost);

                cumulativeProbability += probability;

                if (random <= cumulativeProbability) {
                    centroids.add(points.get(i));
                    break;
                }
            }

        }

        return centroids;
    }

    /**
     * Computes the minimum distance squared from the point to the centroids
     * @param point the point
     * @param centroidsAvailable the centroids
     * @return the minimum distance squared from the point to the centroids
     */
    private static double getMinimumDistanceSquared(DataPoint point, List<DataPoint> centroidsAvailable) {

        double distance = Double.MAX_VALUE;

        for (int i = 0; i < centroidsAvailable.size(); i++) {

            double newDistance = point.distanceTo(centroidsAvailable.get(i));
            if (newDistance < distance) {
                distance = newDistance;
            }
        }

        return Math.pow(distance, 2.0);
    }

    /**
     * Gets the weighted probability (minimum square distance over the total cost) for a given point
     * @param point the point to get the probability of
     * @param centroidsAvailable the centroids
     * @param cost the cost (sum of all squared distances to the centroids of all the points)
     * @return the weighted probability
     */
    private static double getProbability(DataPoint point, List<DataPoint> centroidsAvailable, double cost) {

        double distance = getMinimumDistanceSquared(point, centroidsAvailable);

        return distance / cost;
    }
}
