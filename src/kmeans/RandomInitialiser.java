package kmeans;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomInitialiser implements Initialiser {


    @Override
    public List<DataPoint> createInitialCentroids(int k, List<DataPoint> points) {
        long s1 = System.nanoTime();
        Random generator = new Random();
        List<DataPoint> list = new ArrayList<>();

        while (list.size() < k) {
            int i = generator.nextInt(points.size());
            list.add(points.get(i));
        }

        System.out.println("Completed Random in: " + (System.nanoTime() - s1) / 1000000000.0 + "s");
        return list;
    }
}
