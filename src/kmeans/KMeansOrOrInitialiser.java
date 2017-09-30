package kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class representing an initialiser that uses the k-means|| algorithm (by Bahman Bahmani et al., 2012) for centroid
 * initialisation. This algorithm is similar to the k-means++ algorithm, but allows for a scalable and parallel
 * implementation.
 *
 * @author gorosgobe
 */
public class KMeansOrOrInitialiser implements Initialiser {

    private double oversamplingFactor;

    public KMeansOrOrInitialiser(double oversamplingFactor) {
        this.oversamplingFactor = oversamplingFactor;
    }

    @Override
    public List<DataPoint> createInitialCentroids(int k, List<DataPoint> points) {


        List<DataPoint> centroids = new ArrayList<>();

        //sample a point uniformly
        Random generator = new Random();
        int randomInt = generator.nextInt(points.size());
        DataPoint point = points.get(randomInt);
        centroids.add(point);

        //calculate initial cost
        double phi = points.parallelStream()
                .map(p -> InitialiserUtils.getMinimumDistanceSquared(p, centroids))
                .reduce(0.0, (a, b) -> a + b);
        double cost = phi;

        for (int d = 0; d < Math.round(Math.log(phi)); d++) {

            List<DataPoint> centroidsPrime = new ArrayList<>();

            for (int i = 0; i < points.size(); i++) {
                double probability = InitialiserUtils.getProbability(points.get(i), centroids, cost);
                double randomDouble = generator.nextDouble();
                if (randomDouble < oversamplingFactor * probability) {
                    centroidsPrime.add(points.get(i));
                }
            }

            //add centroids' to centroids
            for (int i = 0; i < centroidsPrime.size(); i++) {
                centroids.add(centroidsPrime.get(i));
            }
        }

        //create weight vector
        int[] w = new int[centroids.size()];

        List<DataPoint> nonCentroidPoints = points.parallelStream()
                .filter(p -> !centroids.contains(p))
                .collect(Collectors.toList());

        for (int i = 0; i < nonCentroidPoints.size(); i++) {
            int index = InitialiserUtils.getIndexOfMinimumDistanceSquared(nonCentroidPoints.get(i), centroids);
            w[index]++;
        }

        //k-means++
        int sum = InitialiserUtils.sum(w);
        List<DataPoint> actualCentroids = new ArrayList<>();
        while (actualCentroids.size() < k) {
            for (int j = 0; j < centroids.size(); j++) {
                double p = InitialiserUtils.getWeightProbability(j, w, sum);
                double d = generator.nextDouble();
                if (d < p) {
                    actualCentroids.add(centroids.get(j));
                    w[j] = 0;
                }
            }
        }


        return actualCentroids;
    }
}
