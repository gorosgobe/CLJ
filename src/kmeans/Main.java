package kmeans;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        List<DataPoint> points = new ArrayList<>();
        Random generator = new Random();

        for (int i = 0; i < 3000; i++) {
            points.add(new DataPoint(generator.nextDouble() * generator.nextInt(100),
                    generator.nextDouble() * generator.nextInt(100)));
        }

        long s1 = System.nanoTime();
        KMeans kMeans = new KMeans(20, points, new KMeansPlusPlusInitialiser());
        System.out.println(kMeans);
        long s2 = System.nanoTime();
        System.out.println("k++: " + ((s2 - s1) / 1000000000.0));

        KMeansVisualise visualisation = new KMeansVisualise(kMeans, "K-means++");
        javax.swing.SwingUtilities.invokeLater(visualisation::showGUI);

        long t1 = System.nanoTime();
        KMeans k2 = new KMeans(20, points, new RandomInitialiser());
        long t2 = System.nanoTime();
        System.out.println("Random: " + ((t2 - t1) / 1000000000.0));

        KMeansVisualise visualise = new KMeansVisualise(k2, "Random");
        SwingUtilities.invokeLater(visualise::showGUI);


        long r1 = System.nanoTime();
        KMeans k3 = new KMeans(20, points, new KMeansOrOrInitialiser(2));
        long r2 = System.nanoTime();
        System.out.println("K||: " + ((r2 - r1) / 1000000000.0));

        KMeansVisualise visualise2 = new KMeansVisualise(k3, "k||");
        SwingUtilities.invokeLater(visualise2::showGUI);


    }
}
