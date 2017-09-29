package kmeans;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansPlusPlusInitialiser implements Initialiser {
    @Override
    public List<DataPoint> createInitialCentroids(int k, List<DataPoint> points) {

        List<DataPoint> centroids = new ArrayList<>();

        Random generator = new Random();
        int randomInt = generator.nextInt(points.size());
        DataPoint point = points.get(randomInt);
        centroids.add(point);

        Random biasedRandomGenerator = new Random();

        while (centroids.size() < k) {

            for (int i = 0; i < points.size(); i++) {
                double probability = getProbability(points.get(i), centroids);
                if (probability >= 1) {
                    centroids.add(points.get(i));
                } else {
                    double randomDouble = biasedRandomGenerator.nextDouble();
                    if (randomDouble >= probability) {
                        centroids.add(points.get(i));
                    }
                }
            }
        }

        return centroids;
    }

    private static double getMinimumDistanceSquared(DataPoint point, List<DataPoint> centroidsAvailable) {

        double distance = Double.MAX_VALUE;

        for (int i = 0; i < centroidsAvailable.size(); i++) {
            double newDistance = Math.pow(point.distanceTo(centroidsAvailable.get(i)), 2.0);
            if (newDistance < distance) {
                distance = newDistance;
            }
        }

        return distance;
    }

    private static double getProbability(DataPoint point, List<DataPoint> centroidsAvailable) {

        double distance = getMinimumDistanceSquared(point, centroidsAvailable);

        double cost = centroidsAvailable.parallelStream()
                .map(i -> getMinimumDistanceSquared(i, centroidsAvailable))
                .reduce(0.0, (a, b) -> a + b);

        return distance / cost;
    }
}
