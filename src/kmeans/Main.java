package kmeans;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        List<DataPoint> points = new ArrayList<>();
        Random generator = new Random();

        for (int i = 0; i < 2000; i++) {
            points.add(new DataPoint(generator.nextDouble() * generator.nextInt(100),
                    generator.nextDouble() * generator.nextInt(100)));
        }

        KMeans kMeans = new KMeans(20, points, new KMeansPlusPlusInitialiser());
        System.out.println(kMeans);

        KMeansVisualise visualisation = new KMeansVisualise(kMeans, "K-means++");
        javax.swing.SwingUtilities.invokeLater(visualisation::showGUI);

        KMeans k2 = new KMeans(20, points, new RandomInitialiser());
        KMeansVisualise visualise = new KMeansVisualise(k2, "Random");
        SwingUtilities.invokeLater(visualise::showGUI);

    }
}
