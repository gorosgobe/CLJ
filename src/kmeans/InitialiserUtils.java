package kmeans;

import java.util.List;

/**
 * Class holding some utils for the initialisers.
 *
 * @author gorosgobe
 */
public class InitialiserUtils {

    private InitialiserUtils() {
    }

    /**
     * Computes the minimum distance squared from the point to the centroids
     * @param point the point
     * @param centroidsAvailable the centroids
     * @return the minimum distance squared from the point to the centroids
     */
    public static double getMinimumDistanceSquared(DataPoint point, List<DataPoint> centroidsAvailable) {

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
     * Computes index of the point with the minimum distance squared from the centroids
     * @param point the point
     * @param centroidsAvailable the centroids
     * @return the index of the point with the minimum distance squared from the centroids
     */
    public static int getIndexOfMinimumDistanceSquared(DataPoint point, List<DataPoint> centroidsAvailable) {

        double distance = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < centroidsAvailable.size(); i++) {

            double newDistance = point.distanceTo(centroidsAvailable.get(i));
            if (newDistance < distance) {
                distance = newDistance;
                index = i;
            }
        }

        return index;
    }

    /**
     * Gets the probability (minimum square distance over the total cost) for a given point
     * @param point the point to get the probability of
     * @param centroidsAvailable the centroids
     * @param cost the cost (sum of all squared distances to the centroids of all the points)
     * @return the probability
     */
    public static double getProbability(DataPoint point, List<DataPoint> centroidsAvailable, double cost) {

        double distance = getMinimumDistanceSquared(point, centroidsAvailable);

        return distance / cost;
    }

    public static double getWeightProbability(int index, int[] weightVector, double totalCost) {
        return weightVector[index] / totalCost;
    }

    public static int sum(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}
