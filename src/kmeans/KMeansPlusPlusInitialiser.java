package kmeans;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*********************************************************************************/
//                             WORKING ON IT, DO NOT USE
/*********************************************************************************/


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

        List<DataPoint> centroids = new ArrayList<>();

        //sample a point uniformly
        Random generator = new Random();
        int randomInt = generator.nextInt(points.size());
        DataPoint point = points.get(randomInt);
        centroids.add(point);

        while (centroids.size() < k) {

            double random = Math.random();
            double cumulativeProbability = 0.0;

            double cost = points.parallelStream()
                    .map(p -> InitialiserUtils.getMinimumDistanceSquared(p, centroids))
                    .reduce(0.0, (a, b) -> a + b);

            for (int i = 0; i < points.size(); i++) {

                double probability = InitialiserUtils.getProbability(points.get(i), centroids, cost);

                cumulativeProbability += probability;

                if (random <= cumulativeProbability) {
                    centroids.add(points.get(i));
                    break;
                }
            }

        }

        return centroids;
    }

}
